package weightInterface;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import clientSocket.*;

public class WeightController {

	private MySocket mySocket = new MySocket();
	private String ip;
	private int port;
	private boolean programRunning = true;
	private Menu menu;

	private int oNumber;
	private int pNumber;
	private double netto;
	private double tarraS;
	private double tarraE;

	public WeightController(Menu menu)
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


	public void noAnswer()
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


	public void communicate() throws IOException
	{
		int choise = menu.showMenu("", new String[]{"Aflaes vaegt (pc)", "Saet tarra vaegt", "Nulstil vaegt", "Vis tekst paa vaegten", "Slet tekst paa vaegten","Lav afvejnings sekvens", "Afslut program"});

		switch(choise)
		{
		case 1:
		{
			String output = getWeight();
			menu.printOut(output);
			break;
		}
		case 2:
		{
			String weight = getWeight();
			mySocket.sendToServer("T");
			String output = mySocket.recieveFromServer();

			if(output.startsWith("ES"))
				menu.printOut("Det lykkedes ikke at saette tarra vaegten.");
			else
				menu.printOut("Tarra vaegt sat til: " + weight);
			break;
		}
		case 3:
		{
			mySocket.sendToServer("Z");
			String output = mySocket.recieveFromServer();
			if(output.startsWith("Z A"))
				menu.printOut("Vaegten blev nulstillet.");
			else
				menu.printOut("Der skete en fejl. Vaegten blev ikke nulstillet.");
			break;
		}
		case 4:
		{
			menu.printOut("Indtast den besked, du vil skrive paa vaegtens display: ");
			String toServer = menu.getInput();
			mySocket.sendToServer("D \"" + toServer + "\"");
			String output = mySocket.recieveFromServer();
			if(output.startsWith("D A"))
				menu.printOut("Din besked vises nu paa vaegten.");
			else
				menu.printOut("Der opstod en fejl. Beskeden blev ikke vist.");
			break;
		}
		case 5:
		{
			mySocket.sendToServer("DW");
			String output = mySocket.recieveFromServer();
			if(output.startsWith("DW A"))
				menu.printOut("Teksten blev slettet. Vaegten kan nu aflaeses.");
			else
				menu.printOut("Der opstod en fejl. Teksten blev ikke slettet.");
			break;
		}
		case 6:
		{
			sekvens();
			mySocket.sendToServer("DW");
			mySocket.recieveFromServer();
			break;
		}
		case 7:
		{
			programRunning = false;
			break;
		}
		}
	}

	public String getWeight() throws IOException
	{
		mySocket.sendToServer("S");
		String output = mySocket.recieveFromServer();
		return returnNumber(output) + " kg";
	}

	public boolean reEstablishConnection() 
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

	//==========================================
	//Fjerner overfloedige tegn
	private String returnNumber(String output)
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
		return number;
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

	public void sekvens()
	{

		boolean keepRunning = true;
		//get operator ID
		while(true)
		{
			try
			{
				//getting the user id
				System.out.println("Venter paa operatoer numner");
				keepRunning =  getONumber();
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Ugyldig operatoer nummer, sender anmodning igen.");
			}
			catch(IOException e)
			{
				System.out.println("IOException, afbryder sekvens.");
				try
				{
					mySocket.sendToServer("D \"Fejl i forbindelse, sekvens afbrudt\"");
					mySocket.recieveFromServer();
				}
				catch(IOException ee )
				{
					System.out.println("Fejl meddelse blev ikke sendt til vaegt");
				}
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
					//mySocket.sendToServer("D \"Produkt nummer ugyldigt\"");
					pNumber = 0;
				}
				catch(IOException e)
				{
					System.out.println("IOException, sekvens afbrydes");
					pNumber = 0;
					try
					{
						mySocket.sendToServer("D \"Fejl i forbindelse, sekvens afbrudt\"");
						mySocket.recieveFromServer();
					}
					catch(IOException ee )
					{
						System.out.println("Fejl meddelse blev ikke sendt til vaegt");
					}
					keepRunning = false;
					break;
				}
			}
		}

