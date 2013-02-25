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
	@Override
	public double weightApplication(String text) {
		System.out.println();
		double input = 0;
		try
		{
			System.out.println(text);
			input = Double.parseDouble(scan.next());
		}
		catch(NumberFormatException e)
		{
			System.out.println("Vægten skal angives med et positivt kommatal. komma angives med punktum(.)");
			input = -1;
		}
		return input;
	}
	
	@Override
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
	@Override
	public String getPassword()
	{
		System.out.print("Enter your password: ");
		String password = scan.next();
		return password;

	}
	@Override
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
	@Override
	public void outString(String text)
	{
		System.out.println(text);
	}
	@Override
	public String getInput()
	{
		return scan.next();
	}

}
