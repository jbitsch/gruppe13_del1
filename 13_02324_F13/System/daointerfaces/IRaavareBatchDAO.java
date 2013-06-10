package daointerfaces;

import java.util.ArrayList;

import dto.RaavareBatchDTO;

public interface IRaavareBatchDAO {
	RaavareBatchDTO getRaavareBatch(int rbId) throws DALException;
	ArrayList<RaavareBatchDTO> getRaavareBatchList() throws DALException;
	ArrayList<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException;
	void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException;
	void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException;
}

