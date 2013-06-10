package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IReceptKompDAO;
import dto.ReceptKompDTO;

public class MySQLReceptKompDAO implements IReceptKompDAO {
	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptId + " AND raavare_id" + raavareId);
	    try {
	    	if (!rs.first()) throw new DALException("Receptkomponenten med recept " + receptId + " og raavare " + raavareId + " findes ikke"); 
	    	return new ReceptKompDTO (rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	@Override
	public void createReceptKomp(ReceptKompDTO receptkomp) throws DALException {		
			Connector.doUpdate(
				"INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES " +
				"(" + receptkomp.getReceptId() + ", '" + receptkomp.getRaavareId() + "', '" + receptkomp.getNomNetto() + "', '" + 
				receptkomp + "')"
			);
	}
	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomp) throws DALException {
		Connector.doUpdate(
				"UPDATE receptkomponent SET nom_netto = '" + receptkomp.getNomNetto() + "', tolerance =  '" + receptkomp.getTolerance() + 
				"' WHERE recept_id = " + receptkomp.getReceptId() + " AND raavare_id = " + receptkomp.getRaavareId()
				);  
	}
	@Override
	public List<ReceptKompDTO> getReceptKompList() throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent");
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent WHERE " + receptId);
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
}
