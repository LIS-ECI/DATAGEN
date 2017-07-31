/**
 * 
 */
package edu.eci.pgr.rowGenerators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.business.controller.BusinessFacade;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.persistence.ConnectionFactory;
import edu.eci.pgr.rowGenerators.dao.FillerDao;
import edu.eci.pgr.sorting.Task;
import edu.eci.pgr.view.ViewConstants;

/**
 * @author Felipe
 *
 */

public abstract class RowGenerator implements Serializable{
	private static final String MINUTES = MessageBundleManager.getString("RowGenerator.0"); //$NON-NLS-1$
	private static final String FILE_INSERTION_ERROR = MessageBundleManager.getString("RowGenerator.1"); //$NON-NLS-1$
	private static final String UNIQUE_AUTOREFERENCED = MessageBundleManager.getString("RowGenerator.2"); //$NON-NLS-1$
	private static final String FILLING_DATA_ERROR = MessageBundleManager.getString("RowGenerator.3"); //$NON-NLS-1$
	private static final String ERROR_FILLING = MessageBundleManager.getString("RowGenerator.4"); //$NON-NLS-1$
	private static final String AUTOREFERENCED_ERROR_SQL = MessageBundleManager.getString("RowGenerator.5"); //$NON-NLS-1$
	private static final String AUTOREFERENCE_ERROR = MessageBundleManager.getString("RowGenerator.6"); //$NON-NLS-1$
	private static final String FILLING_ERROR = MessageBundleManager.getString("RowGenerator.7"); //$NON-NLS-1$
	private static final String FILLING_MESSAGE = MessageBundleManager.getString("RowGenerator.8"); //$NON-NLS-1$
	private static final String INSTANTIATION_MESSAGE = MessageBundleManager.getString("RowGenerator.10"); //$NON-NLS-1$
	private static final String INSERTING_FINISH = MessageBundleManager.getString("RowGenerator.11"); //$NON-NLS-1$
	private static final String AUTOREFERENCED_TABLE = MessageBundleManager.getString("RowGenerator.12"); //$NON-NLS-1$
	private static final String INSERTING_IN = MessageBundleManager.getString("RowGenerator.13"); //$NON-NLS-1$
	private static final String LOG_ERROR = MessageBundleManager.getString("RowGenerator.14"); //$NON-NLS-1$
	private static final String CONNECTION_ERROR = MessageBundleManager.getString("RowGenerator.15"); //$NON-NLS-1$
	private static final String INSTANTIATION_ERROR = MessageBundleManager.getString("RowGenerator.16"); //$NON-NLS-1$
	private static final String TABLES_FILLED_IN = MessageBundleManager.getString("RowGenerator.17"); //$NON-NLS-1$
	private static final String FILLED_START = MessageBundleManager.getString("RowGenerator.18"); //$NON-NLS-1$
	private static final String HOUR = MessageBundleManager.getString("RowGenerator.19"); //$NON-NLS-1$

