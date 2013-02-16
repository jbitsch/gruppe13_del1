package Main;
import java.util.List;


public interface IOperatoerDAO {
	User getOperatoer(int oprId) throws DALException;
	List<User> getOperatoerList() throws DALException;
	void createOperatoer(User opr) throws DALException;
	void updateOperatoer(User opr) throws DALException;
	void deleteOperatoer(User opr) throws DALException;
	
	@SuppressWarnings("serial")
	public class DALException extends Exception
	{
		public DALException(int id)
		{
			super("Fejl ved søgning efter bruger med ID: " + id);
		}
	}
}
