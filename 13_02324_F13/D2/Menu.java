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
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Debug info: ");
		System.out.println("Hooked up to " + conIp );
		System.out.println("Brutto: " + (brutto)+ " kg" );
		System.out.println("Streng modtaget: "+inline) ;
		System.out.println(" ");
		System.out.println("Denne vegt simulator lytter på ordrene ");
		System.out.println("D, DN, S, T, B, Q ");
		System.out.println("paa kommunikationsporten. ");
		System.out.println("******");
		System.out.println("Tast T for tara (svarende til knaptryk paa vegt)");
		System.out.println("Tast B for ny brutto (svarende til at belastningen paa vegt ændres)");
		System.out.println("Tast Q for at afslutte program program");
		System.out.println("Indtast (T/B/Q for knaptryk / brutto ændring /quit)");
		System.out.print ("Tast her: ");
		
		//return scan.next().charAt(0); //TODO get char T/B/Q
	}
	public void startMenu(int portdst) //TODO get new port
	{
		System.out.println("Venter paa connection paa port " + portdst );
		System.out.println("Indtast eventuel portnummer som 1. argument");
		System.out.println("paa kommando linien for andet portnr");
		
		//return scan.nextInt();
	}
	public void closeCon()
	{
		System.out.println("");
		System.out.println("Program stoppet Q modtaget paa com port");
	}


}
