package dto;

/**
 * Operat�r Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class OperatoerDTO
{
	/** Operat�r-identifikationsnummer (opr_id) i omr�det 1-99999999. V�lges af brugerne */   
	int oprId;                     
	/** Operat�rnavn (opr_navn) min. 2 max. 20 karakterer */					
	String oprNavn;                
	/** Operat�r initialer min. 2 max. 3 karakterer */						
	String ini;                 
	/** Operat�r cpr-nr 10 karakterer */
	String cpr;                 
	/** Operat�r passeord min. 7 max. 8 karakterer */
	String password;            
	String rolle;

	public OperatoerDTO(int oprId, String oprNavn, String ini, String cpr, String password, String rolle)
	{
		this.oprId = oprId;
		this.oprNavn = oprNavn;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.rolle = rolle;
	}
	
    public OperatoerDTO(OperatoerDTO opr)
    {
    	this.oprId = opr.getOprId();
    	this.oprNavn = opr.getOprNavn();
    	this.ini = opr.getIni();
    	this.cpr = opr.getCpr();
    	this.password = opr.getPassword();
    }
    
    public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public String getOprNavn() { return oprNavn; }
	public void setOprNavn(String oprNavn) { this.oprNavn = oprNavn; }
	public String getIni() { return ini; }
	public void setIni(String ini) { this.ini = ini; }
	public String getCpr() { return cpr; }
	public void setCpr(String cpr) { this.cpr = cpr; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String toString() { return oprId + "\t" + oprNavn + "\t" + ini + "\t" + cpr + "\t" + password; }
	public String getRolle() { return rolle;}
	public void setRolle(String rolle) { this.rolle = rolle;}
}
