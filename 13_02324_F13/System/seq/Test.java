package seq;

import java.io.IOException;
import java.sql.SQLException;

import connector.Connector;

public class Test {

	public static void main(String[] args) {
		try {
			new Connector();
		} catch (InstantiationException e) {
			System.out.println("DB fejl");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("DB fejl");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("DB fejl");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB fejl");
			e.printStackTrace();
		}
		
		Validation a = new Validation();
		
		MySocket2 b = new MySocket2();
		try {
//			b.connect("localhost", 4567);
//			b.connect("localhost", 8000);
			b.connect("169.254.2.2", 8000);
			a.recieveUserId(b, 0);
			a.recieveProductBatchId(b, 0);
			System.out.println("done");
		} catch (IOException e) {
			System.out.println("noget galt");
			e.printStackTrace();
		}
		
	}

}
