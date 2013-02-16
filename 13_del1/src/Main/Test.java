package Main;
import java.util.ArrayList;

import Main.IOperatoerDAO.DALException;

public class Test {
	IOperatoerDAO data;

	public Test()
	{
		data = new Data();
	}
	public void run()
	{
		User user = new Admin(0, "Al Svanesen", "112233-4455",">02324it!<");

		try 
		{
			User us = data.getOperatoer(user.getOprID());
			System.out.println(us.getOprNavn());

		} 
		catch (DALException e) {
			System.out.println(e.getMessage());
		}
		
		try 
		{
			data.createOperatoer(user);
			System.out.println("User created");
		} 
		catch (DALException e) {
			System.out.println(e.getMessage());
		}
		try 
		{
			ArrayList<User> users = data.getOperatoerList();
			for (int i=0; users.size() > i; i++)
			{
				System.out.println(users.get(i).getOprNavn()+" : "+users.get(i).getOprID());
			}

		} 
		catch (DALException e) {
			System.out.println(e.getMessage());
		}

	}
}
