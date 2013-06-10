package daointerfaces;

import java.util.ArrayList;

import dto.ReceptKompDTO;

public interface IReceptKompDAO {
	ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException;
	ArrayList<ReceptKompDTO> getReceptKompList(int receptId) throws DALException;
	ArrayList<ReceptKompDTO> getReceptKompList() throws DALException;
	void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException;
	void updateReceptKomp(ReceptKompDTO receptkomponent) throws DALException;
}
