import java.util.Scanner;

public class Menu {

	private static Scanner scan;

	public Menu()
	{
		scan  = new Scanner(System.in);
	}

	public void printmenu(double brutto, double tara, String inline, String indstruktionsDisplay, String conIp){
		
		for (int i=0;i<25;i++)
			System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto: " + (brutto-tara)+ " kg" );
		System.out.println("Instruktionsdisplay: " + indstruktionsDisplay );
		System.out.println("*************************************************");
		System.out.println("Debug info: ");
		System.out.println("Hooked up to " + conIp );
		System.out.println("Brutto: " + (brutto)+ " kg" );
		System.out.println("Streng modtaget: "+inline) ;
		System.out.println(" ");
		System.out.println("Understøttede kommandoer: ");
		System.out.println("D, DN, S, T, B, Q ");
		System.out.println("******");
		System.out.print ("Tast her: ");
	}
	
	public void startMenu(int portdst) 
	{
		System.out.println("Venter paa connection paa port " + portdst );
		System.out.println("Indtast eventuel portnummer som 1. argument");
		System.out.println("paa kommando linien for andet portnr");
		
	}
	public void closeCon()
	{
		System.out.println("");
		System.out.println("Program stoppet Q modtaget paa com port");
	}
	public double getBruto()
	{
		double newB = 0;
		while(true)
		{
			try
			{
				System.out.println("Indtast ny brutto: ");
				newB = Double.parseDouble(scan.next());
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Ugyldig ny bruto vægt");
			}
		}
		return newB;
	}
	public String getInput()
	{	
		return scan.next();
	}
	public void printText(String text)
	{
		System.out.println();
		System.out.println(text);
	}

}
