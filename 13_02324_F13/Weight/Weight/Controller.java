package Weight;

import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {

	private static double brutto=0;
	private static double tara=0;
	private static String inline="";
	private static String IndstruktionsDisplay= "";
	private static int portdst;
	private boolean rm20Flag = false;
	private WeightGUI wg;
	private Data data;
	private DecimalFormat brutoFormat;

	public Controller(WeightGUI wg, Data data) {
		this.wg = wg;
		this.data = data;
		brutoFormat = new DecimalFormat("0.000");

	}

	public synchronized void run(int port)
	{
		portdst = port;	
	
		try
		{
			System.out.println("Lytter paa port "+ portdst);
			data.getCon(portdst);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		wg.setVisible(true);

		new Thread(new PhysicalScaleSim()).start();

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
					wg.setK3btn(true);
					wg.writeCommand("K 3");
					data.writeTo("K A"+"\r\n");
				}
				else if(inline.startsWith("K 1"))
				{
					wg.setK3btn(false);
					wg.writeCommand("K 1");
					data.writeTo("K A"+"\r\n");
				}
				else if (inline.startsWith("DW")){
					IndstruktionsDisplay="";
					wg.writeCommand("DW");
					wg.setMessage(IndstruktionsDisplay);
					data.writeTo("DW A"+"\r\n");
				}
				else if (inline.startsWith("D")){
					if (inline.equals("D"))
						IndstruktionsDisplay="";
					else
						IndstruktionsDisplay=(inline.substring(2,inline.length()));
					
					wg.writeCommand("D");
					wg.setMessage(IndstruktionsDisplay);
					data.writeTo("D A"+"\r\n");
				}
				else if (inline.startsWith("T")){
					tara = brutto;
					updateGUI();
					wg.writeCommand("T");
					String sTara = brutoFormat.format(tara);
					data.writeTo("T S " + sTara + " kg "+"\r\n");
				}
				else if (inline.startsWith("S")){
					String sNetto = brutoFormat.format((brutto-tara));
					wg.writeCommand("S");
					data.writeTo("S S " + sNetto+ " kg " +"\r\n");
				}
				else if (inline.startsWith("B")){ 
					try
					{
						String temp= inline.substring(2,inline.length());
						brutto = Double.parseDouble(temp);
						updateGUI();
						wg.writeCommand("B");
						data.writeTo("DB"+"\r\n");
					}catch (IndexOutOfBoundsException e) {
						data.writeTo("ES" + "\r\n");
						wg.writeCommand("Ugyldig kommando: "+inline);
					}catch(NumberFormatException e){
						data.writeTo("ES"+"\r\n");
						wg.writeCommand("Ugyldig kommando: "+inline);
					}
				}
				else if ((inline.startsWith("RM20 8"))){
					String[] temp;
					String delimiter = "\"";
					temp = inline.split(delimiter);

					if(temp.length>1)
					{
						data.writeTo("RM20 B\r\n");
						wg.setMessage(temp[1]);
						wg.writeCommand("Venter paa svar fra RM20 8 ordre");
						wg.setEdit(true, false);
						rm20Flag = true;
					}
					else
						data.writeTo("RM20 L\r\n");
				}
				else if((inline.startsWith("Z")))
				{
					brutto = 0.00;
					tara = 0.00;
					wg.writeCommand("Z");
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
					data.writeTo("I0 B \"RM20 8\"\r\n");
					data.writeTo("I0 B \"Q\"\r\n");
					wg.writeCommand("I0");
				}
				else if ((inline.startsWith("Q"))){
					wg.dispose();
					System.in.close();
					System.out.close();
					data.closeCon();
					System.exit(0);
				}
				else
				{
					wg.writeCommand("Ugyldig kommando: "+inline);
					data.writeTo("ES"+"\r\n");
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private class PhysicalScaleSim implements Runnable {

		public void run() {
			choosePhysicalAction();
		}

		private void choosePhysicalAction() {
			try{
				while (true){

					char input = wg.getInput();

					switch(input)
					{
					case 'E':
						String retur = wg.getAnswer();
						try {
							data.writeTo("RM20 A \""+retur+"\"\r\n");

						} catch (IOException e1) {
							e1.printStackTrace();
						}
						wg.setEdit(false, true);
						wg.setMessage("");
						wg.writeCommand("");
						rm20Flag = false;
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
						brutto = wg.getBrutto();
						updateGUI();
						break;
					case 'Q':
						wg.dispose();
						System.in.close();
						System.out.close();
						data.closeCon();
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
	}
	private void updateGUI()
	{	
		String sBrutto = brutoFormat.format(brutto);
		String sNetto = brutoFormat.format((brutto-tara));
		String sTara = brutoFormat.format(tara);
		wg.updateWeight(sNetto, sBrutto, sTara);
	}
}
