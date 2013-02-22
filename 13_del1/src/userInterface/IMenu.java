package userInterface;

public interface IMenu {
	void start();
	int userID();
	String getPassword();
	int showMenu(String header, String[] options);
	void outString(String text);
	String getInput();
	double weightApplication(String text);
}
