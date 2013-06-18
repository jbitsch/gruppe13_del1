package RUN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ProduktAdministration;


import daointerfaces.DALException;

public class ProduktHandling {
	public void createProduktbatch(HttpServletRequest request, String handling,ProduktAdministration produktAdmin) {
		String produktbatchReceptId = request.getParameter("produktbatchReceptId");
			
		if(!(produktbatchReceptId == null )){
			
			try {
					produktAdmin.setProduktbatchReceptId(produktbatchReceptId);
					produktAdmin.setHandling(handling);
					produktAdmin.udfoerHandling();
			} catch (DALException e) {
				e.printStackTrace();
			}
		}
		String visInfo = request.getParameter("visInfo");

		if(!(visInfo  == null)){
			try {
				produktAdmin.setReceptKomp(Integer.parseInt(visInfo ));
			} catch (DALException e) {
				e.printStackTrace();
			}
		}
	}
	public void showProduktbatch(HttpServletRequest request,HttpSession session, ProduktAdministration produktAdmin) {
		String produktbatchValg = request.getParameter("produktbatchValg");
		if(!(produktbatchValg == null)){
			int produktbatchValgID = Integer.parseInt(produktbatchValg);
			produktAdmin.setProduktbatchId(produktbatchValgID);
			
			try {
				int id = produktAdmin.getProduktBatch().getRecept().getReceptId();
				produktAdmin.setReceptKomp(id);
			} catch (DALException e) {
				e.printStackTrace();
			}
			
			session.setAttribute("menu", "showProduktbatch");
		}
		String searchProduktBatch = request.getParameter("searchProduktB"); 
		if(!(searchProduktBatch == null)){
			String searchName =request.getParameter("searchProduktBatch");
			produktAdmin.setReceptNavn(searchName);
		}
	}
}
