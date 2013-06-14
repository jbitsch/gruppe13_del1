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

	private String receptId = "";
	private String receptNavn = "";
	
	private String produktbatchReceptId = "";
	private int produktbatchId = 0;
	private ArrayList<ReceptKompDTO> receptKomp=null;
	private ArrayList<RaavareDTO> raavareList = null;
	
	public ProduktAdministration()
	{
		produktBatchDAO = new MySQLProduktBatchDAO();
		produktBatchKomDAO = new MySQLProduktBatchKompDAO();
		receptDAO = new MySQLReceptDAO();
		receptKompDAO = new MySQLReceptKompDAO();
		raavareDAO = new MySQLRaavareDAO();
		
		receptKomp = new ArrayList<ReceptKompDTO>();
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
		receptNavn = "";
		receptId = "";
		receptKomp.clear();
		raavareList = null;
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
			else if("Opret recept".equals(handling))
			{
				createRecept();
			}
			else if("Tilfoej raavare".equals(handling))
			{
				
			}
			else if("Slet".equals(handling))
			{
				deleteKomp(Integer.parseInt((receptId)));//Er raavare id...
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
	public void createRecept() throws DALException
	{

		boolean dataOk = true;
		int recept_id = 0;
		try
		{
			recept_id = Integer.parseInt(receptId);
		}catch(NumberFormatException e1)
		{
			error+="Recept id skal være et tal<br>";
			dataOk = false;
		}
		try
		{
			receptDAO.getRecept(recept_id);
			error += "Id'et findes i forvejen<br>";
			dataOk = false;
			
		}
		catch(DALException e)
		{
			// Id'et lever op til alle krav
		}
		if(receptNavn.length()<2 || receptNavn.length()>20)
		{
			error += "Receptnavn skaæ være mellem 2 og 20 karaktere<br>";
			dataOk = false;
		}
		if(!(receptKomp.size()>0))
		{
			error +="Der skal minimum vaere tilfoejet en raavare til recepten<br>";
			dataOk = false;
		}
		if(dataOk)
		{
			ReceptDTO recept= new ReceptDTO(recept_id,replaceChar(receptNavn)); 
			receptDAO.createRecept(recept);
			for(int i=0; i<receptKomp.size();i++)
			{
				
				ReceptKompDTO receptkomponent = receptKomp.get(i);
				receptkomponent.setReceptId(recept_id);
				receptKompDAO.createReceptKomp(receptkomponent);
			}
			succes = "Recepten med navn "+receptNavn+" er nu oprettet";
			delete();
			
		}
		
		
	}
	public void addToRaavareList(String r_id, String netto, String tolerance) throws DALException
	{
		boolean dataOk = true;
		double net = 0.00;
		double tol = 0.00;
		try
		{
			net = Double.parseDouble(netto);
			if(net < 0.05 || net > 20.0)
			{
				error+="Netto skal være mellem 0.05 og 20 kg.<br>";
				dataOk = false;
			}
		}catch(NumberFormatException e)
		{
			error += "Netto skal være et tal<br>";
			dataOk = false;
		}
		try
		{
			tol = Double.parseDouble(tolerance);
			if(tol < 0.1 || net > 10.0)
			{
				error+="Tolerance skal være mellem 0.1 og 10%.<br>";
				dataOk = false;
			}
		}catch(NumberFormatException e)
		{
			error += "Tolerance skal være et tal<br>";
			dataOk = false;
		}
		if (dataOk)
		{
			RaavareDTO raavare = raavareDAO.getRaavare(Integer.parseInt(r_id));
			for(int i=0;i<raavareList.size();i++)
			{
				if(raavare.getRaavareId()==raavareList.get(i).getRaavareId())
				{
					raavareList.remove(i);
				}
					
			}
			receptKomp.add(new ReceptKompDTO(0,raavare,net,tol));
			error ="";
		}
	}
	public void deleteKomp(int r_id)
	{
		for(int i=0; i<receptKomp.size(); i++)
		{
			int recept_id = receptKomp.get(i).getRaavare().getRaavareId();
			if(recept_id==r_id)
			{
				raavareList.add(receptKomp.get(i).getRaavare());
				receptKomp.remove(i);
				succes = "Raavaren er fjernet fra listen";
			}
		}
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
		
		if(raavareList==null)
		{
			return raavareList = raavareDAO.getRaavareList();
		}
		else
			return raavareList;
		
	}
	public ArrayList<ReceptDTO> getRecepter() throws DALException 
	{
		return receptDAO.getReceptList();
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
	public String getReceptId() {
		return receptId;
	}
	public void setReceptId(String receptId) {
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
		return receptDAO.getRecept(Integer.parseInt((receptId)));
	}
	public ArrayList<ReceptKompDTO> getReceptKomp()
	{
		return receptKomp;
	}
	public void setReceptKomp(int id)  throws DALException 
	{
		receptKomp = receptKompDAO.getReceptKompList(id);
	}
	public ArrayList<ReceptKompDTO> getReceptKompToadd()
	{
		return receptKomp;
	}
	public String getHandling()
	{
		return handling;
	}
	public String replaceChar(String toReplace)
	{
		toReplace = toReplace.replace("&", "&amp;");   // erstat & med HTML-koden for &
		toReplace = toReplace.replace("<", "&lt;");    // erstat < med HTML-koden for <
		toReplace = toReplace.replace(">", "&gt;");    // erstat > med HTML-koden for >
		toReplace = toReplace.replace("\"","&quot;");  // erstat " med HTML-koden for "
		toReplace = toReplace.replace("'", "&lsquo;"); // erstat ' med HTML-koden for '
		
		return toReplace;
	}

}
