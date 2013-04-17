package weightInterface;

import java.util.Scanner;

public class Menu2 {
	private Scanner scan = new Scanner(System.in);
	
	public int showMenu(String header, String[] options)
	{
		int choise;
		
		System.out.println();

		if(header != "")
			printHeader(header);

		for(int i = 0; i < options.length; i++)
		{
			System.out.println(i + 1 + ". " + options[i]);
		}
		System.out.print("--> ");
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
	
	
	public void printHeader(String header)
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
	public String getInput()
	{
		return scan.next();
	}
	public void printOut(String text)
	{
		System.out.println(text);
	}
	
}
