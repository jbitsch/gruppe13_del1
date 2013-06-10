package RUN;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connector.Connector;
import controller.BrugerValg;
import controller.Login;

import daoimpl.MySQLOperatoerDAO;
import daointerfaces.DALException;
import daointerfaces.IOperatoerDAO;


/**
 * Servlet implementation class BrugerId
 */
public class WebInterface extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;
	private BrugerValg valg = null;
	private IOperatoerDAO d = null;  // interface reference til datalag
	private Connector c = null;
	private Login login = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebInterface() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		ServletContext application = request.getSession().getServletContext();
		HttpSession session = request.getSession();
		
		//tjekker om data er oprettet, ellers bliver det oprettet. 
		d = (IOperatoerDAO) application.getAttribute("data");
		if(d==null)
		{
			d = new MySQLOperatoerDAO();
			application.setAttribute("data", d);
		}
		c = (Connector) application.getAttribute("con");
		if(c==null)
		{
			try {
				c = new Connector();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			application.setAttribute("con", c);
		}
		
		//Opretter et nyt login objekt til sessionen hvis den ikke allerede findes
		login = (Login) session.getAttribute("login");
		if(login == null){
			login = new Login();
			login.setData(d);
			session.setAttribute("login", login);
		}	

		//modtager oplysninger fra login siden
		String id;
		if((id = request.getParameter("id")) != null){
			login.setId(id); 
		}
		String pw;
		if((pw = request.getParameter("password")) != null){
			login.setAdgangskode(pw);
		}
		
		//Handling der skal udføres
		String handling = null;
		String[] params = request.getParameterValues("handling");
		if(params != null){
			handling = params[params.length-1];
		}
		
		//Tjekker om der er logget ind eller om der skal logges ud
		if ("log ind".equals(handling)) { 

				try {
					login.tjekLogin();
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		if ("log_ud".equals(handling)) { 
			login.setAdgangskode("");
		}
		
		//hvis man ikke er logget ind.
		if (!login.isLoggetInd()) {            // er brugeren logget korrekt ind?
			application.log("Bruger med "+login.getId()+" skal logge ind.");
			session.removeAttribute("valg");     // eller evt: session.invalidate()
			request.getRequestDispatcher("login.jsp").forward(request,response);
			return;                              // afslut behandlingen af denne side
		}
		
		
		////////////////////////////////////Logget ind //////////////////////////////////////////////////
		
		//Opretter et nyt brugervalg objekt til sessionen hvis den ikke allerede er oprettet
		valg = (BrugerValg) session.getAttribute("valg");
		if(valg == null){
			valg = new BrugerValg();
			session.setAttribute("valg", valg);
		}
		
		////////////////////////////Change PW informations///////////////////////////////
		String old = request.getParameter("old");
		if(!(old == null || old.isEmpty())){
			valg.setOld(old);
		}
		String new1 = request.getParameter("new1");
		if(!(new1 == null || new1.isEmpty())){
			valg.setNew1(new1);
		}
		String new2 = request.getParameter("new2");
		if(!(new2 == null || new2.isEmpty())){
			valg.setNew2(new2);
		}
		/////////////////////////Create user informations//////////////////////////////////
		String name = request.getParameter("oprName");
		if(!(name == null || name.isEmpty())){
			valg.setName(name);
		}
		String ini = request.getParameter("ini");
		if(!(ini == null || ini.isEmpty())){
			valg.setIni(ini);
		}
		String cpr = request.getParameter("cpr");
		if(!(cpr == null || cpr.isEmpty())){
			valg.setCpr(cpr);
		}
		String newPw = request.getParameter("newPw");
		if(!(newPw == null || newPw.isEmpty())){
			valg.setPassword(newPw);
		}
		String rolle = request.getParameter("rolle");
		if(!(rolle == null || rolle.isEmpty())){
			valg.setRolle(rolle);
		}
		//////////////////Choose user information/////////////////////////////////////////////////////
		String userID = request.getParameter("brugervalg");
		if(!(userID == null || userID.isEmpty())){
			int uId = Integer.parseInt(userID);
			try {
				valg.setUser(uId);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("menu", "userForm");
		}
		//////////////////////////////////////////////////////////////////////////////////
		
		
		//Udfoere handlingen i brugervalg.
		valg.setHandling(handling);
		if (valg.handling != null) {           
			application.log(login.getId()+" udfoerer handling: "+valg.handling);
			try {
				valg.udfoerHandling();
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //saetter handlingen igang i brugervalg
		}	
		
		//Hvilken side skal vi lande paa
		String menuValg = request.getParameter("menuValg");
		if(menuValg!=null)
		{
			session.setAttribute("menu", menuValg);
		}
		if("changePassword".equals(session.getAttribute("menu")))
		{
			valg.setId(login.getId());
			request.getRequestDispatcher("changePw.jsp").forward(request,response);
		}
		else if("userForm".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("userForm.jsp").forward(request,response);
		}
		else if("showUsers".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("chooseUser.jsp").forward(request,response);
		}	
		else
		{
			valg.delete();
			valg.deleteSucErr();
			request.getRequestDispatcher("menu.jsp").forward(request,response);
		}	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
