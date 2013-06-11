package controller;

import daoimpl.MySQLRaavareBatchDAO;
import daoimpl.MySQLRaavareDAO;
import daointerfaces.IRaavareBatchDAO;
import daointerfaces.IRaavareDAO;


public class RaavareAdministration {
	private IRaavareBatchDAO raavareBatchDAO;
	private IRaavareDAO raavareDAO;
	
	public RaavareAdministration()
	{		
		raavareBatchDAO = new MySQLRaavareBatchDAO();
		raavareDAO = new MySQLRaavareDAO();
	}

}
