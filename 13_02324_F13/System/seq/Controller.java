package seq;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
	private MySocket2 socketConnect = new MySocket2();
	private Scanner input = new Scanner(System.in);
	private String ip;
	private int port;
	
	public Controller(){
		
	}
	
	public void Con(){
		try
		{
			System.out.println("Indtast ip:");
			ip = input.next();
			System.out.println("Indtast port:");
			port = Integer.parseInt((input.next()));
			socketConnect.connect(ip, port);
		}
		catch (IOException IO)
		{
			System.out.println("Forkert ip/port");
			Con();
		}
		
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
				waitMethod("D \"Tal\"");
			}
			catch(IOException e)
			{
				System.out.println("IOException, afbryder sekvens.");
				waitMethod("D \"Exception\"");
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
					waitMethod("D \"Tal\"");
					pNumber = 0;
				}
				catch(IOException e)
				{
					System.out.println("IOException, sekvens afbrydes");
					pNumber = 0;
					waitMethod("D \"Exception\"");
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
	
	

}
