import java.util.ArrayList;
import java.util.List;

import bmi.Data1.Person;



public class Data implements IOperatoerDAO {
	
	private ArrayList<User> personer;
	
	
	public Data()
	{
		personer = new ArrayList<Person>();
		
		// Tilføj personer
		personer.add(new OperatorDTO("234567-8901", "Ib Olsen", 1.80, 75.0));
		personer.add(new OperatorDTO("456789-0123", "Ole Jensen", 1.75, 95.0));
		personer.add(new OperatorDTO("123456-7890", "Eva Hansen", 1.65, 65.0));
		personer.add(new OperatorDTO("111111-1111", "Peter Jensen", 1.95, 55.0));
		personer.add(new ("222222-2222", "Albert Svanesen", 1.65, 65.0));

	}
	
	
	public User getOperatoer(int oprId) throws DALException
	{
		return personer.get(0);
	}
	
//	List<User> getOperatoerList() throws DALException;
//	void createOperatoer(User opr) throws DALException;
//	void updateOperatoer(User opr) throws DALException;
}