		while(!validateWeight(keepRunning)){}

	}
	private boolean validateWeight(boolean keepRunning)
	{
		boolean weightOk = false;
		if(keepRunning)
		{
			try
			{
				System.out.println("Afventer bruger paasaetter skaal");
				keepRunning = place();
			}
			catch(IOException e){}
		}
		if(keepRunning)
		{
			try
			{
				System.out.println("Afventer bruger paafylder stof");
				keepRunning = fill();
			}
			catch(IOException e){}
		}
		if(keepRunning)
		{
			try
			{
				System.out.println("Afventer bruger fjerner skaal");
				keepRunning = remove();
			}
			catch(IOException e){}
		}
		if(keepRunning)
		{
			boolean bruttoOk = checkBruttoDifference(tarraE);

			if(bruttoOk == true)
			{
				weightOk = true;
				try
				{
					mySocket.sendToServer("D \"OK\"");
					System.out.println("Brutto kontrol OK");
					mySocket.recieveFromServer();

					//Create the time stamp
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String date = dateFormat.format(cal.getTime());
					String time = timeFormat.format(cal.getTime());

					double store = new FileReader().updateStore(netto, pNumber);
					String toLog = date+";"+time+";"+oNumber+";"+pNumber+";"+netto+";"+store;
					new FileReader().updateFile(toLog, "log.txt", true);
				}
				catch(IOException e){}
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
			mySocket.sendToServer("RM20 8 \"Indtast operator nr.\" \"\" \"&3\" ");

			String fromServer = "";
			do 
			{
				fromServer = mySocket.recieveFromServer();
			}while(!fromServer.startsWith("RM20 B"));

			fromServer = mySocket.recieveFromServer();
			if (fromServer.equals("RM20 A \"0\"")){
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
			mySocket.sendToServer("RM20 8 \"Indtast varenr.\" \"\" \"&3\" ");
			String fromServer = mySocket.recieveFromServer(); //RM20 B
			if(fromServer.startsWith("RM20 B"))
			{
				fromServer = mySocket.recieveFromServer(); //RM20 A -----

				if (fromServer.equals("RM20 A \"0\"")){
					pNumber = 0;
					quit = true;
					keepRunning = false;
				}
				else {
					pNumber = returnInt(fromServer.substring(4));
					productName = new FileReader().checkNum(pNumber, "store.txt", 1);

					if(productName!=null)
					{
						String s   = "RM20 8 \""+productName +"(Y,N)\" \"\" \"&1\" ";
						mySocket.sendToServer(s);
						response = mySocket.recieveFromServer(); //RM20 B

						if(response.startsWith("RM20"))
						{
							response = mySocket.recieveFromServer(); //RM20 A------
							if(response.equals("RM20 A \"N\"")) {
								pNumber = 0;
								quit = true;
								keepRunning = false;
							}
							else if(response.equals("RM20 A \"Y\""))
							{
								quit = true;
							}
						}
					}
					else
					{
						mySocket.sendToServer("D \"Ugyldigt produkt nummer\"");
						mySocket.recieveFromServer(); //Clearing the output from the weight
					}
				}	
			}

		}while( quit==false );

		return keepRunning;
	}
	private boolean place() throws IOException {
		boolean keepRunning = true;

		mySocket.sendToServer("RM20 8 \"Place bowl(Y,N)\" \"\" \"\" ");

		if(mySocket.recieveFromServer().startsWith("RM20")) { //RM20 B
			if(mySocket.recieveFromServer().equals("RM20 A \"Y\"")) //RM20 A 1
			{
				//				//TODO remove when testing on the real weight...
				//				mySocket.sendToServer("B 1.00");
				//				mySocket.recieveFromServer();
				//				//

				mySocket.sendToServer("T");
				String output = mySocket.recieveFromServer();
				tarraS = returnDouble(output);
			}
			else
			{
				keepRunning = false;
			}

		}
		else
		{
			keepRunning = false;
		}
		return keepRunning;
	}
	private boolean fill() throws IOException {
		boolean keepRunning = true;

		mySocket.sendToServer("RM20 8 \"Fill the bowl(Y,N)\" \"\" \"\" ");

		if(mySocket.recieveFromServer().startsWith("RM20")) { //RM20 B
			if(mySocket.recieveFromServer().equals("RM20 A \"Y\"")) //RM20 A 1
			{
				//				//TODO remove when testing on the real weight
				//				mySocket.sendToServer("B 2.00");
				//				mySocket.recieveFromServer();
				//				//

				mySocket.sendToServer("S");
				String output = mySocket.recieveFromServer();
				netto = returnDouble(output);
			}
			else
				keepRunning = false;
		}
		else{
			keepRunning = false;
		}
		return keepRunning;
	}
	private boolean remove() throws IOException {
		boolean keepRunning = true;

		mySocket.sendToServer("RM20 8 \"Remove the bowl(Y,N)\" \"\" \"\" ");
		if(mySocket.recieveFromServer().startsWith("RM20")) { //RM20 B
			if(mySocket.recieveFromServer().equals("RM20 A \"Y\"")) //RM20 A Y
			{
				//				//TODO remove when testing on the real weight. 
				//				mySocket.sendToServer("B -1.00");
				//				mySocket.recieveFromServer();
				//				//

				mySocket.sendToServer("T");
				String output = mySocket.recieveFromServer();
				tarraE = returnDouble(output);
			}
			else
				keepRunning = false;
		}
		else
		{
			keepRunning = false;
		}
		return keepRunning;
	}
	public boolean checkBruttoDifference(double bruttoEnd)
	{
		double top = 0.002;
		double bot = -0.002;
		if(bruttoEnd > top || bruttoEnd < bot)
			return false;
		else 
			return true;
	}

}
