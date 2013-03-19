import java.io.IOException;

public class Controller {

	private static double brutto=0;
	private static double tara=0;
	
	private static String inline="";
	private static String IndstruktionsDisplay= "";
	private static int portdst;
	private static boolean RM20 = false;
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
			
			while (true){
				inline = data.getInput().toUpperCase();
				
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
					try
					{
						String temp= inline.substring(2,inline.length());
						brutto = Double.parseDouble(temp);
						printMenu();
						data.writeTo("DB"+"\r\n");
					}
					catch(IndexOutOfBoundsException e) {
						data.writeTo("ES"+"\r\n");
					}
					catch(NumberFormatException e)
					{
						data.writeTo("ES"+"\r\n");
					}
				}
				else if ((inline.startsWith("RM20 8"))){
					String[] temp;
					String delimiter = "\"";
					temp = inline.split(delimiter);
					data.writeTo("RM20 B\r\n");
					RM20 = true;
					printMenu();

					menu.printText("Venter paa svar fra RM20 ordre: "+temp[1]);

					
				}
				else if ((inline.startsWith("Q"))){
					menu.closeCon();
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
					
                    if (RM20){
                        boolean inputOk = false;
                        int retur = 0;
                        while(!inputOk)
                        {
                        	try
                        	{
                        		retur = Integer.parseInt(input);
                        		inputOk = true;
                        	}
        					catch(NumberFormatException e)
        					{
        						menu.printText("Input skal være tal");
        						input = menu.getInput().toUpperCase();
        					}
                        }
                    	try {
                            data.writeTo("RM20 A "+retur+"\r\n");
                   
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        RM20=false;
                        printMenu();    
                    }
					char choise = input.charAt(0);
					switch(choise)
					{
					case 'T':
						setTara();
						printMenu();
						break;
					case 'B':
						double newBruto = menu.getBruto();
						setBrutto(newBruto);
						printMenu();
						break;
					case 'Q':
						menu.closeCon();
						data.closeCon();
						System.exit(0);
					default:
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
