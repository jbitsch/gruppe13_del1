package weightInterface;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import clientSocket.*;

public class WeightController2 {

	private MySocket2 mySocket = new MySocket2();
	private String ip;
	private int port;
	private boolean programRunning = true;
	private Menu2 menu;

	private int oNumber;
	private int pNumber;
	private double netto;
	private double tarraS;
	private double tarraE;

	public WeightController2(Menu2 menu)
	{
		this.menu = menu;
	}

	public void run()
	{
		menu.printHeader("Indtast port nr.");
		port = Integer.parseInt(menu.getInput());
		menu.printHeader("Indtast ip:");
		ip = menu.getInput(); 

		try
		{
			// Connect
			mySocket.connect(ip, port);
		}
		catch (IOException IO)
		{
			noAnswer();
		}

		while(programRunning)
		{
			try
			{				
				// Input/output
				communicate();
			}
			catch (IOException io)
			{
				noAnswer();
			}
		}
	}

	private void noAnswer()
	{
		boolean menuRunning = true;
		while(menuRunning)
		{
			menu.printOut("Serveren svarer ikke.");
			int choise = menu.showMenu("", new String[]{"Forsoeg igen", "Indtast ny IP og port nr", "Afslut program"});

			switch(choise)
			{
			case 1:
			{
				menuRunning = !reEstablishConnection();
				break;
			}
			case 2:
			{
				menu.printOut("Ip: ");
				ip = menu.getInput();
				menu.printOut("Port: ");
				port = Integer.parseInt(menu.getInput());
				menuRunning = !reEstablishConnection();
				break;
			}
			case 3:
			{
				menuRunning = false;
				programRunning = false;
				break;
			}
			}
		}

	}
	private void communicate() throws IOException
	{
		int choise = menu.showMenu("", new String[]{"Lav afvejnings sekvens", "Afslut program"});

		switch(choise)
		{
		case 1:
		{
			sekvens();
			
			mySocket.sendToServer("DW");
			mySocket.recieveFromServer();
			break;
		}
		case 2:
		{
			programRunning = false;
			break;
		}
		}
	}

	private boolean reEstablishConnection() 
	{
		boolean connected = false;

		try
		{
			mySocket.connect(ip, port);
			connected = true;
		}
		catch (IOException IO)
		{
			menu.printOut("Der kunne ikke oprettes forbindelse.");
		}

		if(connected)
			menu.printOut("Der er oprettet forbindelse til " + ip);

		return connected;
	}

	private void sekvens()
	{
		boolean keepRunning = true;
		//get operator ID
		while(true)
		{
			try
			{
				System.out.println("Venter paa operatoer numner");
				keepRunning =  getONumber();
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Ugyldig operatoer nummer, sender anmodning igen.");
				waitMethod("Skal vaere et tal");
			}
			catch(IOException e)
			{
				System.out.println("IOException, afbryder sekvens.");
				waitMethod("IOException, ");
				pNumber = 0;
				keepRunning = false;
				break;
			}
		}

		//get product name
		if(keepRunning)
		{
			while(true)
			{
				try
				{
					System.out.println("Afventer produktnummer");
					keepRunning = getProductName();
					break;
				}
				catch(NumberFormatException e)
				{
					System.out.println("Ugyldigt ptoduktnummer, sender anmodning igen");
					waitMethod("Skal vaere et tal");
					pNumber = 0;
				}
				catch(IOException e)
				{
					System.out.println("IOException, sekvens afbrydes");
					pNumber = 0;
					waitMethod("IOException, ");
					keepRunning = false;
					break;
				}
			}
		}
		if(keepRunning)
		{
			while(!validateWeight(keepRunning)){}
		}
	}

