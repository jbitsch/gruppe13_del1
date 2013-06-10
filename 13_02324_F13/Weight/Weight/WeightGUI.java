package Weight;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JTextArea;


public class WeightGUI extends JFrame {

	private JPanel contentPane;
	private JTextField netto;
	private JTextField message;
	private JTextField brutto;
	private JTextField fieldReturn;
	private JLabel lblStrengModtaget;
	private Object lock;
	private JButton btnEnter;
	private JButton btnTara;
	private JButton btnUp;
	private JButton btnDown;
	private char choise;
	private int answer;
	private JLabel lblTaraVgt;
	
	
	
	
	public WeightGUI() {
		
		lock = new Object();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
				answer = Integer.parseInt(fieldReturn.getText());
				choise = 'E';
				ok();
			}
		});
		btnEnter.setBounds(421, 120, 63, 25);
		contentPane.add(btnEnter);
		
		JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldReturn.getText().length() < 8)
					fieldReturn.setText(fieldReturn.getText()+"0");
			}
		});
		btn0.setBounds(358, 120, 63, 25);
		contentPane.add(btn0);
		
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
		
		netto = new JTextField();
		netto.setEditable(false);
		netto.setBounds(12, 13, 270, 30);
		contentPane.add(netto);
		netto.setColumns(10);
		netto.setText("0.000 kg");
		
		btnTara = new JButton("Tara");
		btnTara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choise = 'T';
				ok();
			}
		});
		btnTara.setBounds(12, 91, 97, 25);
		contentPane.add(btnTara);
		
		message = new JTextField();
		message.setEditable(false);
		message.setColumns(10);
		message.setBounds(12, 47, 270, 30);
		contentPane.add(message);
		
		
		brutto = new JFormattedTextField();
		brutto.setEditable(false);
		brutto.setBounds(207, 110, 76, 22);
		contentPane.add(brutto);
		brutto.setColumns(10);
		brutto.setText("0.000 kg");
		
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
		
		lblStrengModtaget = new JLabel("Streng modtaget: ");
		lblStrengModtaget.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStrengModtaget.setBounds(11, 157, 473, 30);
		contentPane.add(lblStrengModtaget);
		
		fieldReturn = new JTextField();
		fieldReturn.setEditable(false);
		fieldReturn.setBounds(294, 17, 190, 22);
		contentPane.add(fieldReturn);
		fieldReturn.setColumns(10);
		
		JButton btnClickToQuit = new JButton("Click to Quit");
		btnClickToQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choise = 'Q';
				ok();	
			}
		});
		btnClickToQuit.setBounds(12, 200, 139, 25);
		contentPane.add(btnClickToQuit);
		
		lblTaraVgt = new JLabel("Tara v\u00E6gt: 0.000 kg");
		lblTaraVgt.setBounds(12, 123, 153, 16);
		contentPane.add(lblTaraVgt);
		
		JLabel lblChangeBruto = new JLabel("Change bruto:");
		lblChangeBruto.setBounds(183, 90, 100, 16);
		contentPane.add(lblChangeBruto);
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

	public int getAnswer()
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
		lblTaraVgt.setText("Tara v�gt: "+tar+ " kg");
	}
	public void setEdit(boolean one, boolean two)
	{
		btnEnter.setEnabled(one);
		btnTara.setEnabled(two);
		btnDown.setEnabled(two);
		btnUp.setEnabled(two);
	}
}
