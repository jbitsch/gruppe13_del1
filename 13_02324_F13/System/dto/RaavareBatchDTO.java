package dto;
import java.sql.Timestamp;

public class RaavareBatchDTO
{
	private int rbId;                     // i omr�det 1-99999999
	private RaavareDTO raavare;             // i omr�det 1-99999999
	private double maengde;             // kan v�re negativ
	private Timestamp dato;
	private String leverandoer;

	
	public RaavareBatchDTO(int rbId, RaavareDTO raavare, double maengde, Timestamp dato,String leverandoer)
	{
		this.rbId = rbId;
		this.raavare = raavare;
		this.maengde = maengde;
		this.dato = dato;
		this.leverandoer = leverandoer;
	}
	
	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public RaavareDTO getRaavare() { return raavare; }
	public void setRaavare(RaavareDTO raavare) { this.raavare = raavare; }
	public double getMaengde() { return maengde; }
	public void setMaengde(double maengde) { this.maengde = maengde; }
	public Timestamp getDato(){return dato;}
	public void setDato(Timestamp dato) { this.dato = dato;}
	
	public String getLeverandoer() {
		return leverandoer;
	}

	public void setLeverandoer(String leverandoer) {
		this.leverandoer = leverandoer;
	}

	public String toString() { 
		return rbId + "\t" + raavare +"\t" +leverandoer+"\t" + maengde; 
	}
}
