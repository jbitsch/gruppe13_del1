package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import function.UserLogic;
import function.IUserLogic;

public class Menu implements IMenu {
	
	private Scanner scan = new Scanner(System.in);
	private IUserLogic userLogic;
	private boolean admin = true;
	private String name;
	private int operatorId;

	public Menu(IUserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public void start() {
		boolean keepRunning = true;
		
		System.out.println("-------------------------------------------------");
		System.out.println("*         Velkommen til vægtberegneren!         *");
		System.out.println("-------------------------------------------------");
		System.out.println();
		
		while(keepRunning)
		{
			int choise = showMenu("Hovedmenu", new String[]{"Log ind", "Opret bruger", "Afslut"});
			
			switch(choise)
			{
			case 1:
				if(login())
					menu2();
				break;
			case 2:
				newUser(false);
				break;
			case 3:
				keepRunning = false;
				break;
			}
		}
	}
	
	
	private void menu2()
	{
		String[] options;
		boolean logedIn = true;
		System.out.println();
		if(admin)
		{
			System.out.println("Du er logget ind som administrator.");
			options = new String[]{"Log ud", "Vægt applikation", "Skift password", "Administrer operatører"};
		}
		else
		{
			options = new String[]{"Log ud", "Vægt applikation", "Skift password"};
			System.out.println("Du er logget ind som " + name);
		}
		
		while(logedIn)
		{
			int choise2 = showMenu("Vælg den ønskede funktion", options);
			System.out.println();
			
			switch(choise2)
			{
				case 1:
				{
					admin = false;
					operatorId = 0;
					name = "";
					logedIn = false;
					System.out.println("\nDu logges nu af systemet");
					break;
				}
				case 2:
					boolean runApp = true;
					System.out.print("Indtast applikationskode: ");
					String appCode = scan.next();
					// check whether app code is correct
					do
					{
						weightApplication();
						System.out.println("Ønsker du at afveje endnu en portion? (j/n) ");
						if(!scan.next().equalsIgnoreCase("j"))
							runApp = false;
					} while(runApp);
					break;
				case 3:
					changePassword();
					break;
				case 4:
					adminMenu();
					break;
			}
		}
	}
	
	
	private void adminMenu()
	{
		String id;
		boolean goBack = false;
		
		while(!goBack)
		{
			int adminChoise = showMenu("Administrer operatører", new String[]{"Vis operatør", "Tilføj operatør", "Rediger operatør", "Slet operatør", "Tilbage"});
			
			System.out.println();
			
			switch(adminChoise)
			{
			case 1:
				// Maybe: Show list of all operators
				System.out.print("Indtast bruger ID, på den operatør, du vil have vist: ");
				id = scan.next();
				// Check whether or not ID exists, if yes, show operator.
				break;
			case 2:
				newUser(true);
				break;
			case 3:
				// Maybe: Show list of all operators
				System.out.print("Indtast bruger ID, på den operatør, du vil redigere: ");
				id = scan.next();
				// Check whether or not, user exists
				int adminChoice2 = showMenu("Hvad vil du redigere?", new String[]{"ID", "Navn", "CPR-nr", "Password"});
				
				System.out.println();
				boolean wrongInput = true;
				
				switch(adminChoice2)
				{
				case 1:
					do
					{
					System.out.print("Indtast nyt ID: ");
					id = scan.next();
					// check id, set id
					wrongInput = false;
					// (or error message)
					} while(wrongInput);
					break;
				case 2:
					do
					{
					System.out.print("Indtast nyt Navn: ");
					String operatorName = scan.next();
					// check navn, set id
					wrongInput = false;
					// (or error message)
					} while(wrongInput);
					break;
				case 3:
					do
					{
					System.out.print("Indtast nyt CPR-nr: ");
					String CPR = scan.next();
					// check id, set id
					wrongInput = false;
					// (or error message)
					} while(wrongInput);
					break;
				case 4:
					do
					{
					System.out.print("Indtast nyt password: ");
					String password = scan.next();
					// check id, set id
					wrongInput = false;
					// (or error message)
					} while(wrongInput);
					break;
				}
				break;
			case 4:			
				// Maybe: Show list of all operators
				System.out.print("Indtast bruger ID, på den operatør, du vil slette: ");
				id = scan.next();
				// Check whether or not ID exists, if yes, show operator.
				break;
			case 5:
				goBack = true;
				break;
			}
		}
	}
	
	
	private void weightApplication() {
		System.out.println();
		try
		{
			System.out.print("Indtast tarra vægt (kg): ");
			int tarra = Integer.parseInt(scan.next());
			System.out.print("Indtast brutto vægt (kg): ");
			int brutto = Integer.parseInt(scan.next());
			int netto = brutto - tarra;
			if(netto >= 0)
				System.out.println("Netto vægt: " + netto + " kg");
			else
				System.out.println("Tarra kan ikke være større end brutto.");
		}
		catch(NumberFormatException e)
		{
			System.out.println("Vægten skal angives med tal.");
		}
	}
	
	
	private void changePassword()
	{
		while(true)
		{
			System.out.println();
			System.out.print("Indtast bruger ID: ");
			String id = scan.next();
			System.out.println("Indtast gammelt password: ");
			String oldPassword = scan.next();
			System.out.println("Indtast nyt password: ");
			String newPassword = scan.next();
			System.out.println("Gentag nyt password: ");
			String newPassword2 = scan.next();
			
			// check if password change is legal. And if the current user, is the user with this id
			// change password in system
			break;
		}
	}
	

	private boolean login()
	{
		int id;
		
		System.out.println();
		System.out.print("ID: ");
		try {
			id = Integer.parseInt(scan.next());
		}
		catch(NumberFormatException e)
		{
			System.out.println("Login mislykkedes. Dit ID skal være et tal.\n");
			return false;
		}
		System.out.print("Operatornavn: ");
		String opName = scan.next();
		System.out.print("Password: ");
		String password = scan.next();
		
		// Chech if login excist, print error message if not
		
		// Chech if user is admin
		
		// Get and set name of operator
		name = opName;
		operatorId = id;
		return true;
	}
	
	
	private void newUser(boolean adminOperation)
	{
		System.out.println("Opret ny bruger");
		while(true)
		{
			System.out.print("Operatornavn: ");
			String opName = scan.next();
			System.out.print("Password: ");
			String password = scan.next();
			System.out.print("Gentag password: ");
			String password2 = scan.next();
			
			//if(checkNewUser())
			//make and set id
			operatorId = 22;
			if(!adminOperation)
			{
				//show id
				name = opName;
				menu2();
			}
			return;
		}
	}
	
	
	private int showMenu(String header, String[] options)
	{
		int choise;

		printHeader(header);
		
		for(int i = 0; i < options.length; i++)
		{
			System.out.println(i + 1 + ". " + options[i]);
		}
		while(true)
		{
			System.out.print("... ");
			try
			{
				choise = Integer.parseInt(scan.next());
				if(choise < 1 || choise > options.length)
				{
					throw new NumberFormatException();
				}
				return choise;
			}
			catch(NumberFormatException e)
			{
				System.out.print("\nPunktnummer findes ikke. (For fx at vælge punkt 1, tast: 1)\n");
				
			}
		}
	}
	
	private void printHeader(String header)
	{
		System.out.println();
		
		for(int i = 0; i < header.length(); i++)
			System.out.print("-");
		System.out.println();
		
		System.out.println(header);
		
		for(int i = 0; i < header.length(); i++)
			System.out.print("-");
			System.out.println();
	}

}
