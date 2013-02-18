package function;

import java.util.ArrayList;

import data.IOperatoerDAO;
import data.OperatoerDTO;
import data.IOperatoerDAO.DALException;
import data.User;

public class Function {
	IOperatoerDAO data;
	public Function(IOperatoerDAO data)
	{
		this.data = data;
	}
	public ArrayList<OperatoerDTO> getUsers() throws DALException
	{
		return data.getOperatoerList();
	}
	public User getUser(int ID) throws DALException
	{
		return data.getOperatoer(ID);
	}
	public boolean checkPassword()
	{
		boolean passwordOk = false; //TODO Password tjek....
		return passwordOk;
	}
	
	public void createUser(String name, String cpr) throws DALException
	{
		String password = ""; //TODO Modtag nyt password fra programmet. 
		User newUser = new OperatoerDTO(0, name, cpr,password);
		data.createOperatoer(newUser);
	}
	public void updateUser(User user) throws DALException
	{
		data.updateOperatoer(user);
	}
	public void deleteUser(User user) throws DALException
	{
		data.deleteOperatoer(user);
	}
	public boolean login(String password, int ID) throws DALException
	{
		boolean loginOk = data.attemptLogin(ID, password);
		return loginOk;
	}
	
}
