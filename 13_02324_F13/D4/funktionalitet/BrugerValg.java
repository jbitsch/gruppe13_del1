package funktionalitet;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.IData2;
import data.OperatoerDTO2;
import exceptions.BigTaraException;

public class BrugerValg {
	
	public String handling="";
	public String error = "";
	public String succes = "";
	public IData2 data = null;
	
	//Weight
	public double t = 0.00;
	public double b = 0.00;
	public double netto = 0.00;
	
	//Change password
	public String new1 = "";
	public String new2 = "";
	public String old = "";
	public int id = 0;
	
	//New user
	public String name = "";
	public String ini = "";
	public String cpr ="";
	public String password ="";
	
	public void setData(IData2 data)
	{
		this.data = data;
	}
	public void setHandling(String h) {
		handling = h;
	}
	public ArrayList<OperatoerDTO2> getUsers()
	{
		return data.getAllOperatoer();
	}
	public void delete()
	{
		t = 0.00;
		b = 0.00;
		netto = 0.00;
		
		new1 = "";
		new2 = "";
		old = "";
		id = 0;
		
		name = "";
		ini = "";
		cpr = "";
		password = "";
	}
	////////////////////////////////////////////
	public void setBrutto(String brutto)
	{
		b = Double.parseDouble(brutto);
	}
	public void setTarra(String tarra)
	{
		t = Double.parseDouble(tarra);
	}
	//////////////////////////////////////////
	public void setOld(String old)
	{
		this.old = old;
	}
	public void setNew1(String new1)
	{
		this.new1= new1;
	}
	public void setNew2(String new2)
	{
		this.new2= new2;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	///////////////////////////////////////////
	public void setName(String name)
	{
		this.name = name;
	}
	public void setIni(String ini)
	{
		this.ini = ini;
	}
	public void setCpr(String cpr)
	{
		this.cpr = cpr;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	//////////////////////////////////////////////
	public void setUser(int uId)
	{
		OperatoerDTO2 user = data.getOperatoer(uId);
		name = user.getOprNavn();
		ini = user.getIni();
		password = user.getPassword();
		cpr = user.getCpr();
		id = user.getOprId();
	}
	
	////////////////////////////////////////////////
	public void udfoerHandling() 
	{

		succes = "";
		error ="";
		try
		{
			if (handling.equals("changePw"))
			{
				changePassword(old,new1, new2,id);
			}
			else if (handling.equals("weight"))
			{	
				netto = b - t;
				if(netto < 0)
				{
					netto =  0;
					error = "Tarra skal være større end brutto";
					//throw new BigTaraException();
				}
				t = 0.00;
				b = 0.00;
			}
			else if (handling.equals("Opret bruger") || handling.equals("Ænndre"))
			{
				createUser(name, ini, cpr,password);
			}
			else if(handling.equals("Slet"))
			{
				data.deleteOperatoer(data.getOperatoer(id));
				delete();
			}
			else
				System.out.println("Ukendt handling: " + handling);
		}
		finally
		{
			handling = null;
		}
	}
	private void changePassword(String old, String new1, String new2,int id)
	{
		OperatoerDTO2 user = data.getOperatoer(id);
		boolean OK = true;
		if(!(user.getPassword().equals(old)))
		{
			error+= "Gammelt password stemmer ikke overens.<br> ";
			OK = false;
		}
		
		if(new1.equals(new2))
		{
			if(!checkPassword(new1))
			{
				error+="De nye passwords overholder ikke reglerne. <br>";
				OK = false;
			}
		}
		else
		{
			error+= "De 2 nye passwords er ikke ens. <br>";
			OK = false;
		}
		if(OK)
		{
			user.setPassword(new1);
			data.updateOperatoer(user);
		}
		
	}
	
	private void createUser(String name, String ini, String cpr, String password)
	{
		boolean ok = true;
		
		if(!checkName(name))
		{
			ok = false;
			error+= "Navnet skal være mellem 2 og 20 karaktere.(a-z) <br> ";
		}
		if(!checkIni(ini))
		{
			ok = false;
			error+= "Initialer skal være mellem 2 og 3 karaktere. <br> ";
		}
		if(!checkCpr(cpr))
		{
			ok = false;
			error+= "Cpr nummer skal være 10 tal. <br> ";
		}
		if(!checkPassword(password))
		{
			ok = false;
			error+= "Password skal være mellem 7 og 8 karaktere. Skal indeholder stor og små bogstaver samt tal <br> ";
		}
		if(ok)
		{
			if(id==0)
			{
				id = unusedId();
				OperatoerDTO2 user = new OperatoerDTO2(id, name, ini, cpr, password);
				data.createOperatoer(user);
				succes = "Bruger oprettet med id: "+id;
			}
			else
			{
				OperatoerDTO2 user = data.getOperatoer(id);
				user.setOprNavn(name);
				user.setIni(ini);
				user.setCpr(cpr);
				user.setPassword(password);
				
				data.updateOperatoer(user);
			}
			
			delete();
			
		}
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
	
	/** Operat�r navn min. 2 max.. 20 karakterer */
	public boolean checkName(String navn)
	{
		String REGEX = "^[a-zA-Z[\\s]]{2,20}$+";
		return checkRegex(REGEX,navn);
	}
	
	/** Operat�r initialer min. 2 max. 3 karakterer */
	public boolean checkIni(String ini)
	{
		String REGEX ="^[a-zA-Z[\\s]]{2,3}$+";
		return checkRegex(REGEX, ini);
	}
	
	/** Operat�r cpr-nr 10 karakterer */
	public boolean checkCpr(String cpr)
	{
		String REGEX = "^[0-9]{10,10}$+";
		return checkRegex(REGEX, cpr);
	}
	/** Operat�r password min. 7 max. 8 karakterer */
	public boolean checkPassword(String password)
	{
		String REGEX = "^[0-9[a-zA-Z[\\-\\.\\+\\?[_!=[\\s]]]]]{7,8}$+";
		return checkRegex(REGEX, password);
	}
	private int unusedId() {
		ArrayList<OperatoerDTO2> personer = data.getAllOperatoer();
		
		boolean emptyId;
		for(int b = 1; b < 999999999; b++) {
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

	

}