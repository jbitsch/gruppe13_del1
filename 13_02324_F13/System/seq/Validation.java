package seq;

import java.io.IOException;
import java.sql.SQLException;

import connector.Connector;
import daoimpl.MySQLOperatoerDAO;
import daoimpl.MySQLProduktBatchDAO;
import daoimpl.MySQLReceptDAO;
import daointerfaces.DALException;
import dto.OperatoerDTO;
import dto.ProduktBatchDTO;
import dto.ReceptDTO;

public class Validation {

	public int validateUser(MySocket2 connection) {
		Connector mySQLCon = null;
		try {mySQLCon =  new Connector();}
		catch (SQLException e) {
			System.out.println("fejl");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection.sendToServer("RM20 8 \"Indtast oprId\" \"\" \"\"");
		
		MySQLOperatoerDAO oprDAO = new MySQLOperatoerDAO();
		OperatoerDTO opr;
		try {
			String oprTemp = connection.recieveFromServer();
			opr = oprDAO.getOperatoer(Integer.parseInt(oprTemp.substring(8, oprTemp.length() - 1)));
			
			connection.sendToServer("RM20 8 \"" + opr.getOprNavn() + "\" \"\" \"\""); 
			
			String responseTemp = connection.recieveFromServer();
			String response = responseTemp.substring(8, responseTemp.length() - 1);
			
			if(response.equals("y")) {
				return opr.getOprId();
//				validateProductBatch(mySQLCon, connection);
			}
			else  {
				validateUser(connection);
			}
			
		} catch (DALException e) {
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return opr.getOprId();
	}
	
	public void validateProductBatch(Connector mySQLCon, MySocket2 scaleCon) {
		scaleCon.sendToServer("RM20 8 \"Indtast ProduktBatchId\" \"\" \"\"");
		try {
			String PBIdTemp = scaleCon.recieveFromServer();
			String PBId = PBIdTemp.substring(8, PBIdTemp.length() - 1);
			
			MySQLProduktBatchDAO PBDAO = new MySQLProduktBatchDAO();
			MySQLReceptDAO RDAO = new MySQLReceptDAO();
			ProduktBatchDTO PBDTO = PBDAO.getProduktBatch(Integer.parseInt(PBId));
			if(PBDTO.getStatus() == 0) {
				PBDTO.setStatus(1);
				PBDAO.updateProduktBatch(PBDTO);
				ReceptDTO RDTO = RDAO.getRecept(PBDTO.getReceptId());
				scaleCon.sendToServer("D \"" + RDTO.getReceptNavn() + "\"");
			}
			else {
				throw new DALException("Produktbatch ikke tilgængelig"); 
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
