package controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import daoimpl.MySQLRaavareBatchDAO;
import daoimpl.MySQLRaavareDAO;
import daointerfaces.DALException;
import daointerfaces.IRaavareBatchDAO;
import daointerfaces.IRaavareDAO;
import dto.RaavareBatchDTO;
import dto.RaavareDTO;


public class RaavareAdministration {
	private IRaavareBatchDAO raavareBatchDAO;
	private IRaavareDAO raavareDAO;
	
	private String raavareId = "";
	private String raavareNavn  = "";
	private String leverandoer ="";
	
	private String raavareBatchId = ""; //rbid
	private String batchRaavareId = ""; // raavareid i en raavarebatch
	private String maengde = "";
	
	private String succes = "";
	private String error = "";
	private String handling="";
	
	private boolean isNew =true;
		
	public RaavareAdministration()
	{		
		raavareBatchDAO = new MySQLRaavareBatchDAO();
		raavareDAO = new MySQLRaavareDAO();
	}
	public void delete()
	{
		raavareId = "";
		raavareNavn  = "";
		leverandoer ="";
		
		raavareBatchId = "";
		batchRaavareId = ""; 
		maengde = "";
		
		isNew =true;
	}
	public void deleteSucErr()
	{
		succes = "";
		error = "";
	}
	
	public ArrayList<RaavareBatchDTO> getRaavarebatch() throws DALException
	{
		return raavareBatchDAO.getRaavareBatchList();
	}
	
	public ArrayList<RaavareDTO> getRaavare() throws DALException
	{
		return raavareDAO.getRaavareList();
	}
	public void setRaavare(int id) throws DALException
	{
		RaavareDTO raavare = raavareDAO.getRaavare(id);
		if(raavare!=null)
		{
			raavareId = id+"";
			raavareNavn = raavare.getRaavareNavn();
			leverandoer = raavare.getLeverandoer();
			isNew = false;
		}
	}
	
	//////////////////////Udfoer handling/////////////////////////
	public void udfoerHandling() throws DALException
	{
		error = "";
		succes = "";
		try
		{
			if (handling.equals("Opret Raavare"))
			{
				if(okRaavareId() && okNavn(raavareNavn) && okNavn(leverandoer))
				{
					RaavareDTO nyRaavare = new RaavareDTO(Integer.parseInt(raavareId), raavareNavn, leverandoer);
					
					raavareDAO.createRaavare(nyRaavare);
					succes = "Råvare nr. " + nyRaavare.getRaavareId() + " ved navn " + nyRaavare.getRaavareNavn() + " fra leverandøren " + nyRaavare.getLeverandoer() + " er nu oprettet!";
					delete();
				}
			}
			else if (handling.equals("Ændre Raavare"))
			{
				if(okNavn(raavareNavn) && okNavn(leverandoer))
				{
					RaavareDTO nyRaavare = new RaavareDTO(Integer.parseInt(raavareId), raavareNavn, leverandoer);
					
					raavareDAO.updateRaavare(nyRaavare);
					succes = "Råvare nr. " + nyRaavare.getRaavareId() + " blev opdateret!";
					delete();
				}
			}
			else if (handling.equals("Opret Raavarebatch"))
			{
				if(okRaavareBatchId() && okMaengde() && okBatchRaavareId())
				{
					double talMaengde = Double.parseDouble(maengde);
					talMaengde = Math.round(talMaengde*10000)/10000.0d;
					java.util.Date date= new java.util.Date();					
					RaavareBatchDTO nytRaavareBatch = new RaavareBatchDTO(Integer.parseInt(raavareBatchId), raavareDAO.getRaavare(Integer.parseInt(batchRaavareId)), talMaengde, new Timestamp(date.getTime()));
					
					raavareBatchDAO.createRaavareBatch(nytRaavareBatch);
					succes = "Råvarebatch nr." + nytRaavareBatch.getRbId() + " er nu oprettet!";
					delete();
				}			
			}
			else
				System.out.println("Ukendt handling: " + handling);
		}
		finally
		{
			handling = null;
		}
	}
	
	////Tjek metoder //////////
	public boolean okRaavareId()
	{
		try
		{
			// Kommatal?
			
			// Tjek at id er en int
			int id = Integer.parseInt(raavareId);
			
			// Id i intervallet 1-99999999
			if(id < 1 || id > 99999999)
			{
				error += "Id skal være i intervallet 1-99999999\n";
			}
			else
			{
				// Findes id'et i forvejen?
				try
				{
					raavareDAO.getRaavare(id);
					error += "Id'et findes i forvejen\n";
				}
				catch(DALException e)
				{
					// Id'et lever op til alle krav
					return true;
				}
			}
		}
		catch(NumberFormatException e)
		{
			error += "Id skal være et tal i intervallet 1-99999999\n";
		}
		
		return false;
	}
	
	public boolean okNavn(String navn)
	{
		if(navn.length() < 2 || navn.length() > 20)
		{
			error += "Råvarenavnet skal være på 2-20 karakterer\n";
			return false;
		}
		return true;
	}
	public boolean okBatchRaavareId()
	{
		try
		{
			// Tjek at id er en int
			int id = Integer.parseInt(batchRaavareId);
			
			return true;

		}
		catch(NumberFormatException e)
		{
			error += "Du skal vaelge en raavare\n";
		}
		
		return false;
	}

	public boolean okRaavareBatchId()
	{
		try
		{	
			// Tjek at id er en int
			int id = Integer.parseInt(raavareBatchId);
			
			// Id i intervallet 1-99999999
			if(id < 1 || id > 99999999)
			{
				error += "Id skal være i intervallet 1-99999999\n";
			}
			else
			{
				// Findes id'et i forvejen?
				System.out.println(id);
				try
				{
					raavareBatchDAO.getRaavareBatch(id);
					error += "Id'et findes i forvejen\n";
				}
				catch(DALException e)
				{
					// Id'et lever op til alle krav
					return true;
				}
			}
		}
		catch(NumberFormatException e)
		{
			error += "Id skal være et tal i intervallet 1-99999999\n";
		}
		
		return false;
	}
	
	public boolean okMaengde()
	{
		try
		{
			double value = Double.parseDouble(maengde);
			return true;
		}
		catch(NumberFormatException e)
		{
			error += "Mængden skal angives som et tal\n";
			return false;
		}
		
	}
	public boolean getIsnew()
	{
		return isNew;
	}
	public String getRaavareId() {
		return raavareId;
	}
	public void setRaavareId(String raavareId) {
		this.raavareId = raavareId;
	}
	public String getRaavareNavn() {
		return raavareNavn;
	}
	public void setRaavareNavn(String raavareNavn) {
		this.raavareNavn = raavareNavn;
	}
	public String getLeverandoer() {
		return leverandoer;
	}
	public void setLeverandoer(String leverandoer) {
		this.leverandoer = leverandoer;
	}
	public String getRaavareBatchId() {
		return raavareBatchId;
	}
	public void setRaavareBatchId(String raavareBatchId) {
		this.raavareBatchId = raavareBatchId;
	}
	public String getBatchRaavareId() {
		return batchRaavareId;
	}
	public void setBatchRaavareId(String batchRaavareId) {
		this.batchRaavareId = batchRaavareId;
	}
	public String getMaengde() {
		return maengde;
	}
	public void setMaengde(String maengde) {
		this.maengde = maengde;
	}
	public String getSucces() {
		return succes;
	}
	public String getError() {
		return error;
	}
	
	public void setHandling(String handling)
	{
		this.handling = handling;
	}
	
	public String getHandling()
	{
		return handling;
	}
	
	
}
