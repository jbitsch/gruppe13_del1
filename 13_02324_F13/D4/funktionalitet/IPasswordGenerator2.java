package funktionalitet;

public interface IPasswordGenerator2 {

	/**
	 * A method which checks if a password follows the regulations
	 * @param passwordToCheck The password which is checked
	 * @return A boolean value. True if the password is acceptable, fales if not
	 */
	boolean acceptablePassword(String passwordToCheck, int id, String name);
	
	/**
	 * A method which generates a semi-randomzied password
	 * @return A String containing the semi-random password
	 */
	String generateRandomPassword();
}