	private boolean validateWeight(boolean keepRunning)
	{
		boolean weightOk = false;
		try
		{
			System.out.println("Afventer bruger paasaetter skaal");
			keepRunning = place();
		}
		catch(IOException e)
		{
			System.out.println("Fejl ved paasaetelse af skaal");
			keepRunning = false;
		}
		
		if(keepRunning)
		{
			try
			{
				System.out.println("Afventer bruger paafylder stof");
				keepRunning = fill();
			}
			catch(IOException e)
			{
				System.out.println("Fejl ved paafyldning af stof");
				keepRunning = false;
			}
		}
		if(keepRunning)
		{
			try
			{
				System.out.println("Afventer bruger fjerner skaal");
				keepRunning = remove();
			}
			catch(IOException e)
			{
				System.out.println("Fejl ved fjernelse af skaal");
				keepRunning = false;
			}
		}
		if(keepRunning)
		{
			boolean bruttoOk = checkBruttoDifference(tarraE);

			if(bruttoOk == true)
			{
				
				try
				{
					
					double store = new FileReader().updateStore(netto, pNumber);
					if(store>=0)
					{
						//Create the time stamp
						DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						String date = dateFormat.format(cal.getTime());
						String time = timeFormat.format(cal.getTime());
						
						String toLog = date+";"+time+";"+oNumber+";"+pNumber+";"+netto+";"+store;
						new FileReader().updateFile(toLog, "log.txt", true);
						weightOk = true;
						
						System.out.println("Brutto kontrol OK");
						waitMethod("OK, ");
					}
					else
					{
						System.out.println("Ikke nok paa lager");
						waitMethod("-Stock, ");
					}
					
				}
				catch(IOException e){
					System.out.println("Fejl i opdatering af filer");
					waitMethod("F update E, ");
				}
			}
			else
			{
				System.out.println("Fejl i brutto kontrol");
				waitMethod("T error, ");
			}
		}
		if(!keepRunning)
			weightOk = true;

		return weightOk;
	}

	private boolean  getONumber() throws NumberFormatException, IOException
	{
		boolean quit = false;
		boolean keepRunning = true;

		do {
			mySocket.sendToServer("RM20 8 \"Indtast operator nr.( Q )\" \" \" \"&3\" ");

			String fromServer = "";
			do 
			{
				fromServer = mySocket.recieveFromServer().toUpperCase();
			}while(!fromServer.startsWith("RM20 B"));

			fromServer = mySocket.recieveFromServer().toUpperCase();
			if (fromServer.equals("RM20 A \"Q\"") || fromServer.equals("RM20 A Q")){
				oNumber = 0;
				quit = true;
				keepRunning = false;
			}
			else {
				oNumber = returnInt(fromServer.substring(4));
				String Onum = new FileReader().checkNum(oNumber, "users.txt", 0);
				if (Onum!=null)
				{
					quit = true;
				}
				else
					waitMethod("Ugyldigt operator nr., ");
			}
		}while( quit==false );

		return keepRunning;
	}

	private boolean getProductName() throws NumberFormatException, IOException {
		String response;
		String productName = null;

		boolean quit = false;
		boolean keepRunning = true;;

		do {
			mySocket.sendToServer("RM20 8 \"Indtast varenr. ( Q )\" \" \" \"&3\" ");
			String fromServer = mySocket.recieveFromServer().toUpperCase(); //RM20 B
			if(fromServer.startsWith("RM20 B"))
			{
				fromServer = mySocket.recieveFromServer().toUpperCase(); //RM20 A -----
				if (fromServer.equals("RM20 A \"Q\"") || fromServer.equals("RM20 A Q")){
					pNumber = 0;
					quit = true;
					keepRunning = false;
				}
				else {
					pNumber = returnInt(fromServer.substring(4));
					productName = new FileReader().checkNum(pNumber, "store.txt", 1);

					if(productName!=null)
					{
						String s   = "RM20 8 \""+productName +"(Y,N,Q)\" \" \" \"&1\" ";
						mySocket.sendToServer(s);
						response = mySocket.recieveFromServer().toUpperCase(); //RM20 B

						if(response.startsWith("RM20"))
						{
							response = mySocket.recieveFromServer().toUpperCase(); //RM20 A------
							if(response.equals("RM20 A \"Q\"") || response.equals("RM20 A Q")) {
								pNumber = 0;
								quit = true;
								keepRunning = false;
							}
							else if(response.equals("RM20 A \"Y\"") || response.equals("RM20 A Y"))
							{
								quit = true;
							}
							else if(response.equals("RM20 A \"N\"") || response.equals("RM20 A N"))
							{
								//do nothing
							}
							else
								waitMethod("D \"Wrong intput, ");
						}
					}
					else
					{
						waitMethod("D \"Ugyldigt produktnr., ");
					}
				}	
			}

		}while( quit==false );

		return keepRunning;
	}
	private boolean place() throws IOException {
		boolean keepRunning = true;
		boolean isDone = false;
		do
		{
			mySocket.sendToServer("RM20 8 \"Place bowl(Y,N)\" \" \" \" \" ");

			if(mySocket.recieveFromServer().startsWith("RM20")) { //RM20 B
				String response = mySocket.recieveFromServer().toUpperCase();
				if(response.equals("RM20 A \"Y\"") || response.equals("RM20 A Y") ) //RM20 A Y
				{
					//				//TODO remove when testing on the real weight...
					//				mySocket.sendToServer("B 1.00");
					//				mySocket.recieveFromServer();
					//				//

					mySocket.sendToServer("T");
					String output = mySocket.recieveFromServer();
					tarraS = returnDouble(output);
					
					isDone = true;
				}
				else if(response.equals("RM20 A \"N\"") || response.equals("RM20 A N"))
				{
					keepRunning = false;
					isDone = true;
				}
				else
					waitMethod("D \"Wrong input, ");
			}
			else
			{
				keepRunning = false;
				isDone = true;
			}
		}while(!isDone);

		return keepRunning;
	}

