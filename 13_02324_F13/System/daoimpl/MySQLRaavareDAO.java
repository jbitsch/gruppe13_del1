package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;

import daointerfaces.DALException;
import daointerfaces.IRaavareDAO;
import dto.RaavareDTO;

public class MySQLRaavareDAO implements IRaavareDAO{

	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM raavare WHERE raavare_id = " + raavareId+" ORDER BY raavare_navn");
		try {
			if (!rs.first()) throw new DALException("Raavaren " + raavareId + " findes ikke"); 
			return new RaavareDTO (rs.getInt(1), rs.getString(2), rs.getString(3));
		} catch (SQLException e) {
			throw new DALException(e); 
		}
	}

	@Override
	public ArrayList<RaavareDTO> getRaavareList() throws DALException {
		ArrayList<RaavareDTO> list = new ArrayList<RaavareDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM raavare ORDER BY raavare_navn");
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		Connector.doUpdate("INSERT INTO raavare(raavare_id,raavare_navn,leverandoer) VALUES "+
				"(" + raavare.getRaavareId() + ", '" + raavare.getRaavareNavn() + "', '" + raavare.getLeverandoer() + "')"
				);
	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		Connector.doUpdate(
				"UPDATE raavare SET raavare_navn =  '" + raavare.getRaavareNavn() + 
				"', leverandoer = '" + raavare.getLeverandoer() +"' WHERE raavare_id = " +
				raavare.getRaavareId()
		);

	}



}
