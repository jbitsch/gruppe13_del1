package seq;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
//		Connector mySQLCon = null;
//		try {mySQLCon =  new Connector();}
//		catch (SQLException e) {
//			System.out.println("fejl");
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		connection.sendToServer("RM20 8 \"Indtast OprId\" \" \" \" \" ");
		
		MySQLOperatoerDAO oprDAO = new MySQLOperatoerDAO();
		OperatoerDTO opr = null;
		try {
			System.out.println(connection.recieveFromServer());
			String oprTemp = connection.recieveFromServer();
			System.out.println(oprTemp);
			opr = oprDAO.getOperatoer(Integer.parseInt(oprTemp.substring(7, oprTemp.length())));
			System.out.println(opr);
			
			connection.sendToServer("RM20 8 \"" + opr.getOprNavn() + "\" \" \" \" \" "); 
			
			System.out.println(connection.recieveFromServer());
			String responseTemp = connection.recieveFromServer();
			System.out.println(responseTemp);
			String response = responseTemp.substring(7, responseTemp.length());
			
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
	
	public int validateProductBatch(/*Connector mySQLCon, */MySocket2 scaleCon) {
		Integer PBId = null;
		scaleCon.sendToServer("RM20 8 \"Indtast ProduktBatchId\" \" \" \" \" ");
		try {
			scaleCon.recieveFromServer();
			String PBIdTemp = scaleCon.recieveFromServer();
			PBId = Integer.parseInt(PBIdTemp.substring(7, PBIdTemp.length()));
			
			MySQLProduktBatchDAO PBDAO = new MySQLProduktBatchDAO();
			MySQLReceptDAO RDAO = new MySQLReceptDAO();
			ProduktBatchDTO PBDTO = PBDAO.getProduktBatch(PBId);
			System.out.println(PBDTO.getStatus());
			if(PBDTO.getStatus() == 0) {
				PBDTO.setStatus(1);
				PBDTO.setDatoStart(new Timestamp(System.currentTimeMillis()));
				PBDAO.updateProduktBatch(PBDTO);
				ReceptDTO RDTO = RDAO.getRecept(PBDTO.getRecept().getReceptId());
				scaleCon.sendToServer("D " + RDTO.getReceptNavn());
			}
			else {
				throw new DALException("Produktbatch i prod"); 
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
		
		return PBId;
		
	}
	
}
