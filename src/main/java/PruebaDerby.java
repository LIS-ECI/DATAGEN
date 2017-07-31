import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.DAOException;
import edu.eci.pgr.exceptions.DAOSelectException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.persistence.DAO;
import edu.eci.pgr.properties.EmbededDataBaseConnectionLoader;


public class PruebaDerby {
	public static void main(String[] args) {
		try {
			DAO dao = new DAO(EmbededDataBaseConnectionLoader.getInstance().getConnectionInformation());
			try {
				System.out.println(dao.selectDataWithWhereClause(new String[]{"VALUE"}, new String[]{TypesNames.STRING}, "LAST_NAMES", " ROWID = 1000").get(0)[0]);
			} catch (DAOSelectException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (InstantException e) {
			e.printStackTrace();
		}
	}
}
