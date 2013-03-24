import java.io.IOException;
import java.text.DecimalFormat;

public class Controller2 {

	private static double brutto=0;
	private static double tara=0;

	private static String inline="";
	private static String IndstruktionsDisplay= "";
	private static int portdst;
	private static boolean RM20 = false;
	private GuiControl gui;
	private Data data;
	private DecimalFormat bruttoFormat;


	public Controller2(GuiControl gui, Data data) {
		this.gui = gui;
		this.data = data;
		bruttoFormat = new DecimalFormat("0,000");
	}

	/**
	 * The method which starts the program. It's primary function is to receive orders from the
	 * connection and give the correct response. It also instantiates the PhysicalScaleSim thread
	 * @param port The port number the program listens to.
	 */
	public synchronized void run(int port)
	{

		portdst = port;


		try
		{
			//lytter for forbindelser på portdst
			gui.notificationDialog("Du skal forbinde en terminal til port: "+port);
			data.getCon(portdst);
			data.writeTo("Gyldige kommandoer: RM20 8, DW, D, T, S, B, Q");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		//Forbindelse er nu oprettet. Printermenuen udskrives. 
		gui.visibility();

		//Der oprettes en ny tråd som skal lytte på input fra selve programmet
		Thread physSim = new Thread(new PhysicalScaleSim());
		physSim.start();

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
					gui.writeCommand("DW");
					gui.setMessage(IndstruktionsDisplay);
					data.writeTo("DW"+"\r\n");
				}
				//Skriver tekst til display
				else if (inline.startsWith("D")){
					if (inline.equals("D"))
						IndstruktionsDisplay="";
					else
						IndstruktionsDisplay=(inline.substring(2,
								inline.length()));
					gui.writeCommand("D");
					gui.setMessage(IndstruktionsDisplay);
					data.writeTo("DA"+"\r\n");
				}
				// Sætter tara vægt
				else if (inline.startsWith("T")){
					data.writeTo("T " + (tara) + " kg "+"\r\n");
					tara = brutto;
					updateGUI();
					gui.writeCommand("T");

				}
				//Henter netto vaegt
				else if (inline.startsWith("S")){
					String sNetto = bruttoFormat.format((brutto-tara));
					gui.writeCommand("S");
					data.writeTo("S " + sNetto+ " kg " +"\r\n");
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
							updateGUI();
							gui.writeCommand("B");
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
							gui.setMessage(temp[1]);
							gui.writeCommand("Venter paa svar fra RM20 8 ordre");
							gui.disableButtons(true);
						}
					}
					else
						data.writeTo("RM20 L\r\n");
				}
				else if ((inline.startsWith("Q"))){
					gui.dispose();
					data.closeCon();
					System.exit(0);
				}
				else
				{
					gui.writeCommand("Ugyldig kommando: "+inline);
					data.writeTo("ES"+"\r\n");
				}
			}
		}
		catch (Exception e){

			gui.notificationDialog("Connection to Client lost");
			e.printStackTrace();
			//menu.printText("Forbindelse til klient tabt, programmet lukkes");

		}
	}


	/**
	 * 
	 * A class which acts as the physical components of the scale being simulated.
	 * This class implements the Runnable interface and is supposed to be instantiated as
	 * a thread
	 *
	 */
	private class PhysicalScaleSim implements Runnable {


		/**
		 * The method used to start the thread. The method will listen for input constantly. 
		 * If an input is given, it will perform the specified action
		 */
	
		public void run() {
			try{
				
				while (true){
					String input = gui.getAnswer().toUpperCase();


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
								gui.notificationDialog("Input skal være maks 7 tal");
						
							}
						}
						try {
							data.writeTo("RM20 A "+retur+"\r\n");

						} catch (IOException e1) {
							e1.printStackTrace(System.out);
						}
						RM20=false;

					}else{
	
						char choise = gui.getInput();
						switch(choise)
						{
						case 'T':
							setTara();
							updateGUI();
							break;
						case 'B':
							double newBruto = gui.getBrutto();
							setBrutto(newBruto);
							updateGUI();
							break;
						case 'Q':
							gui.dispose();
							data.closeCon();
							System.exit(0);
						default:
							updateGUI();


						}
					}
				}
			}
			catch (Exception e){
				System.out.println("Exception: "+e.getMessage());
			}

		}

		/**
		 * A method which is called by choosePhysicalScaleAction if it receives the input
		 * "B (a double value)". When called the method will change the brutto value of the
		 * scale
		 * @param brut A double value representing the weigh which is placed on the scale
		 */
		private synchronized void setBrutto(double brut) {
			brutto = brut;

		}

		/**
		 * A method which is called by choosePhysicalScaleAction if it receives the input
		 * "T". When called the method will set the tara value of the scale, to the current
		 * brutto value
		 */
		private synchronized void setTara() {
			tara = brutto;
		}
	}
	private void updateGUI()
	{	
		Double sBrutto = brutto;
		String sNetto = bruttoFormat.format((brutto-tara));
		String sTara = bruttoFormat.format(tara);
		gui.updateWeight(sNetto, sBrutto, sTara);
	}
}


/*		choosePhysicalAction();
		}



private void choosePhysicalAction() {*/ 
