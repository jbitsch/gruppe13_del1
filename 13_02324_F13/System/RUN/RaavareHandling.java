package RUN;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Login;
import model.RaavareAdministration;


import daointerfaces.DALException;

public class RaavareHandling {
	public boolean raavareChange(HttpServletRequest request,RaavareAdministration raavareAdmin)
	{
		boolean dataExcist = false;
		
		String raavareId = request.getParameter("raavareId");
		if(!(raavareId == null)){
			raavareAdmin.setRaavareId(raavareId);
			dataExcist = true;
		}
		String raavareNavn = request.getParameter("raavareNavn");
		if(!(raavareNavn == null)){
			raavareAdmin.setRaavareNavn(raavareNavn);
			dataExcist = true;
		}
		return dataExcist;
	}	
	public void searchRB(HttpServletRequest request, RaavareAdministration raavareAdmin) {
		String searchRB = request.getParameter("searchRB"); 
		if(!(searchRB == null)){
			String searchName =request.getParameter("searchRBatch");
			raavareAdmin.setRaavareNavn(searchName);
		}
	}
	public void raavareChoose(HttpServletRequest request,RaavareAdministration raavareAdmin,HttpSession session)
	{
		String raavareId = request.getParameter("raavarevalg");
		if(!(raavareId == null)){
			int raavareID = Integer.parseInt(raavareId);
			try {
				raavareAdmin.setRaavare(raavareID);
			} catch (DALException e) {
				e.printStackTrace();
			}
			session.setAttribute("menu", "Opret raavare");
		}
	}
	public boolean raavarebatchChange(HttpServletRequest request,RaavareAdministration raavareAdmin)
	{
		boolean dataExcist = false;
		
		String raavarebatchId = request.getParameter("raavarebatchId");
		if(!(raavarebatchId == null)){
			raavareAdmin.setRaavareBatchId(raavarebatchId);
			dataExcist = true;
		}
		String raavareMaengde = request.getParameter("raavareMaengde");
		if(!(raavareMaengde == null)){
			raavareAdmin.setMaengde(raavareMaengde);
			dataExcist = true;
		}
		String raavarevalgBatch = request.getParameter("raavarevalgBatch");
		if(!(raavarevalgBatch == null)){
			raavareAdmin.setBatchRaavareId(raavarevalgBatch);
			dataExcist = true;
		}
		String leverandoer = request.getParameter("leverandoer");
		if(!(leverandoer == null)){
			raavareAdmin.setLeverandoer(leverandoer);
			dataExcist = true;
		}
		return dataExcist;
	}
	public void udfoerHandlingRaavareAdmin(ServletContext application,String handling,RaavareAdministration raavareAdmin,Login login) {
		raavareAdmin.setHandling(handling);
		if (raavareAdmin.getHandling() != null) {           
			application.log(login.getId()+" udfoerer handling: "+raavareAdmin.getHandling());
			try {
				raavareAdmin.udfoerHandling();
			} catch (DALException e) {
				e.printStackTrace();
			} 
		}
	}
}
