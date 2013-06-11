package dto;

public class ProduktBatchKompDTO 
{
	int pbId; 	  // produktbatch'ets id
	RaavareBatchDTO rb;        // i omr�det 1-99999999
	double tara;
	double netto;

	public ProduktBatchKompDTO(int pbId, RaavareBatchDTO rb, double tara, double netto)
	{
		this.pbId = pbId;
		this.rb = rb;
		this.tara = tara;
		this.netto = netto;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public RaavareBatchDTO getRb() { return rb; }
	public void setRb(RaavareBatchDTO rb) { this.rb = rb; }
	public double getTara() { return tara; }
	public void setTara(double tara) { this.tara = tara; }
	public double getNetto() { return netto; }
	public void setNetto(double netto) { this.netto = netto; }

	public String toString() { 
		return pbId + "\t" + rb +"\t" + tara +"\t" + netto ; 
	}
}
