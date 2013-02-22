package controller;

import data.Admin;
import data.IOperatoerDAO.DALException;
import data.User;
import function.Function;
import userInterface.IMenu;
import userInterface.Menu;


public class MenuController {

	private IMenu menu;
	private Function function;
	
	public MenuController(IMenu menu, Function function) {
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
			adminMenu(user);
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
				showWeightApp();
				menu.outString("Ønsker du at afveje endnu en portion? (j/n) ");
				if(!menu.getInput().equalsIgnoreCase("j"))
				{
					runApp = false;
				}
					
			} while(runApp);
		}
		else
		{
			menu.outString("Forkert password, du bliver sendt tilbage til menuen");
		}
	}
	//==================Hjælpe metode til weight app ==================//
	private void showWeightApp()
	{
		int tarra = 0;
		do
		{
			tarra = menu.weightApplication("Indtast tarra vægt (kg): ");
			
		}while(tarra<0);

		int brutto = 0;
		do
		{
			brutto = menu.weightApplication("Indtast brutto vægt (kg): ");
			
		}while(brutto<0);
		
		int netto = function.calculateWeight(tarra, brutto);
		if(netto >= 0)
			menu.outString("Netto vægt: " + netto + " kg");
		else
			menu.outString("Tarra kan ikke være større end brutto.");
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
			if(!oldPasswordOk)
			{
				menu.outString("Forkert gammelt password");
			}
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
				if(!newPasswordOk)
				{
					menu.outString("Det nye password overholder ikke reglerne");
				}
				else
				{
					user.setPassword(newPassword);
					newPasswordOk = true;
					try
					{
						function.updateUser(user);
					}
					catch(DALException e)
					{
						menu.outString(e.getMessage());
					}
				}
			}
			else
			{
				menu.outString("De 2 nye passwords er ikke ens");
			}
		}while(!newPasswordOk);
	}
	//===================================================================
	private void adminMenu(User user)
	{
		AdminController adminCon = new AdminController(menu,function);
		adminCon.run(user);
	}
}



