package function;
import java.util.Random;



public class PasswordGenerator implements IPasswordGenerator {


	private final int MIN_LENGTH = 6;  
	private final int MAX_LENGTH = 12;  //TODO Debater max længde
	private final int ASCII_UPPERCASE_A = 65;
	private final int ASCII_UPPERCASE_Z = 90;
	private final int ASCII_LOWERCASE_A = 97;
	private final int ASCII_LOWERCASE_Z = 122;

	private char[] uppercaseLetters = new char[ASCII_UPPERCASE_Z - ASCII_UPPERCASE_A + 1];
	private char[] lowercaseLetters = new char[ASCII_LOWERCASE_Z - ASCII_LOWERCASE_A + 1];
	private char[] specielCharacters = {'.', '-', '_', '+', '!', '?', '='};

	private boolean type0Implemented = false;
	private boolean type1Implemented = false;
	private boolean type2Implemented = false;
	private boolean type3Implemented = false;

	public PasswordGenerator() {
		this.generateLetterArrays();
	}

	@Override
	public String generateRandomPassword() {
		Random random = new Random();
		int passwordLength = generateLength(random);
		String randomPassword = "";

		//Sets the 4 first categories
		randomPassword = randomPassword + generateLowercaseLetter(random);
		randomPassword = randomPassword + generateNumber(random);
		randomPassword = randomPassword + generateUppercaseLetter(random);
		randomPassword = randomPassword + generateSpecialCharacter(random);

		for(int i = 4; i < passwordLength; i++) {
			int type = pickCharType(random);

			switch(type) {
			case 0:
				randomPassword = randomPassword + generateUppercaseLetter(random);
				break;
			case 1:
				randomPassword = randomPassword + generateLowercaseLetter(random);
				break;
			case 2:
				randomPassword = randomPassword + generateNumber(random);
				break;
			case 3:
				randomPassword = randomPassword + generateSpecialCharacter(random);
				break;
			}
		}

		//	type0Implemented = false;
		//	type1Implemented = false;
		//	type2Implemented = false;
		//	type3Implemented = false;

		return randomPassword;
	}

	private int generateLength(Random random) {
		int length = random.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + 6; 
		return length;
	}

	private int pickCharType(Random random) {
		int charType = random.nextInt(4);

		//	if(charType == 0) {
		//		type0Implemented = true;
		//	}
		//	else if(charType == 1) {
		//		type1Implemented = true;
		//	}
		//	else if(charType == 2) {
		//		type2Implemented = true;
		//	}
		//	else {
		//		type3Implemented = true;
		//	}
		//
		return charType;
	}

	private char generateUppercaseLetter(Random random) {
		int arrayPlacement = random.nextInt(uppercaseLetters.length);
		return uppercaseLetters[arrayPlacement];
	}

	private char generateLowercaseLetter(Random random) {
		int arrayPlacement = random.nextInt(lowercaseLetters.length);
		return lowercaseLetters[arrayPlacement];
	}

	private int generateNumber(Random random) {
		return random.nextInt(10);
	}

	private char generateSpecialCharacter(Random random) {
		int arrayPlacement = random.nextInt(specielCharacters.length);
		return specielCharacters[arrayPlacement];
	}

	private void generateLetterArrays() { 
		int counter = 0;
		for(int i = ASCII_UPPERCASE_A; i <= ASCII_UPPERCASE_Z; i++) {
			uppercaseLetters[counter] = (char)i;
			counter++;
		}
		counter = 0;

		for(int i = ASCII_LOWERCASE_A; i <= ASCII_LOWERCASE_Z; i++) {
			lowercaseLetters[counter] = (char)i;
			counter++;
		}
	}

	@Override
	public boolean acceptablePassword(String passwordToCheck, int id, String name) { 
		boolean acceptable = false;
		char[] passwordChars = new char[passwordToCheck.length()];
		int spaceCounter = 0;

		if(passwordToCheck.length() >= MIN_LENGTH &&
				passwordToCheck.length() <= MAX_LENGTH) {

			passwordChars = passwordToCheck.toCharArray();



			for(int i = 0; i < passwordChars.length; i++) {

				if(passwordChars[i] == ' ') {
					return false;
				}

				for(int j = 0; j <= 9; j++) {
					if(passwordChars[i] == Character.forDigit(j, 10)) { //TODO check virker
						spaceCounter++;
						type0Implemented = true;
						break;
					}
				}
				for(int j = 0; j < uppercaseLetters.length; j++) {
					if(passwordChars[i] == uppercaseLetters[j]) {
						spaceCounter++;
						type1Implemented = true;
						break;
					}
				}
				for(int j = 0; j < lowercaseLetters.length; j++) {
					if(passwordChars[i] == lowercaseLetters[j]) {
						spaceCounter++;
						type2Implemented = true;
						break;
					}
				}
				for(int j = 0; j < specielCharacters.length; j++) {
					if(passwordChars[i] == specielCharacters[j]) {
						spaceCounter++;
						type3Implemented = true;
						break;
					}
				}
			}
			int typeCounter = 0;

			if(type0Implemented) {
				typeCounter++;
			}
			if(type1Implemented) {
				typeCounter++;
			}
			if(type2Implemented) {
				typeCounter++;
			}
			if(type3Implemented) {
				typeCounter++;
			}

			//	if(typeCounter >= 3) {
			//		for(int i = 0; i < passwordChars.length; i++) {
			//			if(passwordChars[i] == ' ') {
			//				spaceCounter++;
			//			}
			//		}
			//	}
			if(spaceCounter == passwordChars.length && typeCounter >= 3) {
				acceptable = true;
			}
			type0Implemented = false;
			type1Implemented = false;
			type2Implemented = false;
			type3Implemented = false;
		}
		return acceptable;
	}
	
	private boolean checkIfIdIsPresent(String passwordToCheck, int id) {
		boolean output = true;
		String idString = "" + id;
		char[] idArray = idString.toCharArray();
		char[] passwordArray = passwordToCheck.toCharArray();
		int idCounter = 0;
		int digitCounter = 0;
		
		for(int i = 0; i < idArray.length;) {
			for(int j = 0; j < passwordArray.length; j++) {
				if(idArray[i] == passwordArray[j]) {
					idCounter++;
					break;
				}
			}
		}
		if(idCounter != 2) {
			return true;
		}
		else {
			for(int i = 0; i < passwordArray.length; i++) {
				if(idArray[digitCounter] == passwordArray[i]) {
					digitCounter++;
					if(digitCounter == 2) {
						output = false;
					}
				}
				else {
					digitCounter = 0;
				}
			}
		}
		return output;
	}
	
	private boolean checkIfNameIsPresent(String passwordToCheck, String name) {
		boolean output = true;
		char[] nameArray = name.toCharArray();
		char[] passwordArray = passwordToCheck.toCharArray();
		int spaceCounter = 0;
		
		for(int i = 0; i < nameArray.length; i++) {
			if(nameArray[i] == ' ') {
				spaceCounter++;
			}
		}
		
		
	}
}
