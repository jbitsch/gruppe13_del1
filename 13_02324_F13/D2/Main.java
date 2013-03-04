
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Data d = new Data();
		Menu m = new Menu();
		Controller con = new Controller(m,d);
		con.run();

	}

}
