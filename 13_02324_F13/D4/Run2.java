

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Data2;
import data.IData2;
import data.OperatoerDTO2;
import funktionalitet.Funktionalitet2;
import funktionalitet.IFunktionalitet2;

/**
 * Servlet implementation class BrugerId
 */
public class Run2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IData2 d = null;  // interface reference til datalag
	IFunktionalitet2 f = null; // interface reference til funktionalitetslag
	OperatoerDTO2 user = null;
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
		
		user = (OperatoerDTO2) session.getAttribute("user");
		if(user == null){
			user = new OperatoerDTO2();
			//scope = session
			session.setAttribute("user", user);
		}
		
		f = (IFunktionalitet2) application.getAttribute("function");
		if(f==null)
		{
			d = new Data2();
			f = new Funktionalitet2(d);
			application.setAttribute("function", f);
		}
		String id;
		if((id = request.getParameter("id")) != null){
			user.setOprId(Integer.parseInt(id)); 
		}
		String pw;
		if((pw = request.getParameter("password")) != null){
			user.setPassword(pw);
		}
		
		String handling = null;
		String[] params = request.getParameterValues("handling");
		if(params != null){
			handling = params[params.length-1];
		}
		
		boolean loginOk = false;
		if ("log ind".equals(handling)) { 
			loginOk = f.login(pw, id); 
		}
		
		if ("log_ud".equals(handling)) { 
			//TODO
		}
		if (!loginOk) {            // er brugeren logget korrekt ind?
			application.log("Bruger med "+id+" skal logge ind.");
			session.removeAttribute("valg");     // eller evt: session.invalidate()
			request.getRequestDispatcher("login.jsp?").forward(request,response);
			return;                              // afslut behandlingen af denne side
		}
//		valg = (Brugervalg) session.getAttribute("valg");
//		
//		System.out.println("valg: " + valg);
//		if(valg == null){
//			//Svarer til useBean tag paa Login
//			valg = new Brugervalg();
//			valg.setBankmodel(bank); //saet bankmodel foerste gang
//			//scope = application
//			session.setAttribute("valg", valg);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
