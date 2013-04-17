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
		int choise = menu.showMenu("", new String[]{"Aflaes vaegt (pc)", "Saet tarra vaegt", "Nulstil vaegt", "Vis tekst paa vaegten", "Slet tekst paa vaegten","Send RM20 ordre", "Afslut program"});

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
			menu.printOut("indtast 4 eller 8: ");
			String numSend = menu.getInput();
			menu.printOut("Tekst der skal sendes");
			String text = menu.getInput();
			mySocket.sendToServer("RM20 "+numSend+" \""+text+"\" \" \" \" \" ");
			String output = mySocket.recieveFromServer();
			if(output.startsWith("RM20 L"))
				System.out.println("Besked ikke forstaaet");
			else
			{
				output = mySocket.recieveFromServer();
				System.out.println("svar på RM20 ordre: "+output);
			
			}
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
}
	
