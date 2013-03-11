import java.io.IOException;
import java.util.Scanner;


public class Controller {

	private static double brutto=0;
	private static double tara=0;
	
	private static String inline="";
	private static String IndstruktionsDisplay= "";
	private static int portdst;
	private static boolean RN20 = false;
	private Menu menu;
	private Data data;

	public Controller(Menu menu, Data data) {
		this.menu = menu;
		this.data = data;
	}

	public synchronized void run(int port)
	{
		
		portdst = port;
		
		try
		{
			menu.startMenu(portdst);
			data.getCon(portdst);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		printMenu();
		
		new Thread(new PhysicalScaleSim()).start();
		
		try{
			
			while (!(inline = data.getInput().toUpperCase()).isEmpty()){
				if (inline.startsWith("DW")){
					IndstruktionsDisplay="";
					printMenu();
					data.writeTo("DW"+"\r\n");
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
					tara = brutto;
					printMenu();

				}
				else if (inline.startsWith("S")){
					printMenu();
					data.writeTo("S " + (brutto-tara)+ " kg " +"\r\n");
				}
				else if (inline.startsWith("B")){ 
					String temp= inline.substring(2,inline.length());
					brutto = Double.parseDouble(temp);
					printMenu();
					data.writeTo("DB"+"\r\n");
				}
				else if ((inline.startsWith("RM20"))){
					data.writeTo("RM20 B\r\n");
					RN20 = true;
					printMenu();
					menu.printText("Venter på svar fra RN20 ordre: ");
					
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
	
	private class PhysicalScaleSim implements Runnable {

		public void run() {
			choosePhysicalAction();
		}
		
		private void choosePhysicalAction() {
			try{
				while (true){
					String input = menu.getInput().toUpperCase();
					
                    if (RN20){
                        int retur = Integer.parseInt(input);
                        try {
                            data.writeTo("RM20 A "+retur+"\r\n");
                   
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        RN20=false;
                        printMenu();    
                    }
					
					else if (input.startsWith("T")){
						setTara();
						printMenu();
					}
					else if (input.startsWith("B")){
						double newBruto = menu.getBruto();
						setBrutto(newBruto);
						printMenu();
					}
					else if ((input.startsWith("Q"))){
						menu.closeCon();
						System.in.close();
						System.out.close();
						data.closeCon();
						System.exit(0);
					}
					else
					{
						System.out.println("Ugyldigt input");
						printMenu();	
					}
				}
			}
			catch (Exception e){
				System.out.println("Exception: "+e.getMessage());
			}
			
		}
		private synchronized void setBrutto(double brut) {
			brutto = brut;
			
		}
		private synchronized void setTara() {
			tara = brutto;
		}
	}
}