	private boolean fill() throws IOException {
		boolean keepRunning = true;
		boolean isDone = false;
		do
		{
			mySocket.sendToServer("RM20 8 \"Fill the bowl(Y,N)\" \" \" \" \" ");

			if(mySocket.recieveFromServer().startsWith("RM20")) { //RM20 B
				String response  = mySocket.recieveFromServer().toUpperCase();
				if(response.equals("RM20 A \"Y\"") || response.equals("RM20 A Y")) //RM20 A Y
				{
					//				//TODO remove when testing on the real weight
					//				mySocket.sendToServer("B 2.00");
					//				mySocket.recieveFromServer();
					//				//

					mySocket.sendToServer("S");
					String output = mySocket.recieveFromServer();
					netto = returnDouble(output);
					
					isDone = true;
				}
				else if(response.equals("RM20 A \"N\"") || response.equals("RM20 A N"))
				{
					keepRunning = false;
					isDone = true;
				}
				else
					waitMethod("D \"Wrong input, ");
			}
			else{
				keepRunning = false;
				isDone = true;
			}
		}while(!isDone);

		return keepRunning;
	}

	private boolean remove() throws IOException {
		boolean keepRunning = true;
		boolean isDone = false;
		do
		{
			mySocket.sendToServer("RM20 8 \"Remove the bowl(Y,N)\" \" \" \" \" ");
			if(mySocket.recieveFromServer().startsWith("RM20")) { //RM20 B

				String response = mySocket.recieveFromServer().toUpperCase();

				if(response.equals("RM20 A \"Y\"") || response.equals("RM20 A Y")) //RM20 A Y
				{
					//				//TODO remove when testing on the real weight. 
					//				mySocket.sendToServer("B -1.00");
					//				mySocket.recieveFromServer();
					//				//

					mySocket.sendToServer("T");
					String output = mySocket.recieveFromServer();
					tarraE = returnDouble(output);

					isDone = true;
				}
				else if(response.equals("RM20 A \"N\"") || response.equals("RM20 A N"))
				{
					keepRunning = false;
					isDone = true;
				}
				else
					waitMethod("D \"Wrong input, ");
			}
			else
			{
				keepRunning = false;
				isDone = true;
			}
		}while(!isDone);
		return keepRunning;
	}
	private boolean checkBruttoDifference(double bruttoEnd)
	{
		double top = 0.002;
		double bot = -0.002;
		if(bruttoEnd > top || bruttoEnd < bot)
			return false;
		else 
			return true;
	}
	private double returnDouble(String output) throws NumberFormatException
	{
		String number = "";
		char[] outputArray = output.toCharArray();
		for(char character: outputArray)
		{
			if((character >= '0' && character <= '9') || character == '.')
			{
				number = number + character;
			}
		}
		return Double.parseDouble(number);
	}
	private int returnInt(String output) throws NumberFormatException
	{
		String number = "";
		char[] outputArray = output.toCharArray();
		for(char character: outputArray)
		{
			if(character >= '0' && character <= '9')
			{
				number = number + character;
			}
		}
		return Integer.parseInt(number);
	}
	private void waitMethod(String text)
	{
		for (int i=5; i>  0; i--)
		{
			try
			{
				mySocket.sendToServer(text+i+"\"");
				mySocket.recieveFromServer();
				Thread.sleep(1000);
			}
			catch(IOException e)
			{

			}
			catch(InterruptedException e)
			{

			}
		}
	}

}
