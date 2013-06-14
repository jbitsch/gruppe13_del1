package RUN;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import controller.BrugerAdministration;
import controller.Login;
import daointerfaces.DALException;

public class OperatoerHandling {
	public void UserSelected(HttpServletRequest request, BrugerAdministration brugerAdmin,HttpSession session)
	{
		String userID = request.getParameter("brugervalg");
		if(!(userID == null || userID.isEmpty())){
			int uId = Integer.parseInt(userID);
			try {
				brugerAdmin.setUser(uId);
			} catch (DALException e) {
				e.printStackTrace();
			}
			session.setAttribute("menu", "userForm");
		}
	}
	public boolean createUserInformation(HttpServletRequest request, BrugerAdministration brugerAdmin) {
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
