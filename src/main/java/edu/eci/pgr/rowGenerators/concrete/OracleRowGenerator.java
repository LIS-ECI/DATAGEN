/**
 * 
 */
package edu.eci.pgr.rowGenerators.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.OracleGeneratorException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.rowGenerators.RowGenerator;

/**
 * @author Felipe
 *
 */
public class OracleRowGenerator extends RowGenerator {
	private static final String FOREIGN_MESSAGE = MessageBundleManager.getString("OracleRowGenerator.0"); //$NON-NLS-1$
	private static final String ROLLBACK_MESSAGE = MessageBundleManager.getString("OracleRowGenerator.1"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;

	/**
	 * get the next value to foreign column
	 * @param col
	 * @param random 
	 * @param ps 
	 * @param isPrimary
	 * @return
	 * @throws GeneratorException 
	 */
	protected void nextForeignValue(ForeignKeyVO col, long random, PreparedStatement ps, int index,Connection con) throws GeneratorException{
		PreparedStatement s=null;
		ResultSet rs=null;
		try {
			con.setAutoCommit(false);
			String sql="SELECT ("+col.getOriginalColumnName()+") FROM "+ col.getOriginalTable().getName()+ //$NON-NLS-1$ //$NON-NLS-2$
			" WHERE ROWNUM = 1 AND ROWID NOT IN (SELECT ROWID FROM "+col.getOriginalTable().getName()+" WHERE ROWNUM < "+(random+1)+")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s=(PreparedStatement) con.prepareStatement(sql);
			rs=s.executeQuery();
			rs.next();
			con.commit();
		    this.setForeignValues(col,index,ps,rs);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new OracleGeneratorException(ROLLBACK_MESSAGE+col.getOriginalTable(),e);
			}
			e.printStackTrace();
			throw new OracleGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
		} finally{
			if(s!=null){
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new OracleGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new OracleGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
				}
			}
		}
	}

	@Override
	protected void setNullValue(PreparedStatement ps, TypeVO type, int index) throws SQLException {
		ps.setObject(index, null);
	}

}
