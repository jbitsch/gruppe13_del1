/*
 * 
 */


import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.SwingConstants;

public class GuiControl {

	private static boolean buttonEssential = true;
	private static boolean buttonTest = false;
	private static int dimensionY = 300;
	private static int frameDimensionX = 540;
	private static int panelKeyPadDimensionX = 250;
	private JTextField firstDisplayLine;
	private JTextField secondDisplayLine;
	private JTextField thirdDisplayLine;
	private static JTextField weightReturnTxt;
	private Object lock;
	private char choice;
	private String answer = new String();
	private JLabel taraAmountLbl = new JLabel("0.000Kg");
	private JSpinner jSpBrutto;
	private double brutto;
	private JFrame frame;
	private DecimalFormat bruttoFormat;
	
	public GuiControl() {
		GuiINIT();


	}
	public void visibility (){
		frame.setVisible(true);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void GuiINIT() {
		lock = new Object();
		bruttoFormat = new DecimalFormat("0,000"); 

		//Initialises a base JFrame for our emulator 
		frame = new JFrame("Weight Emulator Server");
		//Sets the JFrames Default action when you click the Close button or in any other way closes the window. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameDimensionX, dimensionY);
		frame.setResizable(false);
		
		frame.setJMenuBar(createMenuBar());
		frame.getContentPane().add(keyPad(), BorderLayout.LINE_END);
		frame.getContentPane().add(weigthMirror(), BorderLayout.CENTER);
		frame.getContentPane().add(returnPane(), BorderLayout.SOUTH);
	
				
	}
	
	public JPanel returnPane(){
		JPanel returnValuePane = new JPanel();	
		returnValuePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblWeightReturnValue = new JLabel("Weight Return Value");
		returnValuePane.add(lblWeightReturnValue);
		
		weightReturnTxt = new JTextField();
		weightReturnTxt.setEditable(false);
		returnValuePane.add(weightReturnTxt);
		weightReturnTxt.setColumns(10);
		
		return returnValuePane;
	}
	
	public JPanel weigthMirror(){
				
		JPanel mirror = new JPanel();
		mirror.setLayout(null);

		
		firstDisplayLine = new JTextField();
		firstDisplayLine.setEditable(false);
		firstDisplayLine.setHorizontalAlignment(SwingConstants.RIGHT);
		firstDisplayLine.setText("LOOOOOOOOSER");
		firstDisplayLine.setBackground(Color.BLACK);
		firstDisplayLine.setForeground(Color.GREEN);
		firstDisplayLine.setBounds(6, 6, 278, 28);
		mirror.add(firstDisplayLine);
		firstDisplayLine.setColumns(10);
		
		secondDisplayLine = new JTextField();
		secondDisplayLine.setEditable(false);
		secondDisplayLine.setHorizontalAlignment(SwingConstants.RIGHT);
		secondDisplayLine.setForeground(Color.GREEN);
		secondDisplayLine.setBackground(Color.BLACK);
		secondDisplayLine.setBounds(6, 27, 278, 28);
		mirror.add(secondDisplayLine);
		secondDisplayLine.setColumns(10);
		
		thirdDisplayLine = new JTextField();
		thirdDisplayLine.setEditable(false);
		thirdDisplayLine.setForeground(Color.GREEN);
		thirdDisplayLine.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		thirdDisplayLine.setBackground(Color.BLACK);
		thirdDisplayLine.setBounds(6, 51, 278, 28);
		mirror.add(thirdDisplayLine);
		thirdDisplayLine.setColumns(10);
		
		
		SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0,200000,0.1);
		jSpBrutto =new JSpinner(model);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(jSpBrutto);
		jSpBrutto.setEditor(editor);
		jSpBrutto.setBounds(150, 107, 134, 28);
		
		mirror.add(jSpBrutto);
		jSpBrutto.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e){
				brutto = (Double)jSpBrutto.getValue();
				firstDisplayLine.setText(bruttoFormat.format(brutto));
				choice ='B';
				ok();
			}
		});
		
		JLabel taraLbl = new JLabel("TARA:");
		taraLbl.setBounds(16, 86, 43, 16);
		mirror.add(taraLbl);
		taraAmountLbl.setBounds(71, 86, 61, 16);
		mirror.add(taraAmountLbl);
		
		return mirror;
	}
	
	
	
	public JPanel keyPad() {
		//keyPad's JPanel initialisering
		JPanel keypad = new JPanel(new GridLayout(4, 4, 5, 9));
		keypad.setPreferredSize(new Dimension(panelKeyPadDimensionX,dimensionY));
		
		//JButtons + ActionListener's
		//JButton 1
		JButton but_nr1 = new JButton("1");
		but_nr1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr1.setEnabled(buttonEssential);
		keypad.add(but_nr1);
		//JButton 2
		JButton but_nr2 = new JButton("2");
		but_nr2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr2.setEnabled(buttonEssential);
		keypad.add(but_nr2);
		//JButton 3
		JButton but_nr3 = new JButton("3");
		but_nr3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr3.setEnabled(buttonEssential);
		keypad.add(but_nr3);
		//JButton interval imellem 0 til 1
		JButton but_RANGE_0to1 = new JButton(" 0-1Kg");
		but_RANGE_0to1.setEnabled(buttonTest);
		keypad.add(but_RANGE_0to1);
		//JButton 4
		JButton but_nr4 = new JButton("4");
		but_nr4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr4.setEnabled(buttonEssential);
		keypad.add(but_nr4);
		//JButton 5
		JButton but_nr5 = new JButton("5");
		but_nr5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr5.setEnabled(buttonEssential);
		keypad.add(but_nr5);
		//JButton 6
		JButton but_nr6 = new JButton("6");
		but_nr6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr6.setEnabled(buttonEssential);
		keypad.add(but_nr6);
		//JButton interval imellem 1 til 5
		JButton but_RANGE_1to5 = new JButton("1-5Kg");
		but_RANGE_1to5.setEnabled(buttonTest);
		keypad.add(but_RANGE_1to5);
		//JButton 7
		JButton but_nr7 = new JButton("7");
		but_nr7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr7.setEnabled(buttonEssential);
		keypad.add(but_nr7);
		//JButton 8
		JButton but_nr8 = new JButton("8");
		but_nr8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr8.setEnabled(buttonEssential);
		keypad.add(but_nr8);
		//JButton 9
		JButton but_nr9 = new JButton("9");
		but_nr9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr9.setEnabled(buttonEssential);
		keypad.add(but_nr9);
		//JButton Interval imellem 1 til 15
		JButton but_RANGE_1to15 = new JButton("1-15Kg");
		but_RANGE_1to15.setEnabled(buttonTest);
		but_RANGE_1to15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		keypad.add(but_RANGE_1to15);
		//JButton CLEAR
		JButton but_CLEAR = new JButton("Clear");
		but_CLEAR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		keypad.add(but_CLEAR);
		//JButton 0
		JButton but_nr0 = new JButton("0");
		but_nr0.setEnabled(buttonEssential);
		but_nr0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		keypad.add(but_nr0);
		//JButton RETURN
		JButton but_RETURN = new JButton("Return");
		but_RETURN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but_nr1.setEnabled(buttonEssential);
		keypad.add(but_RETURN);
		//JButton TARA
		JButton but_TARA = new JButton("TARA");
		but_TARA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//taraAmountLbl.setText(bruttoFormat.format(brutto));
				choice = 'T';
				ok();
			}
		});
		but_TARA.setEnabled(buttonEssential);
		keypad.add(but_TARA);
		
		
		return keypad;
	}
	
	
	
	/**
	 * Creates the menu bar.
	 * 
	 * @return the j menu bar
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu1;
		JMenuItem menuIt;
		
		// creating the JMenuBar container
		menuBar = new JMenuBar();
		// creating the First menu
		menu1 = new JMenu("Menu");
		// adding Keystroke to menu1 in the JMenuBar container
		menu1.setMnemonic(KeyEvent.VK_M);
		// adding menu1 to the JMenuBar container
		menuBar.add(menu1);
		// creating and adding first option in the menu
		menuIt = new JMenuItem("option 1", KeyEvent.VK_1);
		menuIt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menu1.add(menuIt);
		// creating and adding second option in the menu		
		menuIt = new JMenuItem("option 2", KeyEvent.VK_1);
		menuIt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menu1.add(menuIt);
		// creating and adding third option in the menu		
		menuIt = new JMenuItem("option 3", KeyEvent.VK_1);
		menuIt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menu1.add(menuIt);
		
		menu1.addSeparator();
		// logout menu option...
		menuIt = new JMenuItem("Exit", KeyEvent.VK_Q);
		menuIt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menu1.add(menuIt);
		
		
		
		return menuBar;
		
	}
	
	public char getInput() {

		try {
			synchronized(lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {}	
		return choice;
	}
	private void ok() {
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public String getAnswer()
	{	
		return answer;
			
	}
	public void writeCommand(String text)
	{
		weightReturnTxt.setText("Streng modtaget: "+text);
	}
	public void setMessage(String text)
	{
		secondDisplayLine.setText(text);
	}

	public void updateWeight(String net, Double bru, String tar)
	{
		firstDisplayLine.setText(net+" kg");
		jSpBrutto.setValue(bru);
		taraAmountLbl.setText(tar+" Kg");
	}


	public void dispose() {
		frame.dispose();

	}
	public void notificationDialog(String string) {
		JOptionPane.showMessageDialog(frame, string);
	
		
	}
	public double getBrutto() {
		
		return brutto;
	}
	
}
