package data;
import java.util.ArrayList;



public class Data implements IOperatoerDAO {
	
	private ArrayList<User> personer;

	public Data()
	{
		personer = new ArrayList<User>();

		// Tilføj personer
		personer.add(new OperatoerDTO(11, "Ib Olsen", "112233-4455","12"));
		personer.add(new OperatoerDTO(13, "Ole Jensen", "112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO(12, "Eva Hansen", "112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO(14, "Peter Jensen", "112233-4455","12Qwerty"));
		personer.add(new Admin(10, "Albert Svanesen", "112233-4455",">02324it!<"));
		personer.add(new Admin(1, "Albert Svanesen", "112233-4455","12"));
	}

	public User getOperatoer(int oprId) throws DALException
	{
		User person = null;
		boolean personFound = false;
		for (int i = 0; personer.size() > i; i++)
		{
			if (personer.get(i).getOprID()==oprId)
			{
				person =  personer.get(i);
				personFound = true;
			}
		}
		if(!personFound)
		{
			throw new DALException(oprId);
		}
		return person;
	}

	public ArrayList<User> getOperatoerList() throws DALException
	{

		return personer;
	}

	public void createOperatoer(User opr) throws DALException
	{
		personer.add(opr);
	}
	public void updateOperatoer(User opr) throws DALException
	{	
		boolean updateOk = false;
		for (int i = 0; personer.size() > i; i++)
		{
			if(personer.get(i).getOprID()==opr.getOprID())
			{
				personer.set(i, opr);
				updateOk = true;
				break;
			}
		}
		if(!updateOk)
		{
			throw new DALException(opr.getOprID());		
		}
	}
	public void deleteOperatoer(User opr) throws DALException
	{
		boolean deleteOk = false;
		for (int i = 0; personer.size() > i; i++)
		{
			if(personer.get(i).getOprID()==opr.getOprID())
			{
				personer.remove(i);
				deleteOk = true;
				break;
			}
		}
		if(!deleteOk)
		{
			throw new DALException(opr.getOprID());		
		}
	}
	
	public boolean attemptLogin(int ID, String password) throws DALException {
		boolean loginOk = false;
	
		for (int i = 0; personer.size() > i; i++)
		{
			if(personer.get(i).getOprID()==ID)
			{
				if (personer.get(i).getPassword().equals(password)){
					loginOk = true;
					break;
				} 
			}
		}
	
		if(!loginOk)
		{
			throw new DALException(ID);		
		}
		return loginOk;
	}
	

	
	
	
}
