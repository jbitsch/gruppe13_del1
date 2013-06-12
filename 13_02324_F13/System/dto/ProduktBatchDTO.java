package dto;
import java.sql.Timestamp;

public class ProduktBatchDTO 
{
	int pbId;                     // i omr�det 1-99999999
	int status;					// 0: ikke p�begyndt, 1: under produktion, 2: afsluttet
	ReceptDTO recept;
	OperatoerDTO opr;
	Timestamp datoStart;
	Timestamp datoSlut;
	
	public ProduktBatchDTO(int pbId, ReceptDTO recept, int status, Timestamp datoStart, Timestamp datoSlut,OperatoerDTO opr)
	{
		this.pbId = pbId;
		this.status = status;
		this.recept = recept;
		this.datoStart = datoStart;
		this.datoSlut = datoSlut;
		this.opr = opr;
	}

	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public ReceptDTO getRecept() { return recept; }
	public void setRecept(ReceptDTO recept) { this.recept = recept; }
	public Timestamp getDatoStart(){ return datoStart; }
	public void setDatoStart(Timestamp datoStart) { this.datoStart = datoStart;}
	public Timestamp getDatoSlut(){ return datoSlut; }
	public void setDatoSlut(Timestamp datoSlut) { this.datoSlut = datoSlut;}
	public OperatoerDTO getOpr()
	{
		return opr;
	}
	public void setOpr(OperatoerDTO opr)
	{
		this.opr = opr;
	}
	public String toString() { return pbId + "\t" + status + "\t" + recept; }
}

