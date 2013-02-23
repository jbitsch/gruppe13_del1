package controller;

import java.util.ArrayList;

import data.IOperatoerDAO.DALException;
import data.User;
import function.Function;
import userInterface.IMenu;

public class AdminController {
	IMenu menu;
	Function function;
	public AdminController(IMenu menu, Function function)
	{
		this.menu = menu;
		this.function = function;
	}
	public void run(User user)
	{
		int in = 0;
		do							
		{	
			in = menu.showMenu("Administrer operatører", new String[]{"Vis operatør", "Tilføj operatør", "Rediger operatør", "Slet operatør", "Tilbage"});
			handleMenuInput(in,user);

		} while (in != 5) ; // go back, in=5;
	}

	public void handleMenuInput(int choise, User currentUser)
	{
		User user;
		switch(choise)
		{
		case 1:
			user = getOperatoer(currentUser);
			if (user!=null)
				showOperatoer(user);
			break;
		case 2:
			createUser();
			break;
		case 3:
			user = getOperatoer(currentUser);
			if(user!=null)
				run2(user);
			break;
		case 4:
			user = getOperatoer(currentUser);
			if(user!=null)
				if(user.getOprID()!=currentUser.getOprID())
				{
					deleteOperatoer(user);
				}
				else
				{
					menu.outString("Du kan ikke slette dig selv");
				}
			break;
		case 5:
			menu.outString("");
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
	private User getOperatoer(User currentUser)
	{
		ArrayList<User> operatoers = new ArrayList<User>();
		try
		{
			operatoers = function.getUsers(currentUser);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
		}
		for (int i = 0; operatoers.size()>i; i++)
		{
			menu.outString("Bruger: "+operatoers.get(i).getOprNavn()+", Bruger ID: "+operatoers.get(i).getOprID());
		}

		boolean inputIsOk = false;
		int ID = 0;

		do
		{
			try
			{
				menu.outString("Indtast bruger ID, på den operatør, du vil vælge: ");
				ID = Integer.parseInt(menu.getInput()); 
				inputIsOk = true;
			}
			catch(NumberFormatException e)
			{
				menu.outString("Forkert bruger ID.");
			}
		}while(!inputIsOk);
		User user = null;
		try
		{
			user = function.getUser(ID);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
			menu.outString("Du bliver sendt tilbage til menuen");
		}
		return user;
		
	}
	//===================================================================
	private void showOperatoer(User user)
	{
		menu.outString("Navn: "+user.getOprNavn());
		menu.outString("ID: "+user.getOprID());
		menu.outString("CPR: "+user.getCprNr());
		menu.outString("Password: "+user.getPassword());
	}
	//===================================================================
	private void deleteOperatoer(User user)
	{
		try
		{
			function.deleteUser(user);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
		}
	}
	//===================================================================
	private void createUser()
	{
		menu.outString("Opret ny bruger");

		menu.outString("Operatornavn: ");
		String name = menu.getInput();

		menu.outString("CPR: ");
		String cpr = menu.getInput();
		try
		{
			function.createUser(name, cpr);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
		}
	}
	//===================================================================
	//Redigering af bruger oplysninger.
	public void run2(User user)
	{
		int in = 0;
		do							
		{	
			in = menu.showMenu("Hvad vil du redigere?", new String[]{"Navn", "CPR-nr", "Password","Tilbage"});
			handleMenuInput2(in,user);

		} while (in != 4) ; // go back, in=4;
	}
	public void handleMenuInput2(int choise, User user)
	{
		
		switch(choise)
		{
		case 1:
			changeName(user);
			break;
		case 2:
			changeCpr(user);
			break;
		case 3:
			changePassword(user);
			break;
		case 4:
			menu.outString("");
			break;
		default:invalidInputHandler();
		}
	}
	//===================================================================
	public void changeName(User user)
	{
		menu.outString("Indtast nyt Navn: ");
		String operatorName = menu.getInput();
		user.setOprNavn(operatorName);
		try
		{
			function.updateUser(user);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
		}
	}
	//===================================================================
	public void changeCpr(User user)
	{
		menu.outString("Indtast nyt cpr nr: ");
		String operatorCpr = menu.getInput();
		user.setCprNr(operatorCpr);
		try
		{
			function.updateUser(user);
		}
		catch(DALException e)
		{
			menu.outString(e.getMessage());
		}
	}
	//===================================================================
	public void changePassword(User user)
	{
		boolean passwordOk = false;
		do
		{
			menu.outString("Indtast nyt password: ");
			String password = menu.getInput();
			passwordOk = function.checkPassword(password);
			if(passwordOk)
			{
				user.setCprNr(password);
					try
					{
						function.updateUser(user);
					}
					catch(DALException e)
					{
						menu.outString(e.getMessage());
					}
			}
		}while(!passwordOk);
	}


}
