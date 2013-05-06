package funktionalitet;

import java.util.ArrayList;

import data.OperatoerDTO2;


public interface IFunktionalitet2 {
	ArrayList<OperatoerDTO2> getUsers() ;
	OperatoerDTO2 getUser(int ID) ;
	int createUser(String name, String cpr,String password,String ini); 
	void updateUser(int id, String navn, String ini, String cpr, String password) ;
	void deleteUser(OperatoerDTO2 user) ;
	boolean login(String password, String ID); 
	int unusedId(ArrayList<OperatoerDTO2> personer);
	boolean checkOldPassword(OperatoerDTO2 user, String password);
	double calculateWeight(double tarra, double brutto);
	boolean checkPassword(String password, int id);
	boolean checkName(String navn);
	boolean checkIni(String ini);
	boolean checkCpr(String cpr);
	boolean checkPassword(String password);

}
