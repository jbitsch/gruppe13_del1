package Weight;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		Data d = new Data();
	
		WeightGUI wg = new WeightGUI();
		Controller con = new Controller(wg,d);
		int portdst = 8000;
        if (args.length > 0)
            portdst = Integer.parseInt(args[0]);
        
        con.run(portdst);

	}

}
