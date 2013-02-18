package data;
import java.util.ArrayList;



public interface IOperatoerDAO {
	User getOperatoer(int oprId) throws DALException;
	ArrayList<OperatoerDTO> getOperatoerList() throws DALException;
	void createOperatoer(User opr) throws DALException;
	void updateOperatoer(User opr) throws DALException;
	void deleteOperatoer(User opr) throws DALException;
	boolean attemptLogin(int ID, String password) throws DALException;
	
	@SuppressWarnings("serial")
	public class DALException extends Exception
	{
		public DALException(int id)
		{
			super("Fejl ved søgning efter bruger med ID: " + id);
		}
	}
}