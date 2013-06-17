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
		if(raavareNavn==null || "".equals(raavareNavn))
			return raavareBatchDAO.getRaavareBatchList();
		else
			return raavareBatchDAO.getRaavareBatch(raavareNavn);
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
				boolean okId= okRaavareId();
				boolean okNavn = okNavn(raavareNavn);
				if( okId&&okNavn )
				{
					
					raavareNavn = replaceChar(raavareNavn);
					leverandoer = replaceChar(leverandoer);
					RaavareDTO nyRaavare = new RaavareDTO(Integer.parseInt(raavareId), raavareNavn);
					raavareDAO.createRaavare(nyRaavare);
					succes = "Råvare nr. " + nyRaavare.getRaavareId() + " ved navn " + nyRaavare.getRaavareNavn()+ " er nu oprettet!";
					delete();
				}
			}
			else if (handling.equals("Ændre Raavare"))
			{
				if(okNavn(raavareNavn))
				{
					raavareNavn = replaceChar(raavareNavn);
					RaavareDTO nyRaavare = new RaavareDTO(Integer.parseInt(raavareId), raavareNavn);
					
					raavareDAO.updateRaavare(nyRaavare);
					succes = "Råvare nr. " + nyRaavare.getRaavareId() + " blev opdateret!";
				}
			}
			else if (handling.equals("Opret Raavarebatch"))
			{
				boolean idOk =  okRaavareBatchId();
				boolean maengdeOk = okMaengde();
				boolean rbOk = okBatchRaavareId();
				boolean levOk = okLeverandoer(leverandoer);
				
				if(idOk &&maengdeOk  &&rbOk &&levOk )
				{
					double talMaengde = Double.parseDouble(maengde);
					talMaengde = Math.round(talMaengde*10000)/10000.0d;
					java.util.Date date= new java.util.Date();	
					leverandoer = replaceChar(leverandoer);
					RaavareBatchDTO nytRaavareBatch = new RaavareBatchDTO(Integer.parseInt(raavareBatchId), raavareDAO.getRaavare(Integer.parseInt(batchRaavareId)), talMaengde, new Timestamp(date.getTime()),leverandoer);
					
					raavareBatchDAO.createRaavareBatch(nytRaavareBatch);
					succes = "Råvarebatch nr." + nytRaavareBatch.getRbId() +"fra "+leverandoer +" er nu oprettet!";
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
			// Tjek at id er en int
			int id = Integer.parseInt(raavareId);	
			// Id i intervallet 1-99999999
			if(id < 1 || id > 99999999)
			{
				error += "Id skal være i intervallet 1-99999999<br>";
			}
			else
			{
				// Findes id'et i forvejen?
				try
				{
					raavareDAO.getRaavare(id);
					error += "Id'et findes i forvejen<br>";
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
			error += "Id skal være et tal i intervallet 1-99999999<br>";
		}
		
		return false;
	}
	
	public boolean okNavn(String navn)
	{
		if(navn.length() < 2 || navn.length() > 20)
		{
			error += "Længde på raavarenavn skal være på 2-20 karakterer<br>";
			return false;
		}
		return true;
	}
	public boolean okLeverandoer(String navn)
	{
		if(navn.length() < 2 || navn.length() > 20)
		{
			error += "Længde på leverandoer skal være på 2-20 karakterer<br>";
			return false;
		}
		return true;
	}
	public boolean okBatchRaavareId()
	{
		try
		{
			Integer.parseInt(batchRaavareId);
			return true;

		}
		catch(NumberFormatException e)
		{
			error += "Du skal vaelge en raavare<br>";
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
				error += "Id skal være i intervallet 1-99999999<br>";
			}
			else
			{
				// Findes id'et i forvejen?
				try
				{
					raavareBatchDAO.getRaavareBatch(id);
					error += "Id'et findes i forvejen<br>";
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
			error += "Id skal være et tal i intervallet 1-99999999<br>";
		}
		
		return false;
	}
	
	public boolean okMaengde()
	{
		try
		{
			Double.parseDouble(maengde);
			return true;
		}
		catch(NumberFormatException e)
		{
			error += "Mængden skal angives som et tal<br>";
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
	
	/**
	 * @author Jacob Nordfalk - Webprogrammering med JSP
	 */
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
