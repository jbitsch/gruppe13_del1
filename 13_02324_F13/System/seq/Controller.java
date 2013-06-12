package seq;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import connector.Connector;
import daoimpl.MySQLOperatoerDAO;
import daoimpl.MySQLProduktBatchDAO;
import daoimpl.MySQLRaavareBatchDAO;
import daoimpl.MySQLProduktBatchKompDAO;
import daoimpl.MySQLReceptDAO;
import daoimpl.MySQLReceptKompDAO;
import daointerfaces.DALException;
import dto.OperatoerDTO;
import dto.ProduktBatchDTO;
import dto.ProduktBatchKompDTO;
import dto.RaavareBatchDTO;
import dto.ReceptDTO;
import dto.ReceptKompDTO;


public class Controller {
	
	private OperatoerDTO Opr_PB = null;
	
	private MySocket2 socketConnect = new MySocket2();
	private Scanner input = new Scanner(System.in);
	private String ip;
	private int port;
	private MySQLRaavareBatchDAO raavareBatchDB = new MySQLRaavareBatchDAO();
	private MySQLProduktBatchKompDAO produktBatchKompDB = new MySQLProduktBatchKompDAO();
	private MySQLProduktBatchDAO produktBatchDB = new MySQLProduktBatchDAO();
	private MySQLReceptKompDAO receptKompDB = new MySQLReceptKompDAO();
	private List<ReceptKompDTO> receptKompDBList;

	public Controller(){
	}
	
	public void Con(){
		try
		{
			System.out.println("Indtast ip:");
			ip = input.next();
			System.out.println("Indtast port:");
			port = Integer.parseInt((input.next()));
			socketConnect.connect(ip, port);
		}
		catch (IOException IO)
		{
			System.out.println("Forkert ip/port");
			Con();
		}
		catch (NumberFormatException Ne){
			System.out.println("Forkert ip/port");
			Con();
		}
		mySqlCon();
		System.out.println("hejconnection");
		sekvens();
	}
	
