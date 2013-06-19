package Weight;

import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {

	private static double brutto=0;
	private static double tara=0;
	private static String inline="";
	private static String instruktionsDisplay= "";
	private static int portDst;
	private boolean rm20Flag = false;
	private WeightGUI wg;
	private Data data;
	private DecimalFormat doubleFormat;
	private Thread networkCon;

	public Controller(WeightGUI wg, Data data) {
		this.wg = wg;
		this.data = data;
		doubleFormat = new DecimalFormat("0.000");

	}

	public void run(int port)
	{
		portDst = port;
		networkCon = new NetworkCon();
		networkCon.start();
		wg.setVisible(true);
		weight();	
		
	}
	public void weight()
	{
		try{
			while (true){

				char input = wg.getInput();

				switch(input)
				{
				case 'E':
					String retur = wg.getAnswer();
					try {
						rm20Flag = false;
						wg.enableAnswerBtns(false, true);
						wg.setMessage("");
						wg.setRecievedCommand("");
						data.flushOut();
						data.writeTo("RM20 A \""+retur+"\"\r\n");

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 'T': 
					setTara();
					updateGUI();
					break;
				case 'D':
					setBrutto((brutto-0.001));
					updateGUI();
					break;
				case 'U':					
					setBrutto((brutto+0.001));
					updateGUI();
					break;
				case 'K':
					data.writeTo("K C 4\r\n");
					break;
				case 'S':
					brutto = wg.getSliderBrutto();
					updateGUI();
					break;
				case 'Q':
					wg.dispose();
					System.in.close();
					System.out.close();
					data.closeConnection();
					System.exit(0);
				default: 
					wg.setMessage("Forkert input");

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

	private void updateGUI()
	{	
		String sBrutto = doubleFormat.format(brutto);
		String sNetto = doubleFormat.format((brutto-tara));
		String sTara = doubleFormat.format(tara);
		wg.updateWeight(sNetto, sBrutto, sTara,brutto);
	}


	private class NetworkCon extends Thread {

		public void run() {
			connect();
		}
		public synchronized void connect()
		{

			try
			{
				System.out.println("Lytter paa port "+ portDst);
				data.awaitConnection(portDst);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try{

				while (true){
					try {
						// Henter input fra den tilsluttede klient
						inline = data.getInput().toUpperCase();
					} catch (NullPointerException e) {
						// Hvis forbindelsen er tabt, skal programmet lukke
						inline = "Q";
					}
					if(rm20Flag)
					{
						data.writeTo("RM20 I\r\n");
					}
					else if(inline.startsWith("K 3"))
					{
						wg.enableTransferBtn(true);
						wg.setRecievedCommand("K 3");
						data.writeTo("K A"+"\r\n");
					}
					else if(inline.startsWith("K 1"))
					{
						wg.enableTransferBtn(false);
						wg.setRecievedCommand("K 1");
						data.writeTo("K A"+"\r\n");
					}
					else if (inline.startsWith("DW")){
						instruktionsDisplay="";
						wg.setRecievedCommand("DW");
						wg.setMessage(instruktionsDisplay);
						data.writeTo("DW A"+"\r\n");
					}
					else if (inline.startsWith("D")){
						if (inline.equals("D"))
							instruktionsDisplay="";
						else
							instruktionsDisplay=(inline.substring(2,inline.length()));

						wg.setRecievedCommand("D");
						wg.setMessage(instruktionsDisplay);
						data.writeTo("D A"+"\r\n");
					}
					else if (inline.startsWith("T")){
						tara = brutto;
						updateGUI();
						wg.setRecievedCommand("T");
						String sTara = doubleFormat.format(tara);
						data.writeTo("T S " + sTara + " kg "+"\r\n");
					}
					else if (inline.startsWith("S")){
						String sNetto = doubleFormat.format((brutto-tara));
						wg.setRecievedCommand("S");
						data.writeTo("S S " + sNetto+ " kg " +"\r\n");
					}
					else if (inline.startsWith("B")){ 
						try
						{
							String temp= inline.substring(2,inline.length());
							brutto = Double.parseDouble(temp);
							updateGUI();
							wg.setRecievedCommand("B");
							data.writeTo("DB"+"\r\n");
						}catch (IndexOutOfBoundsException e) {
							data.writeTo("ES" + "\r\n");
							wg.setRecievedCommand("Ugyldig kommando: "+inline);
						}catch(NumberFormatException e){
							data.writeTo("ES"+"\r\n");
							wg.setRecievedCommand("Ugyldig kommando: "+inline);
						}
					}
					else if ((inline.startsWith("RM20 8"))){
						String[] temp;
						String delimiter = "\"";
						temp = inline.split(delimiter);

						if(temp.length>1)
						{
							
							wg.setMessage(temp[1]);
							wg.setRecievedCommand("Venter paa svar fra RM20 8 ordre");
							wg.enableAnswerBtns(true, false);
							rm20Flag = true;
							data.writeTo("RM20 B\r\n");
						}
						else
							data.writeTo("RM20 L\r\n");
					}
					else if((inline.startsWith("Z")))
					{
						brutto = 0.00;
						tara = 0.00;
						wg.setRecievedCommand("Z");
						updateGUI();
						data.writeTo("Z A\r\n");
					}
					else if((inline.startsWith("I0")))
					{
						data.writeTo("I0 B \"I0\"\r\n");
						data.writeTo("I0 B \"S\"\r\n");
						data.writeTo("I0 B \"T\"\r\n");
						data.writeTo("I0 B \"Z\"\r\n");
						data.writeTo("I0 B \"B\"\r\n");
						data.writeTo("I0 B \"D\"\r\n");
						data.writeTo("I0 B \"DW\"\r\n");
						data.writeTo("I0 B \"K 1\"\r\n");
						data.writeTo("I0 B \"K 3\"\r\n");
						data.writeTo("I0 B \"M12 2\"\r\n");
						data.writeTo("I0 B \"RM20 8\"\r\n");
						data.writeTo("I0 B \"Q\"\r\n");
						wg.setRecievedCommand("I0");
					}
					else if((inline.startsWith("M12 2")))
					{
						java.awt.Toolkit.getDefaultToolkit().beep();
						data.writeTo("M12 A"+"\r\n");
					}
					else if ((inline.startsWith("Q"))){
						break;
					}
					else
					{
						wg.setRecievedCommand("Ugyldig kommando: "+inline);
						data.writeTo("ES"+"\r\n");
					}
				}
			}
			catch (Exception e){}
			try {
				data.closeConnection();
				connect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}