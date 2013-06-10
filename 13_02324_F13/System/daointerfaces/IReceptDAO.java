package daointerfaces;

import java.util.ArrayList;

import dto.ReceptDTO;

public interface IReceptDAO {
	ReceptDTO getRecept(int receptId) throws DALException;
	ArrayList<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO recept) throws DALException;
	void updateRecept(ReceptDTO recept) throws DALException;
}
