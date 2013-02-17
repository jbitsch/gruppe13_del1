package data;

import java.util.List;


public interface IOperatoerDAO {
	User getOperatoer(int oprId) throws DALException;
	List<User> getOperatoerList() throws DALException;
	void createOperatoer(User opr) throws DALException;
	void updateOperatoer(User opr) throws DALException;
	void deleteOperatoer(User opr) throws DALException;
	
	public class DALException extends Exception
	{
		public DALException(int id)
		{
			super("Fejl ved login med bruger: " + id);
		}
	}
}
