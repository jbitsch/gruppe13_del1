package daointerfaces;

import java.util.ArrayList;

import dto.RaavareDTO;

public interface IRaavareDAO {
	RaavareDTO getRaavare(int raavareId) throws DALException;
	ArrayList<RaavareDTO> getRaavareList() throws DALException;
	void createRaavare(RaavareDTO raavare) throws DALException;
	void updateRaavare(RaavareDTO raavare) throws DALException;
}
