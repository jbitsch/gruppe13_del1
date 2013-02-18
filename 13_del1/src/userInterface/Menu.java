package userInterface;

import java.util.Scanner;


public class Menu implements IMenu {
	
	private Scanner scan = new Scanner(System.in);


	public Menu() {
		
	}

	@Override
	public void start() {
		
		System.out.println("-------------------------------------------------");
		System.out.println("*         Velkommen til vægtberegneren!         *");
		System.out.println("-------------------------------------------------");
		System.out.println();
		
	}
	
	public void weightApplication() {
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
		
	public int userID()
	{
		int id;
		
		System.out.println();
		System.out.print("Enter your ID: ");
		try 
		{
			id = Integer.parseInt(scan.next());
		}
		catch(NumberFormatException e)
		{
			id = -1;
		
		}
		return id;
	}
	public String getPassword()
	{
		System.out.print("Enter your password: ");
		String password = scan.next();
		return password;

	}
	
	public int showMenu(String header, String[] options)
	{
		int choise;

		printHeader(header);

		for(int i = 0; i < options.length; i++)
		{
			System.out.println(i + 1 + ". " + options[i]);
		}
		System.out.print("...>");
		try
		{
			choise = Integer.parseInt(scan.next());
			if(choise < 1 || choise > options.length)
			{
				throw new NumberFormatException();
			}
		}
		catch(NumberFormatException e)
		{
			choise = 0;
		}
		System.out.println("");
		return choise;
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
	
	public void outString(String text)
	{
		System.out.println(text);
	}
	public String getInput()
	{
		return scan.next();
	}

}