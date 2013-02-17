package data;

public abstract class User {

	protected int oprID;
	protected String oprNavn;
	protected String cprNr;
	protected String password;
	
	public User(int oprID, String oprNavn, String cprNr, String password)
	{
		this.oprID = oprID;
		this.oprNavn = oprNavn;
		this.cprNr = cprNr;
		this.password = password;
	}
}
