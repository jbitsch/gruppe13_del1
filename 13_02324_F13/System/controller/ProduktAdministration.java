package controller;

import daoimpl.MySQLProduktBatchDAO;
import daoimpl.MySQLProduktBatchKompDAO;
import daoimpl.MySQLReceptDAO;
import daoimpl.MySQLReceptKompDAO;
import daointerfaces.IProduktBatchDAO;
import daointerfaces.IProduktBatchKompDAO;
import daointerfaces.IReceptDAO;
import daointerfaces.IReceptKompDAO;

public class ProduktAdministration {
	private IProduktBatchDAO produktBatchDAO;
	private IProduktBatchKompDAO produktBatchKomDAO;
	private IReceptDAO receptDAO;
	private IReceptKompDAO receptKompDAO;
	
	public ProduktAdministration()
	{
		produktBatchDAO = new MySQLProduktBatchDAO();
		produktBatchKomDAO = new MySQLProduktBatchKompDAO();
		receptDAO = new MySQLReceptDAO();
		receptKompDAO = new MySQLReceptKompDAO();
	}
}
