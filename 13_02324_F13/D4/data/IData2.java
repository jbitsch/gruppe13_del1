package data;

import java.util.ArrayList;


public interface IData2 {
	OperatoerDTO2 getOperatoer(int oprId) ;
	ArrayList<OperatoerDTO2> getAllOperatoer();
	void createOperatoer(OperatoerDTO2 opr) ;
	void updateOperatoer(OperatoerDTO2 opr) ;
	void deleteOperatoer(OperatoerDTO2 opr) ;
	boolean attemptLogin(int ID, String password) ;
}
