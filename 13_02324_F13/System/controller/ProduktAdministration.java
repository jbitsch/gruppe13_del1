package controller;

import java.util.ArrayList;

import daoimpl.MySQLProduktBatchDAO;
import daoimpl.MySQLProduktBatchKompDAO;
import daoimpl.MySQLRaavareDAO;
import daoimpl.MySQLReceptDAO;
import daoimpl.MySQLReceptKompDAO;
import daointerfaces.DALException;
import daointerfaces.IProduktBatchDAO;
import daointerfaces.IProduktBatchKompDAO;
import daointerfaces.IRaavareDAO;
import daointerfaces.IReceptDAO;
import daointerfaces.IReceptKompDAO;
import dto.ProduktBatchDTO;
import dto.ProduktBatchKompDTO;
import dto.RaavareDTO;
import dto.ReceptDTO;
import dto.ReceptKompDTO;

public class ProduktAdministration {
	private IProduktBatchDAO produktBatchDAO;
	private IProduktBatchKompDAO produktBatchKomDAO;
	private IReceptDAO receptDAO;
	private IReceptKompDAO receptKompDAO;
	private IRaavareDAO raavareDAO;
	
	private String error ="";
	private String succes = "";
	private String handling ="";

	private int receptId = 0;
	private String receptNavn = "";
	
	private String produktbatchReceptId = "";
	private int produktbatchId = 0;
	private ArrayList<ReceptKompDTO> receptKomp=null;
	private ArrayList<RaavareDTO> raavareToAdd = null;
	
	public ProduktAdministration()
	{
		produktBatchDAO = new MySQLProduktBatchDAO();
		produktBatchKomDAO = new MySQLProduktBatchKompDAO();
		receptDAO = new MySQLReceptDAO();
		receptKompDAO = new MySQLReceptKompDAO();
		raavareDAO = new MySQLRaavareDAO();
		
		receptKomp = new ArrayList<ReceptKompDTO>();
		raavareToAdd = new ArrayList<RaavareDTO>();
	}
	public void deleteSucErr()
	{
		succes = "";
		error = "";
		handling = "";
	}
	public void delete()
	{
		produktbatchReceptId = "";
		produktbatchId = 0;
		receptKomp.clear();
		raavareToAdd.clear();
		
	}
	
	//////////////////////Udfoer handling/////////////////////////
	public void udfoerHandling() throws DALException
	{
		succes = "";
		error = "";
		
		try
		{
			if ("Opret produktbatch".equals(handling))
			{
				createProduktbatch(produktbatchReceptId);
			}
			else
				System.out.println("Ukendt handling: " + handling);
		}
		finally
		{
			handling = null;
		}
	}
	
	private void createProduktbatch(String id) throws DALException
	{
		//int pbId, int status, ReceptDTO recept, Timestamp datoStart, Timestamp datoSlut,OperatoerDTO opr
		int receptId = Integer.parseInt(id);
		int batchId = unusedId();
		ProduktBatchDTO produktBatch = new ProduktBatchDTO(batchId,receptDAO.getRecept(receptId),0,null,null,null);
		produktBatchDAO.createProduktBatch(produktBatch);
		
		succes = "Produktbatch med id: "+batchId+ " er nu oprettet.";
	}
	private int unusedId() throws DALException {
		ArrayList<ProduktBatchDTO> produktBatch = produktBatchDAO.getProduktBatchList();
		boolean emptyId;
		for(int b = 1; b < 999999999; b++) {
			emptyId = true;
			for(int c = 0; c < produktBatch.size(); c++) {
				if(b == produktBatch.get(c).getPbId()) {
					emptyId = false;
					break;
				}
			}
			if(emptyId){
				return b;
			}
		}
		return 0;
	}
	
	public ArrayList<RaavareDTO> getRaavare() throws DALException 
	{
		return raavareDAO.getRaavareList();
	}
	public ArrayList<ReceptDTO> getRecepter() throws DALException 
	{
		return receptDAO.getReceptList();
	}
	
	public ArrayList<ReceptKompDTO> getReceptKomp()
	{
		return receptKomp;
	}
	public void setReceptKomp(int id)  throws DALException 
	{
		receptKomp = receptKompDAO.getReceptKompList(id);
	}
	
	public ArrayList<ProduktBatchKompDTO> getproduktbatchKomp()throws DALException
	{
		return produktBatchKomDAO.getProduktBatchKompList(produktbatchId);
	}
	public ProduktBatchDTO getProduktBatch() throws DALException
	{
		return produktBatchDAO.getProduktBatch(produktbatchId);
	}
	public String getError()
	{
		return error;
	}
	public String getSucces()
	{
		return succes;
	}
	public int getReceptId() {
		return receptId;
	}
	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}
	public String getReceptNavn() {
		return receptNavn;
	}
	public void setReceptNavn(String receptNavn) {
		this.receptNavn = receptNavn;
	}
	public void setHandling(String handling)
	{
		this.handling = handling;
	}
	public void setProduktbatchReceptId(String produktbatchReceptId) {
		this.produktbatchReceptId = produktbatchReceptId;
	}
	public ArrayList<ProduktBatchDTO> getProduktbatch()throws DALException
	{
		if(receptNavn==null || "".equals(receptNavn))
			return produktBatchDAO.getProduktBatchList();
		else
			return produktBatchDAO.getProduktBatchList(receptNavn);
	}
	public void setProduktbatchId(int produktbatchId)
	{
		this.produktbatchId = produktbatchId;
	}
	public ReceptDTO getRecept() throws DALException
	{
		return receptDAO.getRecept(receptId);
	}
	public void addReceptKomp(int r_id, double netto, double tolerance) throws DALException
	{
		RaavareDTO raavare = raavareDAO.getRaavare(r_id);
		ReceptKompDTO recept = new ReceptKompDTO(1,raavare,netto,tolerance);
	}
	public void addToRaavareList(int id) throws DALException
	{
		RaavareDTO raavare = raavareDAO.getRaavare(id);
		raavareToAdd.add(raavare);
	}
	public ArrayList<RaavareDTO> getRaavareToadd()
	{
		return raavareToAdd;
	}

}
