import java.util.Scanner;

public class Menu {

	private static Scanner scan;

	public Menu() {
		scan = new Scanner(System.in);
	}

	/**
	 * A method which prints out information (host side)
	 * 
	 * @param brutto
	 *            The current brutto value of the scale
	 * @param tara
	 *            the current tara value of the scale
	 * @param inline
	 *            A String received from the connected client
	 * @param indstruktionsDisplay
	 * @param conIp
	 *            IP address of the connected client
	 */
	public void printmenu(double brutto, double tara, String inline,
			String indstruktionsDisplay, String conIp) {

		for (int i = 0; i < 25; i++)
			System.out.println(" ");
		System.out.println("*************************************************");
		System.out.println("Netto: " + (brutto - tara) + " kg");
		System.out.println("Instruktionsdisplay: " + indstruktionsDisplay);
		System.out.println("*************************************************");
		System.out.println("Debug info: ");
		System.out.println("Hooked up to " + conIp);
		System.out.println("Brutto: " + (brutto) + " kg");
		System.out.println("Streng modtaget: " + inline);
		System.out.println(" ");
		System.out.println("Understøttede kommandoer: ");
		System.out.println("RM20 8, DW, D, T, S, B, Q");
		System.out.println("******");
		System.out
				.println("Indtast (T/B/Q for knaptryk / brutto aendring /quit)");
		System.out.print("Tast her: ");
	}

	/**
	 * Initial message from the program-
	 * 
	 * @param portdst
	 *            Port number which the program listens to
	 */
	public void startMenu(int portdst) {
		System.out.println("Venter paa connection paa port " + portdst);
		System.out.println("Indtast eventuel portnummer som 1. argument");
		System.out.println("paa kommando linien for andet portnr");

	}

	/**
	 * Informs that the program received instructions to terminate
	 */
	public void closeCon() {
		System.out.println("");
		System.out.println("Program stoppet Q modtaget paa com port");
	}

	/**
	 * Prints out the current brutto value of the scale
	 * 
	 * @return The brutto value of the scale
	 */
	public double getBruto() {
		double newB = 0;
		while (true) {
			try {
				System.out.println("Indtast ny brutto: ");
				newB = Double.parseDouble(scan.next());
				break;
			} catch (NumberFormatException e) {
				System.out.println("Ugyldig ny bruto vægt");
			}
		}
		return newB;
	}

	public String getInput() {
		return scan.next();
	}

	public void printText(String text) {
		System.out.println();
		System.out.println(text);
	}

}
