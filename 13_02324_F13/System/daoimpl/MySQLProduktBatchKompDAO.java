package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IProduktBatchKompDAO;
import dto.ProduktBatchKompDTO;
import dto.RaavareBatchDTO;
import dto.RaavareDTO;

public class MySQLProduktBatchKompDAO implements IProduktBatchKompDAO{
	
	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT pbk.*, rb.*, r.* FROM produktbatchkomponent pbk, raavarebatch rb, raavare r WHERE pbk.rb_id = rb.rb_id AND rb.raavare_id = r.raavare_id AND pbk.pb_id = " + pbId + " AND pbk.rb_id = " + rbId);
	    try {
	    	if (!rs.first()) throw new DALException("Produktbatchkomponent med produktbatch " + pbId + " og raavarebatch " + rbId + " findes ikke"); 
	    	RaavareDTO raavare = new RaavareDTO(rs.getInt(9),rs.getString(10),rs.getString(11));
	    	RaavareBatchDTO raavareBatch = new RaavareBatchDTO(rs.getInt(5),raavare,rs.getDouble(7),rs.getTimestamp(8));
	    	return new ProduktBatchKompDTO (rs.getInt(1), raavareBatch, rs.getDouble(3), rs.getDouble(4));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO pbk) throws DALException {		
			Connector.doUpdate(
				"INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto) VALUES " +
				"(" + pbk.getPbId() + ", '" + pbk.getRb().getRbId() + "', '" + pbk.getTara() + "', '" + 
				pbk.getNetto() + "')"
			);
	}
	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO pbk) throws DALException {
		Connector.doUpdate(
				"UPDATE produktbatchkomponent SET tara = '" + pbk.getTara() + "', netto =  '" + pbk.getNetto() + 
				"' WHERE pb_id = " +
				pbk.getPbId() + " AND rb_id = " + pbk.getRb().getRbId()
		);
	}
	@Override
	public ArrayList<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		ArrayList<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT pbk.*, rb.*, r.* FROM produktbatchkomponent pbk, raavarebatch rb, raavare r WHERE pbk.rb_id = rb.rb_id AND rb.raavare_id = r.raavare_id");
		try
		{
			while (rs.next()) 
			{
		    	RaavareDTO raavare = new RaavareDTO(rs.getInt(9),rs.getString(10),rs.getString(11));
		    	RaavareBatchDTO raavareBatch = new RaavareBatchDTO(rs.getInt(5),raavare,rs.getDouble(7),rs.getTimestamp(8));
				list.add(new ProduktBatchKompDTO(rs.getInt(1), raavareBatch, rs.getDouble(3), rs.getDouble(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public ArrayList<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		ArrayList<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT pbk.*, rb.*, r.* FROM produktbatchkomponent pbk, raavarebatch rb, raavare r WHERE pbk.rb_id = rb.rb_id AND rb.raavare_id = r.raavare_id AND pbk.pb_id =  " + pbId);
		try
		{
			while (rs.next()) 
			{
				RaavareDTO raavare = new RaavareDTO(rs.getInt(9),rs.getString(10),rs.getString(11));
		    	RaavareBatchDTO raavareBatch = new RaavareBatchDTO(rs.getInt(5),raavare,rs.getDouble(7),rs.getTimestamp(8));
				list.add(new ProduktBatchKompDTO(rs.getInt(1), raavareBatch, rs.getDouble(3), rs.getDouble(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
}
