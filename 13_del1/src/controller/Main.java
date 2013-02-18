package controller;

import data.Data;
import userInterface.Menu;
import function.Function;

public class Main {

	public static void main(String[] args) {
		
		Menu menu = new Menu();
		Data data = new Data();
		Function function = new Function(data);
		MenuController controller = new MenuController(menu,function);
		controller.run();
	}

}
