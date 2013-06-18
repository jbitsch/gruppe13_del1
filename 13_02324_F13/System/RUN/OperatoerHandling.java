package RUN;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.BrugerAdministration;
import model.Login;

import daointerfaces.DALException;

public class OperatoerHandling {
	public void UserSelected(HttpServletRequest request, BrugerAdministration brugerAdmin,HttpSession session)
	{
		String userID = request.getParameter("brugervalg");
		if(!(userID == null)){
			int uId = Integer.parseInt(userID);
			try {
				brugerAdmin.setUser(uId);
			} catch (DALException e) {
				e.printStackTrace();
			}
			session.setAttribute("menu", "Opret bruger");
		}
		String handling = request.getParameter("handling");
		if(!(handling == null)){
			if("Slet bruger".equals(handling))
				session.setAttribute("menu", "userDelete");
		}
	}
	public boolean createUserInformation(HttpServletRequest request, BrugerAdministration brugerAdmin) {
		boolean dataExcist = false;

		String name = request.getParameter("oprName");
		if(!(name == null)){
			brugerAdmin.setName(name);
			dataExcist = true;
		}
		String ini = request.getParameter("ini");
		if(!(ini == null)){
			brugerAdmin.setIni(ini);
			dataExcist = true;
		}
		String cpr = request.getParameter("cpr");
		if(!(cpr == null)){
			brugerAdmin.setCpr(cpr);
			dataExcist = true;
		}
		String newPw = request.getParameter("newPw");
		if(!(newPw == null)){
			brugerAdmin.setPassword(newPw);
			dataExcist = true;
		}
		String rolle = request.getParameter("rolle");
		if(!(rolle == null)){
			brugerAdmin.setRolle(rolle);
			dataExcist = true;
		}
		String old = request.getParameter("old");
		if(!(old == null)){
			dataExcist = true;
			brugerAdmin.setOld(old);
		}
		String new1 = request.getParameter("new1");
		if(!(new1 == null)){
			dataExcist = true;
			brugerAdmin.setNew1(new1);
		}
		String new2 = request.getParameter("new2");
		if(!(new2 == null)){
			dataExcist = true;
			brugerAdmin.setNew2(new2);
		}
		return dataExcist;
	}
	public void udfoerHandlingUserAdmin(ServletContext application,String handling,BrugerAdministration brugerAdmin,Login login) {
		//Udfoere handlingen i brugervalg.
		brugerAdmin.setHandling(handling);
		if (brugerAdmin.getHandling() != null) {           
			application.log(login.getId()+" udfoerer handling: "+brugerAdmin.getHandling());
			try {
				brugerAdmin.udfoerHandling();
			} catch (DALException e) {
				e.printStackTrace();
			} 
		}
	}
}
