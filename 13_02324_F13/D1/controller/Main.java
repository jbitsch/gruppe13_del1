package controller;

import data.Data;
import userInterface.IMenu;
import userInterface.Menu;
import function.Function;

public class Main {

	public static void main(String[] args) {
		
		IMenu menu = new Menu();
		Data data = new Data();
		Function function = new Function(data);
		MenuController controller = new MenuController(menu,function);
		controller.run();
	}

}
