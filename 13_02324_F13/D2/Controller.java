import java.util.Scanner;


public class Controller {

	private static double brutto=0;
	private static double tara=0;
	
	private static String inline="";
	private static String IndstruktionsDisplay= "";
	private static int portdst = 8000;

	private Menu menu;
	private Data data;

	public Controller(Menu menu, Data data) {
		this.menu = menu;
		this.data = data;
	}

	public synchronized void run()
	{
		
		new Thread(new PhysicalScaleSim()).start();
		
		
		menu.startMenu(portdst);
		
		try{
			data.getCon(portdst);
			printMenu();
			
			while (!(inline = data.getInput().toUpperCase()).isEmpty()){
				if (inline.startsWith("DN")){
					// ikke implimenteret
				}
				else if (inline.startsWith("D")){
					if (inline.equals("D"))
						IndstruktionsDisplay="";
					else
						IndstruktionsDisplay=(inline.substring(2,
								inline.length()));
					printMenu();
					data.writeTo("DB"+"\r\n");
				}
				else if (inline.startsWith("T")){
					data.writeTo("T " + (tara) + " kg "+"\r\n");
					tara=brutto;
					printMenu();

				}
				else if (inline.startsWith("S")){
					printMenu();
					data.writeTo("S " + (brutto-tara)+ " kg " +"\r\n");
				}
				else if (inline.startsWith("B")){ // denne ordre findes
					//ikke p� en fysisk v�gt
					String temp= inline.substring(2,inline.length());
					brutto = Double.parseDouble(temp);
					printMenu();
					data.writeTo("DB"+"\r\n");
				}
				else if ((inline.startsWith("Q"))){
					menu.closeCon();
					System.in.close();
					System.out.close();
					data.closeCon();
					System.exit(0);
				}
				else
				{
					printMenu();
					data.writeTo("ES"+"\r\n");
				}
			}
		}
		catch (Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
	}
	private void printMenu()
	{
		menu.printmenu(brutto, tara, inline, IndstruktionsDisplay, data.getIp());
		
	}
	
	public class PhysicalScaleSim implements Runnable {

		public void run() {
			choosePhysicalAction();
		}
		
		private void choosePhysicalAction() {
			Scanner scan = new Scanner(System.in);
			//TODO spørg om konsol plads
			System.out.println("Vægten venter på bruger-input");
			int functionChoice = scan.nextInt(); 
			if(functionChoice == 1) {
				setBrutto(scan.nextInt());
				choosePhysicalAction();
			}
			else if(functionChoice == 2) {
				setTara();
				choosePhysicalAction();
			}
			else if(functionChoice == 0) {
				quitApp();
				choosePhysicalAction();
			}
			else {
				choosePhysicalAction();
			}
			
		}
		private synchronized void setBrutto(int brutto) {
			Controller.brutto = brutto;
			System.out.println("DB"+"\r\n");
		}
		private synchronized void setTara() {
			tara = brutto;
			System.out.println("T " + (tara) + " kg "+"\r\n");
		}
		private synchronized void quitApp() {
	
		}
	}
}
