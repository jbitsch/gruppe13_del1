package function;

import java.util.ArrayList;
import data.User;
import data.IOperatoerDAO.DALException;

public interface IFunction {
	public ArrayList<User> getUsers(User currentUser) throws DALException;
	public User getUser(int ID) throws DALException;
	public void createUser(String name, String cpr) throws DALException;
	public void updateUser(User user) throws DALException;
	public void deleteUser(User user) throws DALException;
	public boolean login(String password, int ID) throws DALException;
	public boolean checkPassword(String password);
	int unusedId(ArrayList<User> personer);
	public int calculateWeight(int tarra, int brutto);
	
}
