package daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import connector.Connector;
import daointerfaces.DALException;
import daointerfaces.IProduktBatchDAO;
import dto.OperatoerDTO;
import dto.ProduktBatchDTO;
import dto.ReceptDTO;

public class MySQLProduktBatchDAO implements IProduktBatchDAO{
	
	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT pb.*,r.*, o.* FROM recept r, produktbatch pb LEFT JOIN operatoer o ON pb.opr_id = o.opr_id WHERE r.recept_id=pb.recept_id AND pb_id = " + pbId);
		try {
			if (!rs.first()) throw new DALException("Produktbatch " + pbId + " findes ikke"); 
			ReceptDTO recept = new ReceptDTO(rs.getInt(7),rs.getString(8)); 
			OperatoerDTO opr = new OperatoerDTO(rs.getInt(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14));
			return new ProduktBatchDTO (rs.getInt(1), rs.getInt(3),recept , rs.getTimestamp(4), rs.getTimestamp(5),opr);
		} catch (SQLException e) {
			throw new DALException(e); 
		}
	}

	@Override
	public ArrayList<ProduktBatchDTO> getProduktBatchList() throws DALException {
		ArrayList<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT pb.*,r.*, o.* FROM recept r, produktbatch pb LEFT JOIN operatoer o ON pb.opr_id = o.opr_id WHERE r.recept_id=pb.recept_id");
		try
		{
			while (rs.next()) 
			{
				OperatoerDTO opr = new OperatoerDTO(rs.getInt(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14));
				ReceptDTO recept = new ReceptDTO(rs.getInt(7),rs.getString(8)); 
				list.add(new ProduktBatchDTO(rs.getInt(1), rs.getInt(3),recept,rs.getTimestamp(4), rs.getTimestamp(5),opr));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector.doUpdate("INSERT INTO produktbatch(pb_id, recept_id, status) VALUES "+
				"(" + produktbatch.getPbId() + ", '" + produktbatch.getRecept().getReceptId() + "', '" + produktbatch.getStatus() + "')"
				);
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector.doUpdate(
				"UPDATE produktbatch SET recept_id =  '" + produktbatch.getRecept().getReceptId() + 
				"', status = '" + produktbatch.getStatus() + "', dato_start = '" + produktbatch.getDatoStart() + "', dato_slut = '" + produktbatch.getDatoSlut() +"' WHERE pb_id = " +
				produktbatch.getPbId()
		);

	}

}
