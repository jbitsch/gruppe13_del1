package main;


import weightInterface.*;


public class Main2 {
	public static void main (String[] args)
	{
		Menu2 menu = new Menu2();	
		WeightController2 weightInterface = new WeightController2(menu);
		weightInterface.run();
	}	
}
