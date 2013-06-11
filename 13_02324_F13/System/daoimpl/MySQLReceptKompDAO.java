package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IReceptKompDAO;
import dto.RaavareDTO;
import dto.ReceptKompDTO;

public class MySQLReceptKompDAO implements IReceptKompDAO {

	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT rk.*,r.* FROM raavare r, receptkomponent rk WHERE r.raavare_id=rk.raavare_id AND rk.recept_id = " + receptId + " AND rk.raavare_id=" + raavareId);
	    try {
	    	if (!rs.first()) throw new DALException("Receptkomponenten med recept " + receptId + " og raavare " + raavareId + " findes ikke"); 
	    	RaavareDTO raavare = new RaavareDTO(rs.getInt(5),rs.getString(6),rs.getString(7));
	    	return new ReceptKompDTO (rs.getInt(1),raavare, rs.getDouble(3), rs.getDouble(4));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	@Override
	public void createReceptKomp(ReceptKompDTO receptkomp) throws DALException {		
			Connector.doUpdate(
				"INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES " +
				"(" + receptkomp.getReceptId() + ", '" + receptkomp.getRaavare().getRaavareId() + "', '" + receptkomp.getNomNetto() + "', '" + 
				receptkomp + "')"
			);
	}
	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomp) throws DALException {
		Connector.doUpdate(
				"UPDATE receptkomponent SET nom_netto = '" + receptkomp.getNomNetto() + "', tolerance =  '" + receptkomp.getTolerance() + 
				"' WHERE recept_id = " + receptkomp.getReceptId() + " AND raavare_id = " + receptkomp.getRaavare().getRaavareId()
				);  
	}
	@Override
	public ArrayList<ReceptKompDTO> getReceptKompList() throws DALException {
		ArrayList<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT rk.*,r.* FROM raavare r, receptkomponent rk WHERE r.raavare_id=rk.raavare_id");
		try
		{
			while (rs.next()) 
			{
				RaavareDTO raavare = new RaavareDTO(rs.getInt(5),rs.getString(6),rs.getString(7));
				list.add(new ReceptKompDTO(rs.getInt(1), raavare, rs.getDouble(3), rs.getDouble(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public ArrayList<ReceptKompDTO> getReceptKompList(int receptId) throws DALException {
		ArrayList<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT rk.*,r.* FROM raavare r, receptkomponent rk WHERE r.raavare_id=rk.raavare_id AND rk.recept_id = " + receptId);
		try
		{
			while (rs.next()) 
			{
				RaavareDTO raavare = new RaavareDTO(rs.getInt(5),rs.getString(6),rs.getString(7));
				list.add(new ReceptKompDTO(rs.getInt(1), raavare, rs.getDouble(3), rs.getDouble(4)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
}
