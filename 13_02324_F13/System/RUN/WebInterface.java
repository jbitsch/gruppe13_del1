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

import daointerfaces.DALException;


/**
 * Servlet implementation class BrugerId
 */
public class WebInterface extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;
	private BrugerAdministration brugerAdmin = null;
	private RaavareAdministration raavareAdmin = null;
	private ProduktAdministration produktAdmin = null; 
	private Connector c = null;
	private Login login = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebInterface() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		ServletContext application = request.getSession().getServletContext();
		HttpSession session = request.getSession();
		
		c = (Connector) application.getAttribute("con");
		if(c==null)
		{
			try {
				c = new Connector();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			application.setAttribute("con", c);
		}
		
		//Opretter et nyt login objekt til sessionen hvis den ikke allerede findes
		login = (Login) session.getAttribute("login");
		if(login == null){
			login = new Login();
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
					e.printStackTrace();
				}
		}
		if ("log_ud".equals(handling)) { 
			login.setAdgangskode("");
		}
		
		 // er brugeren logget korrekt ind?
		if (!login.isLoggetInd()) {           
			application.log("Bruger med "+login.getId()+" skal logge ind."); 
			request.getRequestDispatcher("/WEB-INF/CDIO/login.jsp").forward(request,response);
			return;                              // afslut behandlingen af denne side
		}
		////////////////////////////////////Logget ind //////////////////////////////////////////////////
		
		
		//Opretter et nye administrations objekter til sessionen hvis de ikke allerede er oprettet
		createAdminObjekts(session);

		///////////////Choose user information////////////////
		OperatoerHandling op = new OperatoerHandling();
		op.UserSelected(request, brugerAdmin, session);
		boolean brugerChange = op.createUserInformation(request, brugerAdmin);
		if(brugerChange)
				op.udfoerHandlingUserAdmin(application, handling, brugerAdmin, login);
		
		///////////////Raavare information //////////////
		RaavareHandling raavareHandling = new RaavareHandling();
		raavareHandling.raavareChoose(request, raavareAdmin, session); //er der valgt en raavare?
		boolean raavareChange = raavareHandling.raavareChange(request, raavareAdmin);
		if(raavareChange)
			raavareHandling.udfoerHandlingRaavareAdmin(application, handling, raavareAdmin, login);
		
		//////////////////////////Raavarebatch information ///////////////////////
		boolean raavarebatchChange = raavareHandling.raavarebatchChange(request, raavareAdmin);
		if(raavarebatchChange)
			raavareHandling.udfoerHandlingRaavareAdmin(application, handling, raavareAdmin, login);
		
		raavareHandling.searchRB(request,raavareAdmin);//er der soegt paa en raavarebatch?
		
		
		//////////////////////////////Recept/////////////////////////////////
		ReceptHandling receptHandling = new ReceptHandling();
		try {
			boolean dataEntered = receptHandling.createRecept(request, produktAdmin);
			if(dataEntered)
				receptHandling.udfoerHandlingProduktAdmin(application, handling, produktAdmin, login);
		} catch (DALException e1) {
			e1.printStackTrace();
		}

		receptHandling.receptValg(request, session,produktAdmin);//er der valgt en recept?
		
		//////////////////////Create produktbatch/////////////////////////////////
		ProduktHandling produktHandling = new ProduktHandling();
		produktHandling.createProduktbatch(request, handling,produktAdmin);
		
		//show produktbatch
		produktHandling.showProduktbatch(request, session, produktAdmin);
		
			
		//Hvilken side skal vi lande paa
		String menuValg = request.getParameter("menuValg");
		
		if(menuValg!=null)
		{
			session.setAttribute("menu", menuValg);
		}
		if("changePassword".equals(session.getAttribute("menu")))
		{
			brugerAdmin.setId(login.getId());
			request.getRequestDispatcher("/WEB-INF/CDIO/changePw.jsp").forward(request,response);
		}
		else if("userForm".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/userForm.jsp").forward(request,response);
		}
		else if("showUsers".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/chooseUser.jsp").forward(request,response);
		}
		else if("receptForm".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/receptForm1.jsp").forward(request,response);
		}
		else if("raavareForm".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/raavareForm.jsp").forward(request,response);
		}
		else if("showRaavare".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/chooseRaavare.jsp").forward(request,response);
		}
		else if("raavarebatchForm".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/raavareBatchForm.jsp").forward(request,response);
		}
		else if("showRaavarebatch".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/chooseRaavarebatch.jsp").forward(request,response);
		}
		else if("produktbatch".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/produktbatchForm.jsp").forward(request,response);
		}
		else if("chooseProduktBatch".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/chooseProduktbatch.jsp").forward(request,response);
		}
		else if("showProduktbatch".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/showProduktbatch.jsp").forward(request,response);
		}
		else if("showRecept".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/showRecept.jsp").forward(request,response);
		}
		else if("chooseRecept".equals(session.getAttribute("menu")))
		{
			request.getRequestDispatcher("/WEB-INF/CDIO/chooseRecept.jsp").forward(request,response);
		}
		else if("Tilbage".equals(session.getAttribute("menu")))
		{
			String page = request.getParameter("backpage");
			delete();
			if(page!=null)
				request.getRequestDispatcher(page).forward(request,response);
			else
				request.getRequestDispatcher("/WEB-INF/CDIO/menu.jsp").forward(request,response);
		}
		else
		{	
			delete();
			request.getRequestDispatcher("/WEB-INF/CDIO/menu.jsp").forward(request,response);	
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private void createAdminObjekts(HttpSession session) {
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
	}
	private void delete() {
		brugerAdmin.delete();
		brugerAdmin.deleteSucErr();
		
		raavareAdmin.delete();
		raavareAdmin.deleteSucErr();
		
		produktAdmin.delete();
		produktAdmin.deleteSucErr();
	}
}
