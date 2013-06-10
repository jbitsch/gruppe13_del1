package dto;
import java.sql.Timestamp;

public class RaavareBatchDTO
{
	int rbId;                     // i omr�det 1-99999999
	int raavareId;             // i omr�det 1-99999999
	double maengde;             // kan v�re negativ
	Timestamp dato;

	
	public RaavareBatchDTO(int rbId, int raavareId, double maengde, Timestamp dato)
	{
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.maengde = maengde;
		this.dato = dato;
	}
	
	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public int getRaavareId() { return raavareId; }
	public void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public double getMaengde() { return maengde; }
	public void setMaengde(double maengde) { this.maengde = maengde; }
	public Timestamp getDato(){return dato;}
	public void setDato(Timestamp dato) { this.dato = dato;}
	public String toString() { 
		return rbId + "\t" + raavareId +"\t" + maengde; 
	}
}