	public void mySqlCon(){
		try{
			Connector mySql = new Connector();
			System.out.println("MySQL connection");
		}
		catch(Exception e){
			System.out.println("Connecting to MySQL server failed, trying agin in 30 secs");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e1) {
			}
			mySqlCon();	
		}
	}
	
	private void færdig(int pbId) throws IOException{
		String response = "";
		try {
			produktBatchDB.updateProduktBatch(new ProduktBatchDTO(pbId, produktBatchDB.getProduktBatch(pbId).getRecept(), 2, produktBatchDB.getProduktBatch(pbId).getDatoStart(), new Timestamp(System.currentTimeMillis()), produktBatchDB.getProduktBatch(pbId).getOpr()));
		} catch (DALException e) {
		}
		for(int i = 0; i < 3; i++){
		socketConnect.sendToServer("M12 2");
		}
		socketConnect.sendToServer("DET VAR FLOT HVA!?" + "\" \" \" \" \" ");
		System.out.println("Modtager svar fra text clear: \n" + socketConnect.recieveFromServer().toUpperCase());
		while(true){		
			response = socketConnect.recieveFromServer().toUpperCase();
			System.out.println(response);
			if(response.startsWith("RM20")) {
					if(response.equals("RM20 A \" \"")) {
						sekvens();
						break;
					}
					else{
						socketConnect.sendToServer("DET VAR FLOT HVA!?" + "\" \" \" \" \" ");
						System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
					}
				}
			else{
				socketConnect.sendToServer("DET VAR FLOT HVA!?" + "\" \" \" \" \" ");
				System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
			}
		}

		
	}
	
	private void afvejning(ReceptKompDTO currentReceptKomp, int pbId) throws IOException{
		// variabler kun brugt i afvejning
		String raavareNavn = "";
		double taraBeholderVægt;
		int raavareBatchSelect;
		double raavareStock = 0;
		double raavareMaengde = 0;
		double raavareTolerance = 0;
		double raavareValue = 0;
		String response = "";
		
		//Hiv info fra database
		try {
			raavareNavn = currentReceptKomp.getRaavare().getRaavareNavn();
			raavareTolerance = currentReceptKomp.getTolerance();
			raavareValue = currentReceptKomp.getNomNetto();
		} catch (Exception e) {
			e.printStackTrace();
	}
		
	// STEP 7. 8. START
	socketConnect.sendToServer("DW");
	System.out.println("Modtager svar fra text clear: \n" + socketConnect.recieveFromServer().toUpperCase());
	socketConnect.sendToServer("K 3");
	System.out.println("Modtager svar fra K 3: \n" + socketConnect.recieveFromServer().toUpperCase());
	System.out.println("Operatøren kontrollerer at vægten er ubelastet og trykker ’ok’");
	socketConnect.sendToServer("RM20 8 \"Empty the weight\" \" \" \" \" ");
	System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
	while(true){		
		response = socketConnect.recieveFromServer().toUpperCase();
		System.out.println(response);
		if(response.startsWith("RM20")) {
				if(response.equals("RM20 A \" \"")) {
					socketConnect.sendToServer("DW");
					System.out.println("Modtager svar fra text clear: \n" + socketConnect.recieveFromServer().toUpperCase());
					System.out.println("Weight emptied, response: " + response);
					socketConnect.sendToServer("T");
					System.out.println("Modtager svar fra tarering: \n" + socketConnect.recieveFromServer().toUpperCase());
					response = "";
					break;
				}
				else{
					socketConnect.sendToServer("RM20 8 \"Empty the weight\" \" \" \" \" ");
					System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
				}
			}
		else{
			socketConnect.sendToServer("RM20 8 \"Empty the weight\" \" \" \" \" ");
			System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
		}
	}
	// STEP 7. 8. SLUT
	
	// STEP 9. 10. 11. 12. START
	System.out.println("Vægten beder om tara beholder.");
	socketConnect.sendToServer("RM20 8 \"Placer tarabeholder\" \" \" \" \" ");
	System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
	while(true){
		response = socketConnect.recieveFromServer().toUpperCase();
		System.out.println(response);
		if(response.startsWith("RM20")) {
				if(response.equals("RM20 A \" \"")) {
					socketConnect.sendToServer("DW");
					System.out.println("Modtager svar fra text clear: \n" + socketConnect.recieveFromServer().toUpperCase());
					socketConnect.sendToServer("S");
					while(!response.contains("kg")) {
					response = socketConnect.recieveFromServer();
					}
					taraBeholderVægt = Double.parseDouble((response.substring(9, 14)));
					System.out.println("Taracontainer placed, response: " + response + ", taravægt: " + taraBeholderVægt);
					socketConnect.sendToServer("T");
					System.out.println("Modtager svar fra tarering: \n" + socketConnect.recieveFromServer().toUpperCase());
					break;
				}
				else{
					socketConnect.sendToServer("RM20 8 \"Placer tarabeholder\" \" \" \" \" ");
					System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
				}
			}
		else{
			socketConnect.sendToServer("RM20 8 \"Placer tarabeholder\" \" \" \" \" ");
			System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
		}
	}
	
	// STEP 13.
	System.out.println("Vægten beder om raavarebatch nummer paa raavaren: " + raavareNavn);
	socketConnect.sendToServer("RM20 8 \"RBnr: "+ raavareNavn + "\" \" \" \"&3\" ");
	System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
	while(true){
		response = socketConnect.recieveFromServer().toUpperCase();
		System.out.println(response);
		if(response.startsWith("RM20 A")) {
			try {
				raavareBatchSelect = Integer.parseInt(response.substring(8, response.length()-1));
				System.out.println("raavarebatch selected: " + raavareBatchSelect);
				try{
					raavareStock = raavareBatchDB.getRaavareBatch(raavareBatchSelect).getMaengde();
					System.out.println("RBStock: " + raavareStock);
					System.out.println("Minimum vægt med tol: " +((currentReceptKomp.getNomNetto() * raavareTolerance/100) + currentReceptKomp.getNomNetto())+ " uden tol: " + currentReceptKomp.getNomNetto());
					if(currentReceptKomp.getRaavare().getRaavareId() == raavareBatchDB.getRaavareBatch(raavareBatchSelect).getRaavare().getRaavareId()) {
						if(raavareStock >= ((currentReceptKomp.getNomNetto() * raavareTolerance/100) + currentReceptKomp.getNomNetto())) {
						socketConnect.sendToServer("DW");
						System.out.println("Modtager svar fra text clear: \n" + socketConnect.recieveFromServer().toUpperCase());
						break;
						}
						else {
						socketConnect.sendToServer("RM20 8 \"RB min. "+ ((currentReceptKomp.getNomNetto() * raavareTolerance/100) + currentReceptKomp.getNomNetto()) + " kg" + "\" \" \" \" \" ");
						System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
						}
					}
					else {
						socketConnect.sendToServer("RM20 8 \"Forkert RB"+ "\" \" \" \"&3\" ");
					}
				}
				catch(DALException e){
					e.printStackTrace();
					socketConnect.sendToServer("RM20 8 \"RB findes ikke" + "\" \" \" \"&3\" ");
					System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
				}	
			}
			catch(NumberFormatException e){
				socketConnect.sendToServer("RM20 8 \"Angiv RBnr ved tal" + "\" \" \" \"&3\" ");
				System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
				}	
			}
		else{
			socketConnect.sendToServer("RM20 8 \"RBnr: "+ raavareNavn + "\" \" \" \"&3\" ");
			System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
		}
	}
	// STEP 13 SLUT
	
	// STEP 14 START
	System.out.println("Vægten beder om afvejning af raavaren: " + raavareNavn);
	socketConnect.sendToServer("RM20 8 \"Afvej: " + raavareValue + " kg" + "\" \" \" \" \" ");
	System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
	while(true){
		response = socketConnect.recieveFromServer().toUpperCase();
		System.out.println(response);
		if(response.startsWith("K")) {
				if(response.equals("K C 4")) {
					socketConnect.sendToServer("S");
					while(!response.contains("kg")) {
						socketConnect.sendToServer("S");
						response = socketConnect.recieveFromServer();
					}
					raavareMaengde = Double.parseDouble((response.substring(9, 14)));
					System.out.println("Raavare manengde: " + raavareMaengde);
					System.out.println("Raavare tolerance: " + raavareTolerance);
					System.out.println("Raavare value: " + raavareValue);
					if(raavareMaengde <= raavareValue + (raavareValue*raavareTolerance/100) && raavareMaengde >= raavareValue - (raavareValue*raavareTolerance/100)){
						System.out.println("Raavare afvejet korrekt, response: " + response + ", raavareVægt: " + raavareMaengde);
						try {
							produktBatchKompDB.createProduktBatchKomp(new ProduktBatchKompDTO(pbId, raavareBatchDB.getRaavareBatch(raavareBatchSelect), taraBeholderVægt, raavareMaengde));
							System.out.println("Ny lager maengde" + (raavareStock - raavareMaengde));
							raavareBatchDB.updateRaavareBatch(new RaavareBatchDTO(raavareBatchSelect, currentReceptKomp.getRaavare(), (raavareStock - raavareMaengde), raavareBatchDB.getRaavareBatch(raavareBatchSelect).getDato()));
						} catch (DALException e) {	
							System.out.println("database error");
						}
						break;
					}
					else
					{
						socketConnect.sendToServer("RM20 8 \"Afvej: " + raavareValue + " kg" + "\" \" \" \" \" ");
						System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
					}
				}
				else{
				socketConnect.sendToServer("RM20 8 \"Afvej: " + raavareValue + " kg" + "\" \" \" \" \" ");
				System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
				}
			}
		else if(response.startsWith("RM20")){
		}
		else{
			socketConnect.sendToServer("RM20 8 \"Afvej: " + raavareValue + " kg" + "\" \" \" \" \" ");
			System.out.println("Modtager svar fra RM20: \n" + socketConnect.recieveFromServer().toUpperCase());
		}
	}
	// STEP 14 Slut	
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
				PBDTO.setOpr(Opr_PB);
				PBDAO.updateProduktBatch(PBDTO);
				ReceptDTO RDTO = RDAO.getRecept(PBDTO.getRecept().getReceptId());
				scaleCon.sendToServer("RM20 8 \""+RDTO.getReceptNavn()+"\" \" \" \"&3\" ");
				scaleCon.recieveFromServer();
				scaleCon.recieveFromServer();
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
	
	private int validateName(MySocket2 connection, int message, OperatoerDTO opr) {


		try {
			if(message == 0){
				if(opr.getOprNavn().length() > 14) {
				connection.sendToServer("RM20 8 \"" + opr.getOprNavn().substring(0, 14) + " [y/n]\" \" \" \"&1\" "); 
			}
				else {
					connection.sendToServer("RM20 8 \"" + opr.getOprNavn() + " [y/n]\" \" \" \"&1\" ");
				}
			}
			else {
				connection.sendToServer("RM20 8 \"forkert svar: [y/n]\" \" \" \"&1\" ");
			}
			System.out.println(connection.recieveFromServer());
			String responseTemp;
			responseTemp = connection.recieveFromServer();
			responseTemp = responseTemp.replaceAll("\"", "");
			System.out.println(responseTemp);
			String response = responseTemp.substring(7, responseTemp.length());

			if(response.equals("N")) {
				recieveUserId(connection, 0);
			}
			else if(!response.equals("Y"))  {
				validateName(connection, 1, opr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opr.getOprId();
	}

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

	
	private void sekvens()
	{
		int pbId = 0;
		try {
			//ADD STEP 1 - 6 her
			// update pbId som bruges af step 7-15
			/////////////
			
			recieveUserId(socketConnect, 0);
			pbId = recieveProductBatchId(socketConnect, 0);
			
			receptKompDBList = receptKompDB.getReceptKompList(produktBatchDB.getProduktBatch(pbId).getRecept().getReceptId());
			System.out.println("size: " + receptKompDBList.size());
			for(int i = 0; i < receptKompDBList.size(); i++) {
			afvejning(receptKompDBList.get(i), pbId);
				}
			færdig(pbId);
			} catch (Exception e) {
		}
		
	}
}
