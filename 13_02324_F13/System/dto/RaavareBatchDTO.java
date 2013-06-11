package dto;
import java.sql.Timestamp;

public class RaavareBatchDTO
{
	int rbId;                     // i omr�det 1-99999999
	RaavareDTO raavare;             // i omr�det 1-99999999
	double maengde;             // kan v�re negativ
	Timestamp dato;

	
	public RaavareBatchDTO(int rbId, RaavareDTO raavare, double maengde, Timestamp dato)
	{
		this.rbId = rbId;
		this.raavare = raavare;
		this.maengde = maengde;
		this.dato = dato;
	}
	
	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public RaavareDTO getRaavare() { return raavare; }
	public void setRaavare(RaavareDTO raavare) { this.raavare = raavare; }
	public double getMaengde() { return maengde; }
	public void setMaengde(double maengde) { this.maengde = maengde; }
	public Timestamp getDato(){return dato;}
	public void setDato(Timestamp dato) { this.dato = dato;}
	public String toString() { 
		return rbId + "\t" + raavare +"\t" + maengde; 
	}
}
