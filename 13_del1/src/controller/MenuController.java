package controller;

import data.Admin;
import data.IOperatoerDAO.DALException;
import data.User;
import function.Function;
import userInterface.Menu;


public class MenuController {

	private Menu menu;
	private Function function;
	
	public MenuController(Menu menu, Function function) {
		this.menu = menu;
		this.function = function;
	}

	public void run()
	{
		menu.start();
		int in = 0;
		do							
		{	
			in = menu.showMenu("Hovedmenu", new String[]{"Log ind", "Afslut"});
			handleMenuInput(in);

		} while (in != 2) ; // quit to in=2;
	}

	public void handleMenuInput(int choise)
	{

		switch(choise)
		{
		case 1:
			User user = loginHandler();
			if(user!=null)
				menu2(user);
			break;
		case 2:
			menu.outString("Farvel!");
			break;
		default:invalidInputHandler();
		}
	}
	
	//===================================================================
	private void invalidInputHandler() 
	{
		menu.outString("Invalid input!");
	}
	//===================================================================
	private User loginHandler()
	{
		int id = -1;
		User user = null;
		do							
		{	
			id = menu.userID();
		} while (id == -1) ; 
		
		String password  = menu.getPassword();
		try
		{
			function.login(password, id);
			user = function.getUser(id);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
		}
		
		return user;
	}
	//===================================================================
	private void menu2(User user)
	{
	
		int in = 0;
		do							
		{	
			String[] options;
			System.out.println();
			menu.outString("Velkommen:" +user.getOprNavn());
			if(user instanceof Admin)
			{
				menu.outString("Du er logget ind som administrator.");
				options = new String[]{"Log ud", "Vægt applikation", "Skift password", "Administrer operatører"};
				in = menu.showMenu("Vælg den ønskede funktion", options);
				handleMenuInputAdmin(in,user);
			}
			else
			{
				options = new String[]{"Log ud", "Vægt applikation", "Skift password"};
				in = menu.showMenu("Vælg den ønskede funktion", options);
				handleMenuInputUser(in,user);
			}


		} while (in != 1) ; // quit to in=1;
	}
	//===================================================================
	public void handleMenuInputUser(int choise, User user)
	{

		switch(choise)
		{
		case 1:
			menu.outString("\nDu logges nu af systemet");
			break;
		case 2:
			weightApp();
			break;
		case 3:
			changePassword(user);
			break;
		default:invalidInputHandler();
		}
	}
	//===================================================================
	public void handleMenuInputAdmin(int choise, User user)
	{

		switch(choise)
		{
		case 1:
			menu.outString("\nDu logges nu af systemet");
			break;
		case 2:
			weightApp();
			break;
		case 3:
			changePassword(user);
			break;
		case 4:
			adminMenu();
			break;
		default:invalidInputHandler();
		}
	}
	//===================================================================
	private void weightApp()
	{
		boolean runApp = true;
		menu.outString("Indtast applikationskode: ");
		String appCode = menu.getInput();
		if(appCode.equals("appCode"))
		{
			do
			{
				menu.weightApplication();
				menu.outString("Ønsker du at afveje endnu en portion? (j/n) ");
				if(!menu.getInput().equalsIgnoreCase("j"))
					runApp = false;
			} while(runApp);
		}
		else
		{
			menu.outString("Forkert password, du bliver sendt tilbage til menuen");
		}
	}
	//===================================================================
	
	private void changePassword(User user)
	{
		boolean oldPasswordOk = false;
		boolean newPasswordOk = false;
		do
		{
			menu.outString("");
			menu.outString("Indtast gammelt password: ");
			String oldPassword = menu.getInput();
			oldPasswordOk = function.checkOldPassword(user, oldPassword);
		}while(!oldPasswordOk);
		
		do
		{
			menu.outString("Indtast nyt password: ");
			String newPassword = menu.getInput();
			menu.outString("Gentag nyt password: ");
			String newPassword2 = menu.getInput();
			if(newPassword2.equals(newPassword))
			{
				newPasswordOk = function.checkPassword(newPassword2); 
			}
			else
			{
				menu.outString("De 2 nye passwords er ikke ens");
			}
		}while(!newPasswordOk);
	}
	//===================================================================
	private void adminMenu()
	{
		AdminController adminCon = new AdminController(menu,function);
		adminCon.run();
	}
}



