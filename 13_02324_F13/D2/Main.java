
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Data d = new Data();
		Menu m = new Menu();
		Controller con = new Controller(m,d);
		int portdst = 8000;
        if (args.length > 0)
            portdst = Integer.parseInt(args[0]);
        
        con.run(portdst);

	}

}
