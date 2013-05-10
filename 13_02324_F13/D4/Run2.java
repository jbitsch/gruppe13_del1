

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Data2;
import data.IData2;
import funktionalitet.BrugerValg;
import funktionalitet.Login;

/**
 * Servlet implementation class BrugerId
 */
public class Run2 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BrugerValg valg = null;
	private IData2 d = null;  // interface reference til datalag
	private Login login = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Run2() {
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
		d = (IData2) application.getAttribute("data");
		if(d==null)
		{
			d = new Data2();
			application.setAttribute("data", d);
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
			login.setId(Integer.parseInt(id)); 
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
			login.tjekLogin(); 
		}
		if ("log_ud".equals(handling)) { 
			login.setAdgangskode("");
		}
		
		//hvis man ikke er logget ind.
		if (!login.isLoggetInd()) {            // er brugeren logget korrekt ind?
			application.log("Bruger med "+login.getId()+" skal logge ind.");
			session.removeAttribute("valg");     // eller evt: session.invalidate()
			request.getRequestDispatcher("login.jsp?").forward(request,response);
			return;                              // afslut behandlingen af denne side
		}
		
		
		////////////////////////////////////Logget ind //////////////////////////////////////////////////
		
		//Opretter et nyt brugervalg objekt til sessionen hvis den ikke allerede er oprettet
		valg = (BrugerValg) session.getAttribute("valg");
		if(valg == null){
			valg = new BrugerValg();
			valg.setData(d);
			session.setAttribute("valg", valg);
		}
		
		///////////////////////////////Weight App informations//////////////////
		String brutto = request.getParameter("brutto");
		if(!(brutto == null || brutto.isEmpty())){
			valg.setBrutto(brutto);
		}
		String tarra = request.getParameter("tarra");
		if(!(tarra == null || tarra.isEmpty())){
			valg.setTarra(tarra);
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
		//////////////////Choose user information/////////////////////////////////////////////////////
		String userID = request.getParameter("brugervalg");
		if(!(userID == null || userID.isEmpty())){
			int uId = Integer.parseInt(userID);
			valg.setUser(uId);
			session.setAttribute("menu", "userForm");
		}
		//////////////////////////////////////////////////////////////////////////////////
		
		
		//Udfoere handlingen i brugervalg.
		valg.setHandling(handling);
		if (valg.handling != null) {           
			application.log(login.getId()+" udfoerer handling: "+valg.handling);
			valg.udfoerHandling(); //saetter handlingen igang i brugervalg
		}	
		
		//Hvilken side skal vi lande paa
		String menuValg = request.getParameter("menuValg");
		if(menuValg!=null)
		{
			session.setAttribute("menu", menuValg);
		}
		if("weight".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("weight.jsp").forward(request,response);
		}
		else if("changePassword".equals(session.getAttribute("menu")))
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
