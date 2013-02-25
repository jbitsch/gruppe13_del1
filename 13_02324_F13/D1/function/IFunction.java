package function;

import java.util.ArrayList;
import data.User;
import data.IOperatoerDAO.DALException;

public interface IFunction {
	ArrayList<User> getUsers(User currentUser) throws DALException;
	User getUser(int ID) throws DALException;
	void createUser(String name, String cpr) throws DALException;
	void updateUser(User user) throws DALException;
	void deleteUser(User user) throws DALException;
	boolean login(String password, int ID) throws DALException;
	int unusedId(ArrayList<User> personer);
	boolean checkOldPassword(User user, String password);
	double calculateWeight(double tarra, double brutto);
	boolean checkPassword(String password, User user);
	
}
