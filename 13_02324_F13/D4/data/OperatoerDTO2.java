package data;

/**
 * Operat�r Data Transfer Object
 */
public class OperatoerDTO2 implements Comparable<OperatoerDTO2>{
	/** Operat�r id i omr�det 1-99999999. V�lges af brugerne */
	int oprId;
	/** Operat�r navn min. 2 max. 20 karakterer */
	String oprNavn;
	/** Operat�r initialer min. 2 max. 3 karakterer */
	String ini;
	/** Operat�r cpr-nr 10 karakterer */
	String cpr;
	/** Operat�r password min. 7 max. 8 karakterer */
	String password;
	public OperatoerDTO2(int oprId, String oprNavn, String ini, String cpr, String password)  {
		super();
		this.oprId = oprId;
		this.oprNavn = oprNavn;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
	}
	public OperatoerDTO2(){}
	
	public int getOprId() {
		return oprId;
	}
	public void setOprId(int oprId) {
		this.oprId = oprId;
	}
	public String getOprNavn() {
		return oprNavn;
	}
	public void setOprNavn(String oprNavn) {
		this.oprNavn = oprNavn;
	}
	public String getIni() {
		return ini;
	}
	public void setIni(String ini) {
		this.ini = ini;
	}
	public String getCpr() {
		return cpr;
	}
	public void setCpr(String cpr) {
		this.cpr = cpr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	//Metode til at sortere users i faldende ordre
	public int compareTo(OperatoerDTO2 compareUsers) {
		 
		int compareID = ((OperatoerDTO2) compareUsers).getOprId(); 
		//ascending order
		return this.oprId - compareID;
 
	}
}

