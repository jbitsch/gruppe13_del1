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
	
	private char obsoleteChar = '"';




	public void recieveUserId(MySocket2 connection, int message) {

		if(message == 0) {
			connection.sendToServer("RM20 8 \"Indtast OprId\" \" \" \"&3\" ");
		}
		else if(message == 1) {
			connection.sendToServer("RM20 8 \"OprId igen: ikke tal\" \" \" \"&3\" ");
		}
		else {
			connection.sendToServer("RM20 8 \"OprId igen: findes ikke\" \" \" \"&3\" ");
		}

		MySQLOperatoerDAO oprDAO = new MySQLOperatoerDAO();
		OperatoerDTO opr = null;
		try {
			if(!connection.recieveFromServer().equals("RM20 B")) {
				System.out.println(connection.recieveFromServer());
			}
			
			String oprTemp = connection.recieveFromServer();
			oprTemp = oprTemp.replaceAll("\"", "");
			System.out.println(oprTemp);
			opr = oprDAO.getOperatoer(Integer.parseInt(oprTemp.substring(7, oprTemp.length())));
			System.out.println(opr);
			
			

			validateName(connection, 0, opr);


		}catch (DALException e) {
			recieveUserId(connection, 2);
			e.printStackTrace();
		}catch (NumberFormatException e) {
			recieveUserId(connection, 1);
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int validateName(MySocket2 connection, int message, OperatoerDTO opr) {


		try {
			if(message == 0){

				//TODO: se på opr-nanvn længde
				connection.sendToServer("RM20 8 \"" + opr.getOprNavn() + " [y/n]\" \" \" \"&2\" "); 
			}
			else {
				connection.sendToServer("RM20 8 \"forkert svar: [y/n]\" \" \" \"&2\" ");
			}
			System.out.println(connection.recieveFromServer());
			String responseTemp;
			responseTemp = connection.recieveFromServer();
			responseTemp = responseTemp.replaceAll("\"", "");
			System.out.println(responseTemp);
			String response = responseTemp.substring(7, responseTemp.length());

			if(response.equals("n")) {
				recieveUserId(connection, 0);
			}
			else if(!response.equals("y"))  {
				validateName(connection, 1, opr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opr.getOprId();
	}

	public int validateUser(MySocket2 connection) {

		connection.sendToServer("RM20 8 \"Indtast OprId\" \"&3\" \" \" ");

		MySQLOperatoerDAO oprDAO = new MySQLOperatoerDAO();
		OperatoerDTO opr = null;
		try {
			System.out.println(connection.recieveFromServer());
			String oprTemp = connection.recieveFromServer();
			oprTemp = oprTemp.replaceAll("\"", "");
			System.out.println(oprTemp);
			opr = oprDAO.getOperatoer(Integer.parseInt(oprTemp.substring(7, oprTemp.length())));
			System.out.println(opr);

			connection.sendToServer("RM20 8 \"" + opr.getOprNavn() + " [y/n]\" \" \" \" \" "); 

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
			validateUser(connection);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return opr.getOprId();
	}

	public int validateProductBatch(MySocket2 scaleCon) {
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

	public int recieveProductBatchId(MySocket2 scaleCon, int message) {
		Integer PBId = null;
		if(message == 0) {
			scaleCon.sendToServer("RM20 8 \"Indtast PBId\" \" \" \"&3\" ");
		}
		else if (message == 1) {
			scaleCon.sendToServer("RM20 8 \"PBId igen: ikke tal\" \" \" \"&3\" ");
		}
		else if (message == 2){
			scaleCon.sendToServer("RM20 8 \"PBId igen: ugyldig\" \" \" \"&3\" ");
		}
		else {
			scaleCon.sendToServer("RM20 8 \"PBId igen: status\" \" \" \"&3\" ");
		}
		try {
			scaleCon.recieveFromServer();
			String PBIdTemp = scaleCon.recieveFromServer();
			PBIdTemp = PBIdTemp.replaceAll("\"", "");
			PBId = Integer.parseInt(PBIdTemp.substring(7, PBIdTemp.length()));

			MySQLProduktBatchDAO PBDAO = new MySQLProduktBatchDAO();
			MySQLReceptDAO RDAO = new MySQLReceptDAO();
			ProduktBatchDTO PBDTO = PBDAO.getProduktBatch(PBId);
			if(PBDTO.getStatus() == 0) {
				PBDTO.setStatus(1);
				PBDTO.setDatoStart(new Timestamp(System.currentTimeMillis()));
				PBDAO.updateProduktBatch(PBDTO);
				ReceptDTO RDTO = RDAO.getRecept(PBDTO.getRecept().getReceptId());
				scaleCon.sendToServer("D \"" + RDTO.getReceptNavn() + "\" ");
			}
			else {
				recieveProductBatchId(scaleCon, 3);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			recieveProductBatchId(scaleCon, 1);
			e.printStackTrace();
		} catch (DALException e) {
			e.printStackTrace();
			recieveProductBatchId(scaleCon, 2);
			e.printStackTrace();
		}

		return PBId;
	}

}
