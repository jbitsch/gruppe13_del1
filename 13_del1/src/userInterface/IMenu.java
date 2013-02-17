package userInterface;

public interface IMenu {
	public void start();
	public void weightApplication();
	public int userID();
	public String getPassword();
	public int showMenu(String header, String[] options);
	public void outString(String text);
	public String getInput();
}
