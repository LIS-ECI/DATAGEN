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
import edu.eci.pgr.exceptions.MySqlGeneratorException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.rowGenerators.RowGenerator;

/**
 * @author Felipe
 *
 */
public class MySqlRowGenerator extends RowGenerator {

	private static final String FOREIGN_MESSAGE1 = MessageBundleManager.getString("MySqlRowGenerator.0"); //$NON-NLS-1$
	private static final String FOREIGN_MESSAGE = MessageBundleManager.getString("MySqlRowGenerator.1"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	private static final int LOAD_FACTOR=200;
	private static int batchIndex;
	//private static Hash
	
	public MySqlRowGenerator(){
		batchIndex=0;
	}
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
			String sql="SELECT ("+col.getOriginalColumnName()+") FROM "+ col.getOriginalTable().getName(); //$NON-NLS-1$ //$NON-NLS-2$
			s=(PreparedStatement) con.prepareStatement(sql);
			rs=s.executeQuery();
			//se deja con absolute ya que el rendimiento es constante a~ 0,6 segs por registro con 100000 registros
			rs.absolute((int) random+1);
		    con.commit();
		    this.setForeignValues(col,index,ps,rs);
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySqlGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
		} finally{
			if(s!=null){
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new MySqlGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
				}
			}
		}
	}
	
	@SuppressWarnings("unused") //$NON-NLS-1$
	private void lazyLoad(ForeignKeyVO col, long random, PreparedStatement ps, int index,Connection con) throws GeneratorException{
		PreparedStatement s=null;
		ResultSet rs=null;
		try {
			con.setAutoCommit(false);
			String sql="SELECT ("+col.getOriginalColumnName()+") FROM "+ col.getOriginalTable().getName()+" ORDER BY "+col.getOriginalColumnName()  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			+ "LIMIT "+batchIndex +"  "+(batchIndex+LOAD_FACTOR); //$NON-NLS-1$ //$NON-NLS-2$
			s=(PreparedStatement) con.prepareStatement(sql);
			rs=s.executeQuery();
		    for (int i = batchIndex; rs!=null && i <= batchIndex+LOAD_FACTOR ; i++) {
		    	rs.next();
			}
		    con.commit();
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySqlGeneratorException(FOREIGN_MESSAGE1+col.getOriginalTable(),e);
		} finally{
			if(s!=null){
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new MySqlGeneratorException(FOREIGN_MESSAGE1+col.getOriginalTable(),e);
				}
			}
		}
	}
	@Override
	protected void setNullValue(PreparedStatement ps, TypeVO type, int index) throws SQLException {
		ps.setObject(index, null);
	}

}
