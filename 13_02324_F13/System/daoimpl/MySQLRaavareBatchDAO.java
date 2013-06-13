package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IRaavareBatchDAO;
import dto.RaavareBatchDTO;
import dto.RaavareDTO;

public class MySQLRaavareBatchDAO implements IRaavareBatchDAO{
	
	@Override
	public RaavareBatchDTO getRaavareBatch(int raavareBatchId) throws DALException {
		
		ResultSet rs = Connector.doQuery("SELECT r.*,rb.* FROM raavare r, raavarebatch rb WHERE r.raavare_id=rb.raavare_id AND rb_id=" + raavareBatchId +" ORDER BY r.raavare_navn ASC");
		try {
			if (!rs.first()) throw new DALException("Raavarebatchen " + raavareBatchId + " findes ikke"); 
			RaavareDTO raavare = new RaavareDTO(rs.getInt(1),rs.getString(2));
			return new RaavareBatchDTO (rs.getInt(3), raavare, rs.getDouble(5), rs.getTimestamp(6),rs.getString(7));
		} catch (SQLException e) {
			throw new DALException(e); 
		}
	}

	@Override
	public ArrayList<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		ArrayList<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT r.*,rb.* FROM raavare r, raavarebatch rb WHERE r.raavare_id=rb.raavare_id ORDER BY r.raavare_navn ASC");
		try
		{
			while (rs.next()) 
			{
				RaavareDTO raavare = new RaavareDTO(rs.getInt(1),rs.getString(2));
				list.add(new RaavareBatchDTO (rs.getInt(3), raavare, rs.getDouble(5), rs.getTimestamp(6),rs.getString(7)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public ArrayList<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		ArrayList<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT r.*,rb.* FROM raavare r, raavarebatch rb WHERE r.raavare_id=rb.raavare_id AND rb.raavare_id=" + raavareId +" ORDER BY r.raavare_navn ASC");
		try
		{
			while (rs.next()) 
			{
				RaavareDTO raavare = new RaavareDTO(rs.getInt(1),rs.getString(2));
				list.add(new RaavareBatchDTO (rs.getInt(3), raavare, rs.getDouble(5), rs.getTimestamp(6),rs.getString(7)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		Connector.doUpdate("INSERT INTO raavarebatch(rb_id, raavare_id, maengde, dato,leverandoer) VALUES "+
				"(" + raavarebatch.getRbId() + ", '" + raavarebatch.getRaavare().getRaavareId() + "', '" + raavarebatch.getMaengde()+ "', '" + raavarebatch.getDato()+ "', '" +raavarebatch.getLeverandoer() + "')"
				);
	}
	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		Connector.doUpdate(
				"UPDATE raavarebatch SET raavare_id =  '" + raavarebatch.getRaavare().getRaavareId() + 
				"', maengde = '" + raavarebatch.getMaengde() +"', dato = '" + raavarebatch.getDato() +"', leverandoer = '" + raavarebatch.getLeverandoer() +"' WHERE rb_id = " +
				raavarebatch.getRbId()
		);

	}
	
}
