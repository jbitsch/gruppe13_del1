

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	IData2 d;  // interface reference til datalag
	IFunktionalitet2 f; // interface reference til funktionalitetslag
	OperatoerDTO2 u;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Run2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// konfiguration af system
		// ops�t referencer til funktionalitets- og datalag
		d = new Data2(); // implementering af IData
		f= new Funktionalitet2(d); // implementering af IFunktionalitet
		u = new OperatoerDTO2();
		ServletContext context = getServletContext();
		context.setAttribute("function", f);
		context.setAttribute("user", u);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
