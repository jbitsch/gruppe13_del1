package function;

import java.util.ArrayList;
import java.util.Collections;

import data.IOperatoerDAO;
import data.OperatoerDTO;
import data.IOperatoerDAO.DALException;
import data.User;

	

public class Function implements IFunction{
	IPasswordGenerator pwGenerator;
	IOperatoerDAO data;
	public Function(IOperatoerDAO data)
	{
		this.data = data;
		pwGenerator = new PasswordGenerator();
	}
	@Override
	public ArrayList<User> getUsers(User currentUser) throws DALException
	{
		
		ArrayList<User> users = data.getOperatoerList();	
        Collections.sort(users); //sort the arrayList after ID
        //removing the current person
        for(int i=0; users.size()> i; i++)
        {
        	if (users.get(i).getOprID()==currentUser.getOprID())
        	{
        		users.remove(i);
        	}
        }
		return users;
	}
	@Override
	public User getUser(int ID) throws DALException
	{
		return data.getOperatoer(ID);
	}
	@Override
	public boolean checkPassword(String password)
	{
		boolean passwordOk = pwGenerator.acceptablePassword(password);
		return passwordOk;
	}
	
	@Override
	public void createUser(String name, String cpr) throws DALException
	{
		String password = pwGenerator.generateRandomPassword();
		int id = unusedId(data.getOperatoerList());
		User newUser = new OperatoerDTO(id, name, cpr,password);
		data.createOperatoer(newUser);
	}
	@Override
	public void updateUser(User user) throws DALException
	{
		data.updateOperatoer(user);
	}
	@Override
	public void deleteUser(User user) throws DALException
	{
		data.deleteOperatoer(user);
	}
	@Override
	public boolean login(String password, int ID) throws DALException
	{
		boolean loginOk = data.attemptLogin(ID, password);
		return loginOk;
	}
	public boolean checkOldPassword(User user, String password)
	{
		boolean passwordOk = false;
		if(user.getPassword().equals(password))
		{
			passwordOk = true;
		}
		return passwordOk;
	}
	public int unusedId(ArrayList<User> personer) {
		boolean emptyId;
		for(int b = 11; b < 99; b++) {
			emptyId = true;
			for(int c = 0; c < personer.size(); c++) {
				if(b == personer.get(c).getOprID()) {
					emptyId = false;
					break;
				}
			}
			if(emptyId){
				return b;
			}
		}
		return 0;
	}
	public int calculateWeight(int tarra, int brutto)
	{
		int netto = brutto - tarra;
		if(netto >= 0)
			return netto;
		else
			return -1;
	}
	
}
