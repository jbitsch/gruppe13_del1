package dto;
import java.sql.Timestamp;

public class ProduktBatchDTO 
{
	int pbId;                     // i omr�det 1-99999999
	int status;					// 0: ikke p�begyndt, 1: under produktion, 2: afsluttet
	int receptId;
	int opr_id;
	Timestamp datoStart;
	Timestamp datoSlut;
	
	public ProduktBatchDTO(int pbId, int status, int receptId, Timestamp datoStart, Timestamp datoSlut,int opr_id)
	{
		this.pbId = pbId;
		this.status = status;
		this.receptId = receptId;
		this.datoStart = datoStart;
		this.datoSlut = datoSlut;
		this.opr_id = opr_id;
	}

	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public Timestamp getDatoStart(){ return datoStart; }
	public void setDatoStart(Timestamp datoStart) { this.datoStart = datoStart;}
	public Timestamp getDatoSlut(){ return datoSlut; }
	public void setDatoSlut(Timestamp datoSlut) { this.datoSlut = datoSlut;}
	public int getOprId()
	{
		return opr_id;
	}
	public void setOprId(int opr_id)
	{
		this.opr_id = opr_id;
	}
	public String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

