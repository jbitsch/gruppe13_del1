package Weight;

import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {

	private static double brutto=0;
	private static double tara=0;
	private static String inline="";
	private static String IndstruktionsDisplay= "";
	private static int portdst;
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
			System.out.println("Lytter p� port "+ portdst);
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
				inline = data.getInput().toUpperCase();
				
				if (inline.startsWith("DW")){
					IndstruktionsDisplay="";
					wg.writeCommand("DW");
					wg.setMessage(IndstruktionsDisplay);
					data.writeTo("DW"+"\r\n");
				}
				else if (inline.startsWith("D")){
					if (inline.equals("D"))
						IndstruktionsDisplay="";
					else
						IndstruktionsDisplay=(inline.substring(2,
								inline.length()));
					wg.writeCommand("D");
					wg.setMessage(IndstruktionsDisplay);
					data.writeTo("DB"+"\r\n");
				}
				else if (inline.startsWith("T")){
					String sTara = brutoFormat.format(tara);
					data.writeTo("T " + sTara + " kg "+"\r\n");
					tara = brutto;
					updateGUI();
					wg.writeCommand("T");

				}
				else if (inline.startsWith("S")){
					String sNetto = brutoFormat.format((brutto-tara));
					wg.writeCommand("S");
					data.writeTo("S " + sNetto+ " kg " +"\r\n");
				}
				else if (inline.startsWith("B")){ 
					String temp= inline.substring(2,inline.length());
					try
					{
						brutto = Double.parseDouble(temp);
						updateGUI();
						wg.writeCommand("B");
						data.writeTo("DB"+"\r\n");
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
					 wg.setMessage(temp[1]);
					 wg.writeCommand("Venter p� svar fra RM20 8 ordre");
					 wg.setEdit(true, false);	 
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
			System.out.println("Exception: "+e.getMessage());
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
						int retur = wg.getAnswer();
						try {
							data.writeTo("RM20 A "+retur+"\r\n");

						} catch (IOException e1) {
							e1.printStackTrace();
						}
						wg.setEdit(false, true);
						wg.setMessage("");
						wg.writeCommand("");
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
