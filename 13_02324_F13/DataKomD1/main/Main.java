package main;


import weightInterface.*;


public class Main {
	public static void main (String[] args)
	{
		Menu menu = new Menu();	
		WeightController weightInterface = new WeightController(menu);
		weightInterface.run();
	}	
}
