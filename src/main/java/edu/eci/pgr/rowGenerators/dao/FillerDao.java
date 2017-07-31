package edu.eci.pgr.rowGenerators.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class FillerDao {
	private static final String ROWS_MESSAGE = MessageBundleManager.getString("FillerDao.0"); //$NON-NLS-1$
	private static FillerDao instance;
	
	private FillerDao(){
		
	}
	
	public static FillerDao getInstance(){
		if(instance == null)
			instance = new FillerDao();
		return instance;
	}
	
	/**
	 * @param name; the column name
	 * @return the column data length in the database
	 * @throws GeneratorException 
	 */
	public long rowsCount(String name,Connection con) throws GeneratorException {
		Statement s=null;
		ResultSet rs=null;
		long ret=0;
		try {
			con.setAutoCommit(false);
			s=(Statement) con.createStatement();
			String sql= "SELECT COUNT(*)  FROM "+ name; //$NON-NLS-1$
			s.execute(sql);
			rs=s.getResultSet();
			if(rs!=null){
				rs.next();
				ret=rs.getInt(1);
			}
			
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GeneratorException(ROWS_MESSAGE+name+" ",e); //$NON-NLS-1$
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
					throw new GeneratorException(ROWS_MESSAGE+name+" ",e); //$NON-NLS-1$
				}
			}

		}
		return ret;
	}

}
