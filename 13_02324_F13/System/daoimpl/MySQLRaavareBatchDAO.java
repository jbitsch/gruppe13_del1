package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IRaavareBatchDAO;
import dto.RaavareBatchDTO;

public class MySQLRaavareBatchDAO implements IRaavareBatchDAO{

	@Override
	public RaavareBatchDTO getRaavareBatch(int raavareBatchId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM raavarebatch WHERE rb_id = " + raavareBatchId);
		try {
			if (!rs.first()) throw new DALException("Raavarebatchen " + raavareBatchId + " findes ikke"); 
			return new RaavareBatchDTO (rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getTimestamp(4));
		} catch (SQLException e) {
			throw new DALException(e); 
		}
	}

	@Override
	public ArrayList<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		ArrayList<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM raavarebatch");
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getTimestamp(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	


	@Override
	public ArrayList<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		ArrayList<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM raavarebatch WHERE raavare_id = " + raavareId);
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getTimestamp(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		Connector.doUpdate("INSERT INTO raavarebatch(rb_id, raavare_id, maengde, dato) VALUES "+
				"(" + raavarebatch.getRbId() + ", '" + raavarebatch.getRaavareId() + "', '" + raavarebatch.getMaengde()+ "', '" + raavarebatch.getDato() + "')"
				);
	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		Connector.doUpdate(
				"UPDATE raavareabatch SET raavare_id =  '" + raavarebatch.getRaavareId() + 
				"', maengde = '" + raavarebatch.getMaengde() +"', dato = '" + raavarebatch.getDato() +"' WHERE rb_id = " +
				raavarebatch.getRbId()
		);

	}
	
}
