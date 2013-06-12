package controller;

import java.sql.Timestamp;
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
import dto.OperatoerDTO;
import dto.ProduktBatchDTO;
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
	
	
	public ProduktAdministration()
	{
		produktBatchDAO = new MySQLProduktBatchDAO();
		produktBatchKomDAO = new MySQLProduktBatchKompDAO();
		receptDAO = new MySQLReceptDAO();
		receptKompDAO = new MySQLReceptKompDAO();
		raavareDAO = new MySQLRaavareDAO();
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
	}
	
	//////////////////////Udfoer handling/////////////////////////
	public void udfoerHandling() throws DALException
	{
		succes = "";
		error = "";
		
		try
		{
			if (handling.equals("Opret produktbatch"))
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
		System.out.println(batchId);
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
	
	public ArrayList<ReceptKompDTO> getReceptKomp(int id) throws DALException 
	{
		return receptKompDAO.getReceptKompList(id);
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
}
