package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IProduktBatchDAO;
import dto.ProduktBatchDTO;

public class MySQLProduktBatchDAO implements IProduktBatchDAO{
	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = " + pbId);
		try {
			if (!rs.first()) throw new DALException("Produktbatch " + pbId + " findes ikke"); 
			return new ProduktBatchDTO (rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5));
		} catch (SQLException e) {
			throw new DALException(e); 
		}
	}

	@Override
	public ArrayList<ProduktBatchDTO> getProduktBatchList() throws DALException {
		ArrayList<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatch");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchDTO(rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getTimestamp(4), rs.getTimestamp(5)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector.doUpdate("INSERT INTO produktbatch(pb_id, recept_id, status, dato_start, dato_slut) VALUES "+
				"(" + produktbatch.getPbId() + ", '" + produktbatch.getReceptId() + "', '" + produktbatch.getStatus() + "', '" + produktbatch.getDatoStart() + "', '" + produktbatch.getDatoSlut() + "')"
				);
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector.doUpdate(
				"UPDATE produktbatch SET recept_id =  '" + produktbatch.getReceptId() + 
				"', status = '" + produktbatch.getStatus() + "', dato_start = '" + produktbatch.getDatoStart() + "', dato_slut = '" + produktbatch.getDatoSlut() +"' WHERE pb_id = " +
				produktbatch.getPbId()
		);

	}

}
