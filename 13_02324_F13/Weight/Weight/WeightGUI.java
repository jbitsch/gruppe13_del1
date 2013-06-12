package Weight;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class WeightGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField netto;
	private JTextField message;
	private JTextField brutto;
	private JTextField fieldReturn;
	private JLabel lblStrengModtaget;
	private Object lock;
	private JButton btnEnter;
	private JButton btnYes;
	private JButton btnNo;
	private JButton btnQuit;
	private JButton btnTara;
	private JButton btnUp;
	private JButton btnDown;
	private char choise;
	private String answer;
	private JLabel lblTaraVgt;
	
	public WeightGUI() {
		setResizable(false);
		
		lock = new Object();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		createButtons();
		
		message = new JTextField();
		message.setEditable(false);
		message.setColumns(10);
		message.setBounds(12, 47, 270, 30);
		contentPane.add(message);
		
		lblStrengModtaget = new JLabel("Streng modtaget: ");
		lblStrengModtaget.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStrengModtaget.setBounds(11, 157, 473, 30);
		contentPane.add(lblStrengModtaget);
		
		brutto = new JFormattedTextField();
		brutto.setEditable(false);
		brutto.setBounds(207, 110, 76, 22);
		contentPane.add(brutto);
		brutto.setColumns(10);
		brutto.setText("0.000 kg");
		
		netto = new JTextField();
		netto.setEditable(false);
		netto.setBounds(58, 13, 224, 30);
		contentPane.add(netto);
		netto.setColumns(10);
		netto.setText("0.000 kg");
		
		fieldReturn = new JTextField();
		fieldReturn.setEditable(false);
		fieldReturn.setBounds(294, 17, 190, 22);
		contentPane.add(fieldReturn);
		fieldReturn.setColumns(10);
		
		lblTaraVgt = new JLabel("Tara vaegt: 0.000 kg");
		lblTaraVgt.setBounds(12, 123, 153, 16);
		contentPane.add(lblTaraVgt);
		
		JLabel lblChangeBruto = new JLabel("Change bruto:");
		lblChangeBruto.setBounds(183, 90, 100, 16);
		contentPane.add(lblChangeBruto);
		
		JLabel lblNetto = new JLabel("Netto:");
		lblNetto.setBounds(12, 18, 56, 16);
		contentPane.add(lblNetto);
	}
	public char getInput() {

		try {
			synchronized(lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {}	
		return choise;
	}
	private void ok() {
		synchronized (lock) {
			lock.notify();
		}
	}

	public String getAnswer()
	{	
		return answer;
	}
	public void writeCommand(String text)
	{
		lblStrengModtaget.setText("Streng modtaget: "+text);
	}
	public void setMessage(String text)
	{
		message.setText(text);
	}
	public void updateWeight(String net, String bru, String tar)
	{
		netto.setText(net+" kg");
		brutto.setText(bru+" kg");
		lblTaraVgt.setText("Tara vaegt: "+tar+ " kg");
	}
	public void setEdit(boolean one, boolean two)
	{
		btnEnter.setEnabled(one);
		btnYes.setEnabled(one);
		btnNo.setEnabled(one);
		btnQuit.setEnabled(one);
		
//		btnTara.setEnabled(two);
//		btnDown.setEnabled(two);
//		btnUp.setEnabled(two);
	}
	private void createButtons() {
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fieldReturn.setText("");
			}
		});
		btnClear.setBounds(295, 120, 63, 25);
		contentPane.add(btnClear);
		
		btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEnter.setEnabled(false);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				answer = fieldReturn.getText();
				fieldReturn.setText("");
				choise = 'E';
				ok();
			}
		});
		btnEnter.setBounds(421, 120, 63, 25);
		contentPane.add(btnEnter);
		
		btnYes = new JButton("Yes(Y)");
		btnYes.setEnabled(false);
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				answer = "Y";
				choise = 'E';
				fieldReturn.setText("");
				ok();
			}
		});
		btnYes.setBounds(492, 42, 93, 25);
		contentPane.add(btnYes);
		
		btnNo = new JButton("No(N)");
		btnNo.setEnabled(false);
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer = "N";
				choise = 'E';
				fieldReturn.setText("");
				ok();
			}
		});
		btnNo.setBounds(492, 67, 93, 25);
		contentPane.add(btnNo);
		
		btnQuit = new JButton("Quit(Q)");
		btnQuit.setEnabled(false);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answer = "Q";
				choise = 'E';
				fieldReturn.setText("");
				ok();
			}
		});
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnQuit.setBounds(492, 93, 93, 25);
		contentPane.add(btnQuit);
		
		JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"0");
			}
		});
		btn0.setBounds(358, 120, 63, 25);
		contentPane.add(btn0);
		
		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"1");
			}
		});
		btn1.setBounds(295, 42, 63, 25);
		contentPane.add(btn1);
		
		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"2");
			}
		});
		btn2.setBounds(358, 42, 63, 25);
		contentPane.add(btn2);
		
		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"3");
			}
		});
		btn3.setBounds(421, 42, 63, 25);
		contentPane.add(btn3);
		
		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"4");
			}
		});
		btn4.setBounds(295, 67, 63, 25);
		contentPane.add(btn4);
		
		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"5");
			}
		});
		btn5.setBounds(358, 67, 63, 25);
		contentPane.add(btn5);
		
		JButton btn6 = new JButton("6");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"6");
			}
		});
		btn6.setBounds(421, 67, 63, 25);
		contentPane.add(btn6);
		
		JButton btn7 = new JButton("7");
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"7");
			}
		});
		btn7.setBounds(295, 93, 63, 25);
		contentPane.add(btn7);
		
		JButton btn8 = new JButton("8");
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"8");
			}
		});
		btn8.setBounds(358, 93, 63, 25);
		contentPane.add(btn8);
		
		JButton btn9 = new JButton("9");
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"9");
			}
		});
		btn9.setBounds(421, 93, 63, 25);
		contentPane.add(btn9);
		
		btnTara = new JButton("Tara");
		btnTara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choise = 'T';
				ok();
			}
		});
		btnTara.setBounds(12, 91, 97, 25);
		contentPane.add(btnTara);
		
		btnDown = new JButton("");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choise = 'D';
				ok();
			}
		});
		btnDown.setIcon(new ImageIcon(WeightGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnDown.setBounds(183, 121, 23, 11);
		contentPane.add(btnDown);
		
		btnUp = new JButton("");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choise = 'U';
				ok();
			}
		});
		btnUp.setIcon(new ImageIcon(WeightGUI.class.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		btnUp.setBounds(183, 109, 23, 11);
		contentPane.add(btnUp);
		
		JButton btnClickToQuit = new JButton("Click to Quit");
		btnClickToQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choise = 'Q';
				ok();	
			}
		});
		btnClickToQuit.setBounds(12, 200, 139, 25);
		contentPane.add(btnClickToQuit);
	}
}
