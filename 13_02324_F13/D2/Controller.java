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
			//Udskriver velkomst menu
			menu.startMenu(portdst);
			//lytter for forbindelser på portdst
			data.getCon(portdst);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//Forbindelse er nu oprettet. Printermenuen udskrives. 
		printMenu();
		
		//Der oprettes en ny traad som skal lytte på input fra selve programmet
		new Thread(new PhysicalScaleSim()).start();
		
		try{
			
			while (true){
				try
				{
					//Henter input fra den tilsluttede klient
					inline = data.getInput().toUpperCase();
				}
				catch(NullPointerException e)
				{
					//Hvis forbindelsen er tabt, skal programmet lukke
					inline = "Q";
				}
				//Hvis programmet afventer svar fra RM20 ordre
				if(RM20)
				{
					data.writeTo("RM20 I\r\n");
				}
				//Fjerner tekst fra display
				else if (inline.startsWith("DW")){
					IndstruktionsDisplay="";
					printMenu();
					data.writeTo("DW"+"\r\n");
				}
				//Skriver tekst til display
				else if (inline.startsWith("D")){
					if (inline.equals("D"))
						IndstruktionsDisplay="";
					else
						IndstruktionsDisplay=(inline.substring(2,
								inline.length()));
					printMenu();
					data.writeTo("DB"+"\r\n");
				}
				//Saetter tara vaegt
				else if (inline.startsWith("T")){
					data.writeTo("T " + (tara) + " kg "+"\r\n");
					tara = brutto;
					printMenu();

				}
				//Henter netto vaegt
				else if (inline.startsWith("S")){
					printMenu();
					data.writeTo("S " + (brutto-tara)+ " kg " +"\r\n");
				}
				//saetter ny brutto baegt. 
				else if (inline.startsWith("B")){ 
					try
					{
						char c = inline.charAt(1);
						if(c == ' ')
						{
							String temp= inline.substring(2,inline.length());
							brutto = Double.parseDouble(temp);
							printMenu();
							data.writeTo("DB"+"\r\n");
						}
						else 
							data.writeTo("ES"+"\r\n");
					}
					catch(IndexOutOfBoundsException e) {
						data.writeTo("ES"+"\r\n");
					}
					catch(NumberFormatException e)
					{
						data.writeTo("ES"+"\r\n");
					}
				}
				//Modtager RM20 8 ordre
				else if ((inline.startsWith("RM20 8"))){
					String[] temp;
					int count = 0;
					for (int i=0; i<inline.length(); i++)
					{
						if(inline.charAt(i)=='\"')
						{
							count++;
						}
							
					}
					if (count==6)
					{
						String delimiter = "\"";
						temp = inline.split(delimiter);
		
						if(temp[1].length()>30)
						{
							data.writeTo("RM20 L\r\n");
						}
						else 
						{
							data.writeTo("RM20 B\r\n");
							RM20 = true;
							printMenu();
							menu.printText("Venter på RM20 8 ordre: "+temp[1]);
						}
					}
					else
						data.writeTo("RM20 L\r\n");
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
			e.printStackTrace();
			//menu.printText("Forbindelse til klient tabt, programmet lukkes");
			
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
                        		if(input.length()<=8)
                        		{
                        			retur = Integer.parseInt(input);
                        			inputOk = true;
                        		}
                        		else
                        			throw new NumberFormatException();
                        		
                        	}
        					catch(NumberFormatException e)
        					{
        						menu.printText("Input skal være maks 8 tal");
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
