package controller;

import userInterface.Menu;
import function.UserLogic;

public class Main {

	public static void main(String[] args) {
		UserLogic userLogic = new UserLogic();
		Menu menu = new Menu(userLogic);
		menu.start();
		//MenuController controller = new MenuController(menu);
	}

}
