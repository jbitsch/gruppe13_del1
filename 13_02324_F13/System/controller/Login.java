package controller;

import daoimpl.MySQLOperatoerDAO;
import daointerfaces.DALException;
import daointerfaces.IOperatoerDAO;
import dto.OperatoerDTO;



public class Login
{
	private int id = 0;
	private String adgangskode = "";
	private String rolle="";
	private String meddelelse = "";       // fejlmeddelelse til brugeren

	private boolean tjek = false;         // om adgangskode skal tjekkes
	private boolean loggetInd = false;    // om adgangskoden var korrekt

	private IOperatoerDAO d;
	
	public Login()
	{
			d = new MySQLOperatoerDAO();
	}

	public void tjekLogin() throws DALException
	{
/*D*/	System.out.println("TJEK = "+tjek);
/*D*/	System.out.println("Bruger = "+id);
/*D*/	System.out.println("KODE = "+adgangskode);
		if (!tjek) return; // er der ikke sket aendringer behoever vi ikke tjekke igen
		loggetInd = false;
		tjek = false;
		
		OperatoerDTO operatoer = d.getOperatoer(id);
		
		
		if (!operatoer.getPassword().equals(adgangskode))
		{
			meddelelse += "Forkert brugernavn eller adgangskode <br>";
		}
		else if("Operatør".equals(operatoer.getRolle()))
		{
			meddelelse += "Du er operatør, og kan derfor ikke logge ind <br>";
		}
		else
		{
			loggetInd = true;
			rolle = operatoer.getRolle();
		}
	}
	
	public void setId(String idString)  
	{ 
		tjek=true;
		try{
			id = Integer.parseInt(idString);
		}
		catch(NumberFormatException e){
			id = 0;
			meddelelse += "Id skal være et tal <br>";
		}
	}

	public int getId()        
	{ 
		return id; 
	}
	public void setAdgangskode(String ak) 
	{ 
		tjek=true; 
		adgangskode = ak; 
	}

	public void setMeddelelse(String m)   { 
		meddelelse = m; 
	}

	public String getMeddelelse() { 
		String m=meddelelse; meddelelse=""; 
		return m; 
	}

	public boolean isLoggetInd() {
		if (tjek) return false;       // er der sket aendringer skal der logges ind
		return loggetInd;
	}
	public String getRolle()
	{
		return rolle;
	}
}