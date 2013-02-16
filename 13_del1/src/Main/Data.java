package Main;
import java.util.ArrayList;
import java.util.Scanner;

public class Data implements IOperatoerDAO {
	
	private ArrayList<User> personer;
	
	public Data()
	{
		personer = new ArrayList<User>();
		
		// Tilføj personer
		personer.add(new OperatoerDTO(1, "Ib Olsen", "112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO(2, "Ole Jensen", "112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO(3, "Eva Hansen", "112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO(4, "Peter Jensen", "112233-4455","12Qwerty"));
		personer.add(new Admin(10, "Albert Svanesen", "112233-4455",">02324it!<"));
	}
	
	public User getOperatoer(int oprId) throws DALException
	{
		int i = 0;
		try 
		{
			while (true)
			{
				if (personer.get(i).getOprID()==oprId)
				{
					return personer.get(i);
				}
				i++;
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new DALException(oprId);
		}
	}
	
	public ArrayList<User> getOperatoerList() throws DALException
	{
		return personer;
	}
	
	public void createOperatoer(User opr) throws DALException
	{
		int max = 0;
		int i = 0;
		try 
		{
			while (true)
			{
				if (personer.get(i).getOprID() >= max)
				{
					max = personer.get(i).getOprID();
				}
				i++;
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new DALException(opr.getOprID());
		}	

//		opr.setOprID(max+1);
//		personer.add(opr);
	}
	public void updateOperatoer(User opr) throws DALException
	{	
		int i = 0;
		try 
		{
			while (true)
			{
				if(personer.get(i).getOprID()==opr.getOprID())
				{
					personer.set(i, opr);
					break;
				}
				i++;
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new DALException(opr.getOprID());
		}			
	}
	public void deleteOperatoer(User opr) throws DALException
	{
		int i = 0;
		try 
		{
			while (true)
			{
				if(personer.get(i).getOprID()==opr.getOprID())
				{
					personer.remove(i);
					break;
				}
				i++;
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new DALException(opr.getOprID());
		}
	}
	
	public void attemptLogin() {
		Scanner input = new Scanner(System.in);
		int enteredID = 0;
		String enteredPassword = "";
		boolean go = true;
		
		System.out.print("Enter your user ID: ");
		while(go){
			String str = input.nextLine();
			try{
				enteredID = Integer.parseInt(str);
				go = false;
			} catch (Exception e){
				System.out.println("Enter a number!");
			}
		}
		
		System.out.print("Enter your password: ");
		enteredPassword = input.nextLine();
		
		for (User u: personer){
			if (u.oprID == enteredID)
				if (u.password.equals(enteredPassword)){
					System.out.println("Hello " + u.oprNavn);
				} else {
					System.out.println("Wrong password");
				}
		}
	
		input.close();
	}
}
