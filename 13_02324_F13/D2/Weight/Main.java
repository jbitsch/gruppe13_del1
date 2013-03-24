package Weight;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Data d = new Data();
		JFrame frame = new JFrame();
		Object[] options = { "Consol!", "GUI!" };
		int n = JOptionPane.showOptionDialog(frame,
				"Would you like to use console or GUI?", "Menu Choice",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (n == JOptionPane.YES_NO_OPTION) {
			Menu m = new Menu();
			Controller con = new Controller(m, d);
			int portdst = 8000;
			if (args.length > 0)
				portdst = Integer.parseInt(args[0]);

			con.run(portdst);
		} else if (n == JOptionPane.NO_OPTION) {
			GuiControl wg = new GuiControl();
			Controller2 con = new Controller2(wg, d);
			int portdst = 8000;
			if (args.length > 0)
				portdst = Integer.parseInt(args[0]);

			con.run(portdst);

		} else {
			System.out.println("Closing down Program.");
			System.exit(0);
		}

		/*
		 * Data d = new Data(); Menu m = new Menu(); Controller con = new
		 * Controller(m,d); int portdst = 8000; if (args.length > 0) portdst =
		 * Integer.parseInt(args[0]); con.run(portdst);
		 */

	}

}
