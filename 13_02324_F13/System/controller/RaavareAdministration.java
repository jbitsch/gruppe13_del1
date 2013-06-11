package controller;

import daoimpl.MySQLRaavareBatchDAO;
import daoimpl.MySQLRaavareDAO;
import daointerfaces.DALException;
import daointerfaces.IRaavareBatchDAO;
import daointerfaces.IRaavareDAO;


public class RaavareAdministration {
	private IRaavareBatchDAO raavareBatchDAO;
	private IRaavareDAO raavareDAO;
	
	private int raavareId = 0;
	private String raavareNavn  = "";
	private String leverandoer ="";
	
	private String succes = "";
	private String error = "";
	private String handling="";
	
	public RaavareAdministration()
	{		
		raavareBatchDAO = new MySQLRaavareBatchDAO();
		raavareDAO = new MySQLRaavareDAO();
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
				//TODO tjek
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
	
	
	
	
	
	
	
	
	

	public int getRaavareId() {
		return raavareId;
	}

	public void setRaavareId(int raavareId) {
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

	public String getSucces() {
		return succes;
	}

	public String getError() {
		return error;
	}
	

}