	private static final long serialVersionUID = 1L;
	public static long TOTAL_COUNT;
	protected DataGenerator dataGen;
	protected Map <String,Long> randoms;	
	public void Fill(List<Task> tasks) throws GeneratorException, UnsupportedDataException, WriterException {
		int totalinserts=0;
		try {
			for(Task task: tasks){
				totalinserts+=task.getTable().getInsertTimes();
			}
			BusinessFacade.getInstance().initialiceProgressComponent(totalinserts);
			BusinessFacade.getInstance().addWarningMessage(FILLED_START);
			Long initialtime=Calendar.getInstance().getTimeInMillis();
			for(Task task: tasks){
				if(!task.isUseStoredData())
					fillTask(task);
			}
			Long finaltime=Calendar.getInstance().getTimeInMillis();
			Double totaltime=((Long)(finaltime-initialtime)).doubleValue();
			NumberFormat nf= new DecimalFormat("##.##"); //$NON-NLS-1$
			BusinessFacade.getInstance().addWarningMessage(tasks.size()+TABLES_FILLED_IN
					+nf.format(totaltime/(1000*60))+MINUTES);
			BusinessFacade.getInstance().addToProcessComponent(1);
		} catch (InstantException e) {
			BusinessFacade.getInstance().addWarningMessage(e.getMessage());
			e.printStackTrace();
			throw new GeneratorException(INSTANTIATION_ERROR,e);
		} finally{
			try{
				BusinessFacade.getInstance().exportFileProcess();
				ConnectionFactory.getInstance().closeConnection();
			} catch (ConnectionException e) {
				e.printStackTrace();
				throw new GeneratorException(CONNECTION_ERROR+e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new WriterException(LOG_ERROR);
			}
		}
	}
	/**
	 * fill a task
	 * @param task
	 * @throws InstantException
	 * @throws GeneratorException 
	 * @throws UnsupportedDataException 
	 * @throws WriterException 
	 */
	private void fillTask(Task task) throws InstantException, GeneratorException, UnsupportedDataException, WriterException{
		Connection con=null;
		PreparedStatement ps= null;
		TableVO table= null;
		String questions=""; //$NON-NLS-1$
		String columnames=""; //$NON-NLS-1$
		randoms= new Hashtable<String, Long>();
		task.generateMaxValuesForCombination();
		boolean isAutoreferenced= task.isAutoReferenced();
		try {
			con=ConnectionFactory.getInstance().getConnection();
			table=task.getTable();
			TOTAL_COUNT= table.getInsertTimes();
			for (ColumnVO col: table.getSingleColumns().values()) {
				if (!col.isAutoIncrement()) {
					questions += ",?"; //$NON-NLS-1$
					columnames += "," + col.getName(); //$NON-NLS-1$
				}
			}
			for (ColumnVO col: table.getPrimaryColumns().values()){
				if (!col.isAutoIncrement()) {
					questions += ",?"; //$NON-NLS-1$
					columnames += "," + col.getName(); //$NON-NLS-1$
				}
			}

			for (ForeignKeyVO col: table.getForeignColumns().values()){
				if (!col.isAutoIncrement()) {
					questions += ",?"; //$NON-NLS-1$
					columnames += "," + col.getName(); //$NON-NLS-1$
				}
			}
			List<ForeignKeyVO> pkfkcolumns = new ArrayList<ForeignKeyVO>(table.getPkFkColumns().values());
			Collections.sort(pkfkcolumns);
			for (ForeignKeyVO col: pkfkcolumns){
				if (!col.isAutoIncrement()) {
					questions += ",?"; //$NON-NLS-1$
					columnames += "," + col.getName(); //$NON-NLS-1$
				}
			}
			questions=questions.substring(1);
			columnames=columnames.substring(1);
			ps=(PreparedStatement) con.prepareStatement("INSERT INTO "+  //$NON-NLS-1$
					table.getName()+" ("+columnames+") VALUES ("+questions+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			BusinessFacade.getInstance().addWarningMessage(INSERTING_IN+table.getName());
			con.setAutoCommit(false);
			// if the table is auto referenced then:
			Timer a= new Timer();
			a.schedule(new programmedHourMessage(),Calendar.getInstance().getTime() ,ViewConstants.HOURINTERVAL);
			if(isAutoreferenced){
				BusinessFacade.getInstance().addWarningMessage(table.getName()+AUTOREFERENCED_TABLE);
				//1. fill data with null fields in auto referenced columns
				for (int i = 0; i < TOTAL_COUNT; i++) {
					// the row in fist iteration when i=0 is null for referenced column
					this.fillAutoreferencedRow(table,ps,con,task.getNextCombinationValues(),i==0);
					ps.executeUpdate();
					ps.clearBatch();
					//add 1 insert to process
					BusinessFacade.getInstance().addToProcessComponent(1);
				}
			}
			else{
				for (int i = 0; i < TOTAL_COUNT; i++) {
					this.fillRow(table,ps,con,task.getNextCombinationValues());
					ps.executeUpdate();
					ps.clearBatch();
					con.commit();
					//add 1 insert to process
					BusinessFacade.getInstance().addToProcessComponent(1);
				}
				con.commit();
			}
			BusinessFacade.getInstance().addWarningMessage(INSERTING_FINISH+table.getName());
		} catch (InstantException e) {
			BusinessFacade.getInstance().addWarningMessage(e.getMessage());
			e.printStackTrace();
			throw new GeneratorException(INSTANTIATION_MESSAGE+task.getTable().getName()+", " ,e); //$NON-NLS-1$
		} catch (SQLException e) {
			BusinessFacade.getInstance().addWarningMessage(e.getMessage());
			e.printStackTrace();
			throw new GeneratorException(FILLING_MESSAGE+task.getTable().getName()+", ",e); //$NON-NLS-1$
		}catch (Exception e) {
			BusinessFacade.getInstance().addWarningMessage(e.getMessage());
			e.printStackTrace();
			throw new GeneratorException(FILLING_ERROR+task.getTable().getName()+", \n"+e.getMessage(),e); //$NON-NLS-1$
		}finally{
			if(ps!= null){
				try {
					ps.close();
				} catch (SQLException e) {
					BusinessFacade.getInstance().addWarningMessage(e.getMessage());
					e.printStackTrace();
					throw new GeneratorException(FILLING_MESSAGE+task.getTable().getName()+", ",e); //$NON-NLS-1$
				}
			}
			try {
				BusinessFacade.getInstance().exportFileProcess();
				ConnectionFactory.getInstance().closeConnection();
			} catch (ConnectionException e) {
				BusinessFacade.getInstance().addWarningMessage(e.getMessage());
				e.printStackTrace();
				throw new GeneratorException(CONNECTION_ERROR,e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new WriterException(LOG_ERROR);
			}
		}
	}

	private void fillAutoreferencedRow(TableVO table, PreparedStatement ps,
			Connection con, long[]permutationValues,boolean forcednull) throws SQLException, InstantException, GeneratorException, UnsupportedDataException, UnsupportedAttributeValueException, UnsupportedAttribute {
		//data to single columns
		int index=1;
		//hash to save the values of primary columns to insert in the autoreferences 
		Hashtable<String, Object> primaryvalues= new Hashtable<String, Object>();
		for(ColumnVO col: table.getSingleColumns().values()){
			//si la columna es autoincrementada no se llenan datos
			if(!col.isAutoIncrement()){
				double nullpercent=100;
				//calculate percent of null fields
				if(col.isNullable()) nullpercent=(Math.random()*100)+1;
				dataGen = col.getGenerator();
				this.fillData(dataGen,ps, col.getType(),index,false,nullpercent<col.getNullablePercentage());
				index++;
			}

		}
		//data to primary columns
		for(ColumnVO col: table.getPrimaryColumns().values()){
			dataGen=col.getGenerator(); 
			this.fillAutoreferencePrimaryData(dataGen,ps, col.getType(),index, primaryvalues, col.getName());
			index++;

		}
		// row change
		randoms=new Hashtable<String, Long>();
		//data to foreign columns
		for(ForeignKeyVO col: table.getForeignColumns().values()){
			long random= (long) (Math.random()*FillerDao.getInstance().rowsCount(col.getOriginalTable().getName(),con));
			String originalname=col.getOriginalTable().getName();
			//if the original name is equals, the random value has been equals
			if (!randoms.containsKey(originalname)){
				randoms.put(col.getOriginalTable().getName(),random);
			}
			boolean enter=true;
			// verify if the reference is in same table
			if(originalname.equals(table.getName())) {
				//if the reference is to the same caolumn this is bad.. cant be filled
				if(col.getOriginalColumnName().equals(col.getName()))
					throw new GeneratorException(col.getName()+AUTOREFERENCE_ERROR);
				double nullpercent=100;
				//calculate percent of null fields
				if(col.isNullable()) {
					nullpercent=(Math.random()*100)+1;
					if(nullpercent<col.getNullablePercentage() || forcednull){
						ps.setObject(index, null);
						enter=false;
					}
				}
				else if(forcednull){
					this.sameAutoreferenceNoNullValue(col,ps,index,primaryvalues.get(col.getOriginalColumnName()));
					enter=false;
				}
			}
			if (enter)// get the next foreign value
				this.nextForeignValue(col, randoms.get(originalname),ps,index,con);
			index++;
		}
		//data to pkfk columns
		this.pkFkValues(table,ps,index,con,permutationValues);
	}


	/** 
	 * allocates the same value in the index and reference 
	 * @param col; the column
	 * @param ps; the prepare statement
	 * @param index; index in the prepare statement
	 * @param value; the value of prepare statement in index position
	 * @throws GeneratorException 
	 */
	@IfForDataTypes(supportedTypes=2, id=0)
	private void sameAutoreferenceNoNullValue(ForeignKeyVO col,PreparedStatement ps, int index, Object value ) throws GeneratorException {
		try {
			if (col.getType().isInteger()){
				ps.setInt(index, (Integer) value);
			}
			else if(col.getType().isString()){
				ps.setString(index, (String) value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GeneratorException(AUTOREFERENCED_ERROR_SQL,e);
		}
	}


	private void fillRow(TableVO table,PreparedStatement ps,Connection conn,long[]permutationValues) throws InstantException, GeneratorException, UnsupportedDataException, UnsupportedAttributeValueException, UnsupportedAttribute{
		//data to single columns
		int index=1;
		for(ColumnVO col: table.getSingleColumns().values()){
			//si la columna es autoincrementada no se llenan datos
			if(!col.isAutoIncrement()){
				double nullpercent=100;
				//calculate percent of null fields
				if(col.isNullable()) nullpercent=(Math.random()*100)+1;
				dataGen=col.getGenerator();
				this.fillData(dataGen,ps, col.getType(),index,false,nullpercent<col.getNullablePercentage());
				index++;
			}
		}
		//data to primary columns
		for(ColumnVO col: table.getPrimaryColumns().values()){
			//si la columna es autoincrementada no se llenan datos
			if(!col.isAutoIncrement()){
				dataGen=col.getGenerator(); 
				this.fillData(dataGen,ps, col.getType(),index,true,false);
				index++;
			}
		}
		// row change
		randoms=new Hashtable<String, Long>();
		//data to foreign columns
		for(ForeignKeyVO col: table.getForeignColumns().values()){
			long random= (long) (Math.random()*FillerDao.getInstance().rowsCount(col.getOriginalTable().getName(),conn));
			String originalname=col.getOriginalTable().getName();
			//if the original name is equals, the random value has been equals
			if (!randoms.containsKey(originalname) ){
				randoms.put(col.getOriginalTable().getName(),random);
			}
			// get the next foreign value
			this.nextForeignValue(col, randoms.get(originalname),ps,index,conn);
			index++;
		}
		//data to pkfk columns
		this.pkFkValues(table,ps,index,conn,permutationValues);
	}


	/**
	 * prepare the statement with corresponding values
	 * @param dataGen
	 * @param ps
	 * @param type
	 * @param index
	 * @param isUnique
	 * @param isNull
	 * @throws GeneratorException 
	 * @throws UnsupportedDataException 
	 */
	@IfForDataTypes(supportedTypes=5, id=1)
	protected void fillData(DataGenerator dataGen, PreparedStatement ps, TypeVO type, int index, boolean isUnique, boolean isNull) throws GeneratorException, UnsupportedDataException {
		try {
			if (isNull){
				setNullValue(ps, type, index);
			}
			else{
				if(type.isInteger()){
					int value;
					if(isUnique)
						value =dataGen.generateNextIntUniqueValue();
					else 
						value = dataGen.generateNextIntValue();
					ps.setInt(index, value);
				}else if(type.isString()){
					String value;
					if(isUnique)
						value = dataGen.generateNextStringUniqueValue();
					else 
						value =dataGen.generateNextStringValue();
					ps.setString(index, value);
				}else if(type.isDate()){
					Date value;
					if(isUnique)
						value = dataGen.generateNextDateUniqueValue();
					else 
						value =dataGen.generateNextDateValue();
					ps.setDate(index, value);
				}else if(type.isDouble()){
					Double value;
					if(isUnique)
						value = dataGen.generateNextDoubleUniqueValue();
					else 
						value =dataGen.generateNextDoubleValue();
					ps.setDouble(index, value);
				}else if(type.isFile()){
					String path;
					FileInputStream value;
					if(isUnique)
						path = dataGen.generateNextStringUniqueValue();
					else 
						path =dataGen.generateNextStringValue();
					value = new FileInputStream(path);
					ps.setBinaryStream(index, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessFacade.getInstance().addWarningMessage(ERROR_FILLING+e.getMessage());
			throw new GeneratorException(FILLING_DATA_ERROR+e.getMessage(),e);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			BusinessFacade.getInstance().addWarningMessage(ERROR_FILLING+e.getMessage());
			throw new GeneratorException(FILLING_DATA_ERROR+e.getMessage(),e);
		}
	}
	protected abstract void setNullValue(PreparedStatement ps, TypeVO type, int index) throws SQLException ;	

	/**
	 * prepare the statement with corresponding values for primary key columns in
	 * a auto referenced table
	 * @param dataGen
	 * @param ps; the prepare statement
	 * @param type; the column type
	 * @param index; the prepare statement index
	 * @param values; saves the values for first insertion
	 * @param columname; the name of the column
	 * @throws GeneratorException 
	 * @throws UnsupportedDataException 
	 */
	@IfForDataTypes(supportedTypes=5, id=2)
	protected void fillAutoreferencePrimaryData(DataGenerator dataGen, PreparedStatement ps, TypeVO type, int index, 
			Hashtable<String, Object> values , String columname) throws GeneratorException, UnsupportedDataException {
		try {
			if(type.isInteger()){
				int value;
				value =dataGen.generateNextIntUniqueValue();
				ps.setInt(index, value);
				values.put(columname, value);
			}else if(type.isString()){
				String value;
				value = dataGen.generateNextStringUniqueValue();
				ps.setString(index, value);
				values.put(columname, value);
			}else if(type.isDate()){
				Date value;
				value = dataGen.generateNextDateUniqueValue();
				ps.setDate(index, value);
				values.put(columname, value);
			}else if(type.isDouble()){
				Double value;
				value = dataGen.generateNextDoubleUniqueValue();
				ps.setDouble(index, value);
				values.put(columname, value);
			}else if(type.isFile()){
				String value;
				value = dataGen.generateNextStringUniqueValue();
				FileInputStream fileInput = new FileInputStream(value);
				ps.setBlob(index, fileInput);
				values.put(columname, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessFacade.getInstance().addWarningMessage(ERROR_FILLING);
			throw new GeneratorException(UNIQUE_AUTOREFERENCED,e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			BusinessFacade.getInstance().addWarningMessage(ERROR_FILLING);
			throw new GeneratorException(FILE_INSERTION_ERROR+e.getMessage(),e);
		}
	}

	/**
	 * get the pkfk values
	 * @param columns
	 * @param columnames
	 * @param ps
	 * @param index
	 * @return
	 * @throws GeneratorException 
	 */
	private String pkFkValues(TableVO tab,  PreparedStatement ps, int index,Connection con,long[] permutationValues) throws GeneratorException {
		Map<String,ForeignKeyVO> pkfkcolumns= tab.getPkFkColumns();
		//get the fk columns
		List<ForeignKeyVO> columns = new ArrayList<ForeignKeyVO>(pkfkcolumns.values());
		//order by name
		Collections.sort(columns);
		String ret=""; //$NON-NLS-1$
		int pos = -1;
		String tablesName = ""; //$NON-NLS-1$
		for(ForeignKeyVO col: columns){
			if(!col.getOriginalTable().getName().equals(tablesName)){
				pos++;
				tablesName = col.getOriginalTable().getName();
			}
			long value = permutationValues[pos];
			nextForeignValue(col, value, ps, index,con);
			index++;
		}
		return ret;
	}
	/**
	 * change the prepare statement values based in the result set
	 * @param col
	 * @param index
	 * @param ps
	 * @param rs
	 * @throws GeneratorException
	 */
	//never used
	@IfForDataTypes(supportedTypes=5, id=3)
	protected void setForeignValues(ForeignKeyVO col, int index, PreparedStatement ps, ResultSet rs) throws GeneratorException {
		try {
			if (col.getType().isInteger()){
				int value = rs.getInt(1);
				ps.setInt(index, value);
			}
			else if(col.getType().isString()){
				String value = rs.getString(1);
				ps.setString(index, value);
			}
			else if(col.getType().isDate()){
				Date value = rs.getDate(1);
				ps.setDate(index, value);
			}
			else if(col.getType().isDouble()){
				Double value = rs.getDouble(1);
				ps.setDouble(index, value);
			}
			else if(col.getType().isFile()){
				String value = rs.getString(1);
				FileInputStream fileInput = new FileInputStream(value);
				ps.setBlob(index, fileInput);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GeneratorException(ERROR_FILLING,e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new GeneratorException(FILE_INSERTION_ERROR+e.getMessage(),e);		
		}
	}

	protected abstract void nextForeignValue(ForeignKeyVO col, long random, PreparedStatement ps, int index,Connection con) throws GeneratorException;

	class programmedHourMessage extends TimerTask{

		@Override
		public void run() {
			BusinessFacade.getInstance().addWarningMessage(HOUR+ViewConstants.DATEFORMAT.format(Calendar.getInstance().getTime()));
		}

	}
}
