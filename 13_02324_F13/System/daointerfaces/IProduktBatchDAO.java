package daointerfaces;

import java.util.ArrayList;

import dto.ProduktBatchDTO;

public interface IProduktBatchDAO {
	ProduktBatchDTO getProduktBatch(int pbId) throws DALException;
	ArrayList<ProduktBatchDTO> getProduktBatchList() throws DALException;
	ArrayList<ProduktBatchDTO> getProduktBatchList(String receptName) throws DALException;
	void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
	void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
}