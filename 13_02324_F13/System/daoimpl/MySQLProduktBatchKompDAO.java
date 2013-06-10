package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IProduktBatchKompDAO;
import dto.ProduktBatchKompDTO;

public class MySQLProduktBatchKompDAO implements IProduktBatchKompDAO{
	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = " + pbId + " AND rb_id = " + rbId);
	    try {
	    	if (!rs.first()) throw new DALException("Produktbatchkomponent med produktbatch " + pbId + " og raavarebatch " + rbId + " findes ikke"); 
	    	return new ProduktBatchKompDTO (rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO pbk) throws DALException {		
			Connector.doUpdate(
				"INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) VALUES " +
				"(" + pbk.getPbId() + ", '" + pbk.getPbId() + "', '" + pbk.getTara() + "', '" + 
				pbk.getNetto() + "', '" + pbk.getOprId() + "')"
			);
	}
	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO pbk) throws DALException {
		Connector.doUpdate(
				"UPDATE produktbatchkomponent SET tara = '" + pbk.getTara() + "', netto =  '" + pbk.getNetto() + 
				"', opr_id = '" + pbk.getOprId() + "' WHERE pb_id = " +
				pbk.getPbId() + " AND rb_id = " + pbk.getRbId()
		);
	}
	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomponent");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id " + pbId);
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
}
