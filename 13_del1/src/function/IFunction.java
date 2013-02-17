package function;

import java.util.ArrayList;

import data.OperatoerDTO;
import data.User;
import data.IOperatoerDAO.DALException;

public interface IFunction {
	public ArrayList<OperatoerDTO> getUsers() throws DALException;
	public User getUser(int ID) throws DALException;
	public void createUser(String name, String cpr) throws DALException;
	public void updateUser(User user) throws DALException;
	public void deleteUser(User user) throws DALException;
	public boolean login(String password, int ID) throws DALException;
	
}
