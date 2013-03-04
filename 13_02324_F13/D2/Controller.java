
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

	public void run()
	{
		menu.startMenu(portdst);
		
		try{
			String in  = data.getCon(portdst);
			printMenu();
			
			System.out.println(in);
			
			while (!(inline = in.toUpperCase()).isEmpty()){
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
					//ikke på en fysisk vægt
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

}
