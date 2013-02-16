package Main;

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

	public int getOprID() {
		return oprID;
	}

	public void setOprID(int oprID) {
		this.oprID = oprID;
	}

	public String getOprNavn() {
		return oprNavn;
	}

	public void setOprNavn(String oprNavn) {
		this.oprNavn = oprNavn;
	}

	public String getCprNr() {
		return cprNr;
	}

	public void setCprNr(String cprNr) {
		this.cprNr = cprNr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
