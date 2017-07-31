/**
 * 
 */
package edu.eci.pgr.rowGenerators.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.SqlServerGeneratorException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.rowGenerators.RowGenerator;

public class SqlServerRowGenerator extends RowGenerator {
	private static final String FOREIGN_MESSAGE = MessageBundleManager.getString("SqlServerRowGenerator.0"); //$NON-NLS-1$
	private static final String ROLLBACK_MESSAGE = MessageBundleManager.getString("SqlServerRowGenerator.1"); //$NON-NLS-1$
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
			ResultSet rs2=con.getMetaData().getColumns(null, null, col.getOriginalTable().getName(), null);
			String orderby =""; //$NON-NLS-1$
			if(rs2 != null){
				if(rs2.next()){
					orderby = rs2.getString("COLUMN_NAME"); //$NON-NLS-1$
				}
			}
			String sql="SELECT "+col.getOriginalColumnName()+" FROM " + //$NON-NLS-1$ //$NON-NLS-2$
					"( SELECT *, ROW_NUMBER() OVER (ORDER BY "+orderby+") as row FROM "+col.getOriginalTable().getName()+ //$NON-NLS-1$ //$NON-NLS-2$
					") as alias WHERE row = "+(random+1); //$NON-NLS-1$
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
				throw new SqlServerGeneratorException(ROLLBACK_MESSAGE+col.getOriginalTable(),e);
			}
			e.printStackTrace();
			throw new SqlServerGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
		} finally{
			if(s!=null){
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new SqlServerGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new SqlServerGeneratorException(FOREIGN_MESSAGE+col.getOriginalTable(),e);
				}
			}
		}
	}

	@Override
	protected void setNullValue(PreparedStatement ps, TypeVO type, int index)throws SQLException {
			if(type.isInteger())
				ps.setNull(index,Types.INTEGER);
			else if(type.isDouble())
				ps.setNull(index,Types.DOUBLE);
			else if(type.isDate())
				ps.setNull(index,Types.DATE);
			else if(type.isString())
				ps.setNull(index,Types.VARCHAR);
			else if(type.isFile())
				ps.setNull(index,Types.BINARY);
	}

}
