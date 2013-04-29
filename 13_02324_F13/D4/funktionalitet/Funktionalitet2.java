package funktionalitet;

import java.util.ArrayList;
import data.IData2;
import data.OperatoerDTO2;


public class Funktionalitet2 implements IFunktionalitet2 {
	private IData2 data;
	private IPasswordGenerator2 pwGenerator;

	public Funktionalitet2()
	{
		pwGenerator = new PasswordGenerator2();
	}
	public Funktionalitet2(IData2 d){
		this.data= d;
		pwGenerator = new PasswordGenerator2();
	}
	public IData2 getD() {
		return data;
	}
	public void setD(IData2 d) {
		this.data = d;
	}
	
	@Override
	public ArrayList<OperatoerDTO2> getUsers()
	{
		ArrayList<OperatoerDTO2> users = data.getOperatoerList();	
		return users;
	}
	@Override
	public OperatoerDTO2 getUser(int ID) {
		return data.getOperatoer(ID);
	}
	@Override
	public boolean checkPassword(String password, int id)
	{	
		if(password.length() > 8 || password.length() <7)
			return false;
		
		OperatoerDTO2 user = data.getOperatoer(id);
		user.setPassword(password);
		data.updateOperatoer(user);
		return true;
	}
	
	@Override
	public void createUser(String name, String cpr,String ini,String password) 
	{
		int id = unusedId(data.getAllOperatoer());
		OperatoerDTO2 newUser = new OperatoerDTO2(id, name,ini, cpr,password);
		data.createOperatoer(newUser);
	}
	@Override
	public void updateUser(int id, String navn, String ini, String cpr, String password)
	{
		OperatoerDTO2 user = data.getOperatoer(id);
		user.setOprNavn(navn);
		user.setIni(ini);
		user.setCpr(cpr);
		user.setPassword(password);
		
		data.updateOperatoer(user);
	}
	@Override
	public void deleteUser(OperatoerDTO2 user)
	{
		data.deleteOperatoer(user);
	}
	@Override
	public boolean login(String password, String ID) 
	{
		try
		{
			int id = Integer.parseInt(ID);
			boolean loginOk = data.attemptLogin(id, password);
			return loginOk;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	@Override
	public boolean checkOldPassword(OperatoerDTO2 user, String password)
	{
		boolean passwordOk = false;
		if(user.getPassword().equals(password))
		{
			passwordOk = true;
		}
		return passwordOk;
	}
	@Override
	public int unusedId(ArrayList<OperatoerDTO2> personer) {
		boolean emptyId;
		for(int b = 11; b < 99; b++) {
			emptyId = true;
			for(int c = 0; c < personer.size(); c++) {
				if(b == personer.get(c).getOprId()) {
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
	@Override
	public double calculateWeight(double tarra, double brutto)
	{
		double netto = brutto - tarra;
		if(netto >= 0)
			return netto;
		else
			return -1;
	}
	
	//#################Check user information#########################//
	@Override
	/** Operatør navn min. 2 max. 20 karakterer */
	public boolean checkName(String navn)
	{
		if(navn.length() < 2 || navn.length() > 20)
			return false;
		return true;
	}
	
	@Override
	/** Operatør initialer min. 2 max. 3 karakterer */
	public boolean checkIni(String ini)
	{
		if(ini.length() < 2 || ini.length() > 3)
			return false;
		return true;
	}
	
	@Override
	/** Operatør cpr-nr 10 karakterer */
	public boolean checkCpr(String cpr)
	{
		if(cpr.length()==10)
			return true;
		return false;
	}
	
	@Override
	/** Operatør password min. 7 max. 8 karakterer */
	public boolean checkPassword(String password)
	{
		if(password.length() < 7 || password.length() > 8)
			return false;
		return true;
	}
	


}
