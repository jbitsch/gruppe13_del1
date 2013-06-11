package daoimpl;

import java.sql.SQLException;

import connector.Connector;
import daointerfaces.DALException;

public class TEST {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connector c = null;
		try {
			c = new Connector();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MySQLProduktBatchKompDAO ll = new MySQLProduktBatchKompDAO();
		try {
			System.out.println(ll.getProduktBatchKomp(1, 1));
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		MySQLProduktBatchDAO ll = new MySQLProduktBatchDAO();
//		try {
//			System.out.println(ll.getProduktBatchList().get(0).toString());
//		} catch (DALException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		MySQLReceptKompDAO ll = new MySQLReceptKompDAO();
//		try {
//			System.out.println(ll.getReceptKomp(3, 1).toString());
//		} catch (DALException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
				
//		MySQLRaavareBatchDAO ll = new MySQLRaavareBatchDAO();
//		
//		try {
//			System.out.println(ll.getRaavareBatchList(11).g);
//			
//		} catch (DALException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
