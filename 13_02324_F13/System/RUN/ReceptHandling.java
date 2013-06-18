package RUN;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Login;
import model.ProduktAdministration;


import daointerfaces.DALException;

public class ReceptHandling {
	/////////////////////////////////Recept///////////////////////////////////////////////////////
	public boolean createRecept(HttpServletRequest request, ProduktAdministration produktAdmin) throws DALException {
		boolean dataExcist = false;
		
		String receptId = request.getParameter("receptId");
		if(!(receptId == null)){
			produktAdmin.setReceptId(receptId);
			dataExcist = true;
		}
		String receptNavn = request.getParameter("receptNavn");
		if(!(receptNavn == null)){
			produktAdmin.setReceptNavn(receptNavn);
			dataExcist = true;
		}
		String r_id = request.getParameter("raavareToAdd");
		if (!(r_id == null)) {
			String netto= request.getParameter("netto"); 
			String tolerance = request.getParameter("tolerance");
			produktAdmin.addToRaavareList(r_id, netto, tolerance);
		}
		String raavareTodelete = request.getParameter("raavareToDelete");
		if (!(raavareTodelete == null)) {
			produktAdmin.setReceptId(raavareTodelete);
			dataExcist = true;
		}
		return dataExcist;
	}
	public void receptValg(HttpServletRequest request, HttpSession session, ProduktAdministration produktAdmin) {
		String receptValg = request.getParameter("receptValg");
		if(!(receptValg == null)){
			try {
				produktAdmin.setReceptKomp(Integer.parseInt(receptValg));
			} catch (DALException e) {
				e.printStackTrace();
			}
			produktAdmin.setReceptId(receptValg);
			session.setAttribute("menu", "showRecept");
		}
	}

	public void udfoerHandlingProduktAdmin(ServletContext application,String handling,ProduktAdministration produktAdmin,Login login) {
		//Udfoere handlingen i brugervalg.
		produktAdmin.setHandling(handling);
		if (produktAdmin.getHandling() != null) {           
			application.log(login.getId()+" udfoerer handling: "+produktAdmin.getHandling());
			try {
				produktAdmin.udfoerHandling();
			} catch (DALException e) {
				e.printStackTrace();
			} 
		}
	}
}
