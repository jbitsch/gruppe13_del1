package funktionalitet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.IData2;
import data.OperatoerDTO2;
import exceptions.BigTaraException;

public class BrugerValg {
	
	public String handling;
	public String error = "";
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
	
	public void setData(IData2 data)
	{
		this.data = data;
	}
	public void setHandling(String h) {
		handling = h;
	}
	public void deleteError()
	{
		error = "";
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
	public void udfoerHandling() throws BigTaraException
	{

		try
		{
			if (handling.equals("changePw"))
			{
				changePassword(old,new1, new2,id);
			}
			else if (handling.equals("weight"))
			{	
				netto = b - t;
				if(netto <= 0)
				{
					netto =  0;
					error = "Tarra skal være større end brutto";
					//throw new BigTaraException();
				}
				t = 0.00;
				b = 0.00;
			}
			else if (handling.equals("newUser"))
			{
				//TODO newUser
			}
			else if (handling.equals("seeUser"))
			{
				//TODO user
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
		String REGEX = "^[a-zA-Z[\\-\\.\\+\\?[_!=[\\s]]]]{7,8}$+";
		if(new1.equals(new2))
		{
			if(!(checkRegex(REGEX, new1)))
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

}