package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import daoimpl.MySQLOperatoerDAO;
import daointerfaces.DALException;
import daointerfaces.IOperatoerDAO;

import dto.OperatoerDTO;

public class BrugerAdministration {
	
	private IOperatoerDAO operatoerDAO;
	
	private String handling="";
	private String error = "";
	private String succes = "";
	
	//Change password
	private String new1 = "";
	private String new2 = "";
	private String old = "";
	private int id = 0;
	
	//New user
	private String name = "";
	private String ini = "";
	private String cpr ="";
	private String password ="";
	private String rolle = "Operatør";
	
	
	///////////////////////////Change password////////////////////////
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
	////////////////////////////Change or create user///////////////
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
	public void setRolle(String rolle)
	{
		this.rolle = rolle;
	}
	public void setUser(int uId) throws DALException
	{
		OperatoerDTO user = operatoerDAO.getOperatoer(uId);
		name = user.getOprNavn();
		ini = user.getIni();
		password = user.getPassword();
		cpr = user.getCpr();
		id = user.getOprId();
		
		rolle = user.getRolle();
	}
	
	//////////////////////Udfoer handling/////////////////////////
	public void udfoerHandling() throws DALException
	{
		succes = "";
		error = "";
		try
		{
			if (handling.equals("changePw"))
			{
				changePassword(old,new1, new2,id);
			}
			else if (handling.equals("Opret bruger") || handling.equals("Ændre"))
			{
				userForm(name, ini, cpr,password);
			}
			else if(handling.equals("Slet bruger"))
			{
				operatoerDAO.deleteOperatoer(id);
				succes = "Bruger med id "+ id + " er blevet slettet";
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
	
	///////////Hjaelpe metoder////////////////////////////////
	
	private void changePassword(String old, String new1, String new2,int id) throws DALException
	{
		OperatoerDTO user = operatoerDAO.getOperatoer(id);
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
			operatoerDAO.updateOperatoer(user);
		}		
	}
	
	private void userForm(String name, String ini, String cpr, String password) throws DALException
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
			if(id==0) //opret ny bruger
			{
				id = unusedId();
				OperatoerDTO user = new OperatoerDTO(id, name, ini, cpr, replaceChar(password),rolle);
				operatoerDAO.createOperatoer(user);
				succes = "Bruger oprettet med id: "+id;
				delete(); //nulstiller alle parametere
			}
			else //Aendre bruger oplysninger
			{
				OperatoerDTO user = operatoerDAO.getOperatoer(id);
				user.setOprNavn(name);
				user.setIni(ini);
				user.setCpr(cpr);
				user.setPassword(replaceChar(password));
				user.setRolle(rolle);
				
				operatoerDAO.updateOperatoer(user);
				
				succes = "Bruger med id: "+id + " aendret";
			}
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
	private boolean checkName(String navn)
	{
		String REGEX = "^[a-zA-Z[\\s]]{2,20}$+";
		return checkRegex(REGEX,navn);
	}
	
	/** Operat�r initialer min. 2 max. 3 karakterer */
	private boolean checkIni(String ini)
	{
		String REGEX ="^[a-zA-Z[\\s]]{2,3}$+";
		return checkRegex(REGEX, ini);
	}
	
	/** Operat�r cpr-nr 10 karakterer */
	private boolean checkCpr(String cpr)
	{
		String REGEX = "^[0-9]{10,10}$+";
		return checkRegex(REGEX, cpr);
	}
	/** Operat�r password min. 7 max. 8 karakterer */
	private boolean checkPassword(String password)
	{
		
		String REGEX = "(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*{7,8}$)?(^(?=.*[0-9])(?=.*[a-z])(?=.*[\\-\\.\\+\\?[_!=[\\s]]]).*{7,8}$)?(^(?=.*[0-9])(?=.*[A-Z])(?=.*[\\-\\.\\+\\?[_!=[\\s]]]).*{7,8}$)?(^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\-\\.\\+\\?[_!=[\\s]]]).*{7,8}$)?";
		return checkRegex(REGEX, password);
	}
	private int unusedId() throws DALException {
		ArrayList<OperatoerDTO> personer = operatoerDAO.getOperatoerList();
		
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
	
	
	public String getHandling() {
		return handling;
	}
	public String getNew1() {
		return new1;
	}
	public String getNew2() {
		return new2;
	}
	public String getOld() {
		return old;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIni() {
		return ini;
	}
	public String getCpr() {
		return cpr;
	}
	public String getPassword() {
		return password;
	}
	public String getRolle() {
		return rolle;
	}
	public BrugerAdministration()
	{
		operatoerDAO = new MySQLOperatoerDAO();
	}
	public String getError()
	{
		return error;
	}
	public String getSucces()
	{
		return succes;
	}
	
	public void setHandling(String h) {
		handling = h;
	}
	public ArrayList<OperatoerDTO> getUsers() throws DALException
	{
		ArrayList<OperatoerDTO> users =  operatoerDAO.getOperatoerList();
		return users; 
	}
	
	//nulstiller alle parametere
	public void delete()
	{	
		new1 = "";
		new2 = "";
		old = "";
		id = 0;
		
		name = "";
		ini = "";
		cpr = "";
		password = "";
	}
	public void deleteSucErr()
	{
		error = "";
		succes = "";
	}
	/**
	 * @author Jacob Nordfalk - Webprogrammering med JSP
	 */
	public String replaceChar(String toReplace)
	{
		toReplace = toReplace.replace("&", "&amp;");   // erstat & med HTML-koden for &
		toReplace = toReplace.replace("<", "&lt;");    // erstat < med HTML-koden for <
		toReplace = toReplace.replace(">", "&gt;");    // erstat > med HTML-koden for >
		toReplace = toReplace.replace("\"","&quot;");  // erstat " med HTML-koden for "
		toReplace = toReplace.replace("'", "&lsquo;"); // erstat ' med HTML-koden for '
		
		return toReplace;
	}

	

}