package controller;

import java.util.ArrayList;

import daoimpl.MySQLRaavareBatchDAO;
import daoimpl.MySQLRaavareDAO;
import daointerfaces.DALException;
import daointerfaces.IRaavareBatchDAO;
import daointerfaces.IRaavareDAO;
import dto.RaavareDTO;


public class RaavareAdministration {
	private IRaavareBatchDAO raavareBatchDAO;
	private IRaavareDAO raavareDAO;
	
	private int raavareId = 0;
	private String raavareNavn  = "";
	private String leverandoer ="";
	
	private String succes = "";
	private String error = "";
	private String handling="";
	private ArrayList<RaavareDTO> raavare = null;
	
	public RaavareAdministration()
	{		
		raavareBatchDAO = new MySQLRaavareBatchDAO();
		raavareDAO = new MySQLRaavareDAO();
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
			raavareId = id;
			raavareNavn = raavare.getRaavareNavn();
			leverandoer = raavare.getLeverandoer();
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
