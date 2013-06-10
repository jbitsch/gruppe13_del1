package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.Connector;

import daointerfaces.DALException;
import daointerfaces.IOperatoerDAO;
import dto.OperatoerDTO;

public class MySQLOperatoerDAO implements IOperatoerDAO {
	@Override
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId);
	    try {
	    	if (!rs.first()) throw new DALException("Operat�ren " + oprId + " findes ikke"); 
	    	return new OperatoerDTO (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	@Override
	public void createOperatoer(OperatoerDTO opr) throws DALException {		
			Connector.doUpdate(
				"INSERT INTO operatoer(opr_id, opr_navn, ini, cpr, password, rolle) VALUES " +
				"(" + opr.getOprId() + ", '" + opr.getOprNavn() + "', '" + opr.getIni() + "', '" + 
				opr.getCpr() + "', '" + opr.getPassword() + "', '" + opr.getRolle() + "')"
			);
	}
	@Override
	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		Connector.doUpdate(
				"UPDATE operatoer SET  opr_navn = '" + opr.getOprNavn() + "', ini =  '" + opr.getIni() + 
				"', cpr = '" + opr.getCpr() + "', password = '" + opr.getPassword() + "', rolle = '" + opr.getRolle() + "' WHERE opr_id = " +
				opr.getOprId()
		);
	}
	@Override
	public ArrayList<OperatoerDTO> getOperatoerList() throws DALException {
		ArrayList<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM operatoer");
		try
		{
			while (rs.next()) 
			{
				list.add(new OperatoerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
		
}
	
