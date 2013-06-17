package RUN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import controller.ProduktAdministration;

import daointerfaces.DALException;

public class ProduktHandling {
	public void createProduktbatch(HttpServletRequest request, String handling,ProduktAdministration produktAdmin) {
		String produktbatchReceptId = request.getParameter("produktbatchReceptId");
			
		if(!(produktbatchReceptId == null || produktbatchReceptId.isEmpty())){
			
			try {
					produktAdmin.setProduktbatchReceptId(produktbatchReceptId);
					produktAdmin.setHandling(handling);
					produktAdmin.udfoerHandling();
			} catch (DALException e) {
				e.printStackTrace();
			}
		}
		String visInfo = request.getParameter("visInfo");

		if(!(visInfo  == null || visInfo.isEmpty())){
			System.out.println(visInfo);
			try {
				produktAdmin.setReceptKomp(Integer.parseInt(visInfo ));
			} catch (DALException e) {
				e.printStackTrace();
			}
		}
	}
	public void showProduktbatch(HttpServletRequest request,HttpSession session, ProduktAdministration produktAdmin) {
		String produktbatchValg = request.getParameter("produktbatchValg");
		if(!(produktbatchValg == null || produktbatchValg.isEmpty())){
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
		if(!(searchProduktBatch == null || searchProduktBatch.isEmpty())){
			String searchName =request.getParameter("searchProduktBatch");
			produktAdmin.setReceptNavn(searchName);
		}
	}
}
