package data;

import java.util.ArrayList;
import java.util.List;

import data.IOperatoerDAO.DALException;



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
		return personer.get(0);
	}
	
	public ArrayList<User> getOperatoerList() throws DALException
	{
		return personer;
	}
	public void createOperatoer(User opr) throws DALException
	{
		
	}
	public void updateOperatoer(User opr) throws DALException
	{
		
	}
	public void deleteOperatoer(User opr) throws DALException
	{
		
	}
}
