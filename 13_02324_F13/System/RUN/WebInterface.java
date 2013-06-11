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
import controller.BrugerAdministration;
import controller.Login;
import controller.ProduktAdministration;
import controller.RaavareAdministration;

import daoimpl.MySQLOperatoerDAO;
import daointerfaces.DALException;
import daointerfaces.IOperatoerDAO;


/**
 * Servlet implementation class BrugerId
 */
public class WebInterface extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;
	private BrugerAdministration brugerAdmin = null;
	private RaavareAdministration raavareAdmin = null;
	private ProduktAdministration produktAdmin = null;
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
		brugerAdmin = (BrugerAdministration) session.getAttribute("brugerAdmin");
		if(brugerAdmin == null){
			brugerAdmin = new BrugerAdministration();
			session.setAttribute("brugerAdmin", brugerAdmin);
		}
		produktAdmin = (ProduktAdministration) session.getAttribute("produktAdmin");
		if(produktAdmin == null){
			produktAdmin = new ProduktAdministration();
			session.setAttribute("produktAdmin", produktAdmin);
		}
		raavareAdmin = (RaavareAdministration) session.getAttribute("raavareAdmin");
		if(raavareAdmin == null){
			raavareAdmin = new RaavareAdministration();
			session.setAttribute("raavareAdmin", raavareAdmin);
		}

		//////////////////Choose user information/////////////////////////////////////////////////////
		String userID = request.getParameter("brugervalg");
		if(!(userID == null || userID.isEmpty())){
			int uId = Integer.parseInt(userID);
			try {
				brugerAdmin.setUser(uId);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("menu", "userForm");
		}
		boolean brugerChange = createUserInformation(request);
		if(brugerChange)
				udfoerHandlingUserAdmin(application,handling);
		
		
		
		
		createRecept(request);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Hvilken side skal vi lande paa
		String menuValg = request.getParameter("menuValg");
		if(menuValg!=null)
		{
			session.setAttribute("menu", menuValg);
		}
		if("changePassword".equals(session.getAttribute("menu")))
		{
			brugerAdmin.setId(login.getId());
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
		else if("receptForm".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("receptForm.jsp").forward(request,response);
		}
		else
		{
			brugerAdmin.delete();
			brugerAdmin.deleteSucErr();
			request.getRequestDispatcher("menu.jsp").forward(request,response);
		}	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private boolean createRecept(HttpServletRequest request) {
		boolean dataExcist = false;
		
		String receptId = request.getParameter("receptId");
		if(!(receptId == null || receptId.isEmpty())){
			produktAdmin.setReceptId(Integer.parseInt(receptId));
			dataExcist = true;
		}
		String receptNavn = request.getParameter("receptNavn");
		if(!(receptNavn == null || receptNavn.isEmpty())){
			produktAdmin.setReceptNavn(receptNavn);
			dataExcist = true;
		}
		System.out.println(request.getParameter("raavareValg"));
		
		return dataExcist;
	}
	
	
	/////////////////////////Create user informations//////////////////////////////////
	private void udfoerHandlingUserAdmin(ServletContext application,String handling) {
		//Udfoere handlingen i brugervalg.
		brugerAdmin.setHandling(handling);
		if (brugerAdmin.handling != null) {           
			application.log(login.getId()+" udfoerer handling: "+brugerAdmin.handling);
			try {
				brugerAdmin.udfoerHandling();
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //saetter handlingen igang i brugervalg
		}
	}
	private boolean createUserInformation(HttpServletRequest request) {
		boolean dataExcist = false;

		String name = request.getParameter("oprName");
		if(!(name == null || name.isEmpty())){
			brugerAdmin.setName(name);
			dataExcist = true;
		}
		String ini = request.getParameter("ini");
		if(!(ini == null || ini.isEmpty())){
			brugerAdmin.setIni(ini);
			dataExcist = true;
		}
		String cpr = request.getParameter("cpr");
		if(!(cpr == null || cpr.isEmpty())){
			brugerAdmin.setCpr(cpr);
			dataExcist = true;
		}
		String newPw = request.getParameter("newPw");
		if(!(newPw == null || newPw.isEmpty())){
			brugerAdmin.setPassword(newPw);
			dataExcist = true;
		}
		String rolle = request.getParameter("rolle");
		if(!(rolle == null || rolle.isEmpty())){
			brugerAdmin.setRolle(rolle);
			dataExcist = true;
		}
		String old = request.getParameter("old");
		if(!(old == null || old.isEmpty())){
			dataExcist = true;
			brugerAdmin.setOld(old);
		}
		String new1 = request.getParameter("new1");
		if(!(new1 == null || new1.isEmpty())){
			dataExcist = true;
			brugerAdmin.setNew1(new1);
		}
		String new2 = request.getParameter("new2");
		if(!(new2 == null || new2.isEmpty())){
			dataExcist = true;
			brugerAdmin.setNew2(new2);
		}
		return dataExcist;
	}
}
