package funktionalitet;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public int createUser(String name, String cpr,String ini,String password) 
	{
		int id = unusedId(data.getAllOperatoer());
		OperatoerDTO2 newUser = new OperatoerDTO2(id, name,ini, cpr,password);
		data.createOperatoer(newUser);
		
		return id;
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
	
	/**
	 * Checks whether or not a given INPUT {@link String} matches the Regular
	 * Expression {@link java.util.regex.Pattern}.
	 *   
	 * @param REGEX		the pattern used to check the INPUT String 
	 * @param INPUT		the String that {@link java.util.regex.Matcher} matches against REGEX
	 * @return			true if the REGEX matches the INPUT String
	 */
	private boolean checkRegex(String REGEX, String INPUT) {
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(INPUT);
		
		return matcher.matches();
	}
	
	
	@Override
	/** Operat�r navn min. 2 max.. 20 karakterer */
	public boolean checkName(String navn)
	{
		String REGEX = "^[a-zA-Z[\\s]]{2,20}$+";
		return checkRegex(REGEX,navn);
	}
	
	@Override
	/** Operat�r initialer min. 2 max. 3 karakterer */
	public boolean checkIni(String ini)
	{
		String REGEX ="^[a-zA-Z[\\s]]{2,3}$+";
		return checkRegex(REGEX, ini);
	}
	
	@Override
	/** Operat�r cpr-nr 10 karakterer */
	public boolean checkCpr(String cpr)
	{
		String REGEX = "^[0-9]{10,10}$+";
		return checkRegex(REGEX, cpr);
	}
	
	@Override
	/** Operat�r password min. 7 max. 8 karakterer */
	public boolean checkPassword(String password)
	{
		String REGEX = "^[a-zA-Z[\\-\\.\\+\\?[_!=[\\s]]]]{7,8}$+";
		return checkRegex(REGEX, password);
	}

}
