package edu.eci.pgr.dataGenerators.concrete;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.business.types.MySqlTypeVO;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class ExternalDataBaseGenerator extends DataGenerator {

	private static final String PARAMETERS_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.0"); //$NON-NLS-1$

	private static final String NULL_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.1"); //$NON-NLS-1$

	private static final String DRIVER_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.2"); //$NON-NLS-1$

	private static final String VALID_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.3"); //$NON-NLS-1$

	private static final String TABLE_COLUMN_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.4"); //$NON-NLS-1$

	private static final String RANGE_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.5"); //$NON-NLS-1$

	private static final String DATA_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.6"); //$NON-NLS-1$

	private static final String URL_MESSAGE = MessageBundleManager.getString("ExternalDataBaseGenerator.7"); //$NON-NLS-1$

	private static final long serialVersionUID = 1L;

	//este generador toma las filas en orden de la otra base de datos
	//en la tabla y columna especificadas, si los valores son unicos 
	//la columna y tabla externas tambien deben serlo

	private static final String GENERATORMESSAGEERROR = MessageBundleManager.getString("ExternalDataBaseGenerator.8"); //$NON-NLS-1$

	private static final String URL = MessageBundleManager.getString("ExternalDataBaseGenerator.9") + //$NON-NLS-1$
	MessageBundleManager.getString("ExternalDataBaseGenerator.10"); //$NON-NLS-1$
	private static final String USER = MessageBundleManager.getString("ExternalDataBaseGenerator.11"); //$NON-NLS-1$
	private static final String PASSWORD= MessageBundleManager.getString("ExternalDataBaseGenerator.12"); //$NON-NLS-1$
	private static final String TABLE = MessageBundleManager.getString("ExternalDataBaseGenerator.13"); //$NON-NLS-1$
	private static final String COLUMN = MessageBundleManager.getString("ExternalDataBaseGenerator.14"); //$NON-NLS-1$
	private static final String DRIVER = MessageBundleManager.getString("ExternalDataBaseGenerator.15") + //$NON-NLS-1$
	MessageBundleManager.getString("ExternalDataBaseGenerator.16") + //$NON-NLS-1$
	MessageBundleManager.getString("ExternalDataBaseGenerator.17"); //$NON-NLS-1$

	private ResultSet rs;
	private ResultSet urs;
	private ExternalRetriever er;
	private long maximum;

	public ExternalDataBaseGenerator(){
		super(new String[]{USER,PASSWORD,URL,TABLE,COLUMN,DRIVER});
		types.add(TypesNames.STRING);
		types.add(TypesNames.DATE);
		types.add(TypesNames.DOUBLE);
		types.add(TypesNames.BOOLEAN);
		types.add(TypesNames.INT);
		uniqueTypes.add(TypesNames.STRING);
		uniqueTypes.add(TypesNames.DATE);
		uniqueTypes.add(TypesNames.DOUBLE);
		uniqueTypes.add(TypesNames.BOOLEAN);
		uniqueTypes.add(TypesNames.INT);
	}
	@Override
	public Date generateNextDateUniqueValue() throws UnsupportedDataException, GeneratorException {
		Date ret=new Date(0);
		try {
			urs=er.getUniqueValue();
			ret=urs.getDate(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;
	}

	@Override
	public Date generateNextDateValue() throws UnsupportedDataException, GeneratorException {
		Date ret=new Date(0);
		try {
			rs=er.getValue();
			ret=rs.getDate(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;
	}

	@Override
	public double generateNextDoubleUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		Double ret=new Double(0);
		try {
			urs=er.getUniqueValue();
			ret=urs.getDouble(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;
	}

	@Override
	public double generateNextDoubleValue()
	throws UnsupportedDataException, GeneratorException {
		Double ret=new Double(0);
		try {
			rs=er.getValue();
			ret=rs.getDouble(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;
	}

	@Override
	public int generateNextIntUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		Integer ret=new Integer(0);
		try {
			urs=er.getUniqueValue();
			ret=urs.getInt(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;
	}

	@Override
	public int generateNextIntValue()
	throws UnsupportedDataException, GeneratorException {
		Integer ret=new Integer(0);
		try {
			rs=er.getValue();
			ret=rs.getInt(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;
	}

	@Override
	public String generateNextStringUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		String ret=new String();
		try {
			urs=er.getUniqueValue();
			ret=urs.getString(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;	
	}

	@Override
	public String generateNextStringValue()
	throws UnsupportedDataException, GeneratorException {
		String ret=new String();
		try {
			rs=er.getValue();
			ret=rs.getString(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;	
	}

	@Override
	public boolean generateNextBooleanValue() throws UnsupportedDataException, GeneratorException {
		Boolean ret=new Boolean(true);
		try {
			rs=er.getValue();
			ret=rs.getBoolean(1);
		} catch (SQLException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new GeneratorException(GENERATORMESSAGEERROR +"\n"+e.getMessage());
		}
		return ret;	
	}

	@Override
	public List<String> getTypes() {
		return this.types;
	}

	@Override
	public List<String> getUniqueTypes() {
		return this.uniqueTypes;
	}


	private class ExternalConnection{

		private Connection con;
		private ExternalConnection() throws SQLException, ClassNotFoundException{
			String url=(String) attributeValues.get(URL);
			String pass=(String) attributeValues.get(PASSWORD);
			String user=(String) attributeValues.get(USER);
			String driver=driverManager();
			con=DriverManager.getConnection(url,user,pass);
			Class.forName(driver);
		}

		private String driverManager() {
			String ret = "";
			if(((String) attributeValues.get(DRIVER)).equals("MYSQL"))
				ret = MessageBundleManager.getString("DATABASE_DRIVER_CLASSNAME1");      
			else if(((String) attributeValues.get(DRIVER)).equals("ORACLE"))
				ret = MessageBundleManager.getString("DATABASE_DRIVER_CLASSNAME2");
			else if(((String) attributeValues.get(DRIVER)).equals("SQLSERVER"))
				ret = MessageBundleManager.getString("DATABASE_DRIVER_CLASSNAME3");
			else 
				ret = (String) attributeValues.get(DRIVER);
			return ret;

		}

		public Connection getConnection(){
			return con;
		}

		public void closeConnection() throws SQLException{
			con.close();
		}
	}
	
	private class ExternalRetriever{
		private int rowcount;
		private int uniqueRowcount;
		protected ExternalConnection e;

		public ExternalRetriever() throws SQLException, ClassNotFoundException{
			rowcount = 1;
			uniqueRowcount = 1;
			e = new ExternalConnection();
			Connection con = e.getConnection();
			String columnname = (String) attributeValues.get(COLUMN);
			String tablename = (String) attributeValues.get(TABLE);
			//el querry toma los valores no nulos
			String distinct ="";
			String sql = "SELECT "+distinct+ "("+columnname+") FROM "+tablename+ " WHERE "
			+columnname+(" IS NOT NULL");
			PreparedStatement ps=con.prepareStatement(sql);
			ps.execute();
			rs = ps.getResultSet();
			distinct = "DISTINCT ";
			sql = "SELECT "+distinct+ "("+columnname+") FROM "+tablename+ " WHERE "
			+columnname+(" IS NOT NULL");
			PreparedStatement ps2=con.prepareStatement(sql);
			ps2.execute();
			urs = ps2.getResultSet();
			sql = "SELECT COUNT("+distinct+ "("+columnname+")) FROM "+tablename+ " WHERE "
			+columnname+(" IS NOT NULL");
			PreparedStatement ps3=con.prepareStatement(sql);
			ps3.execute();
			ResultSet rs= ps3.getResultSet();
			if(rs!=null)
				rs.next();
			maximum = rs.getLong(1);
		}

		public ResultSet getValue() throws SQLException, ClassNotFoundException{
			rs.absolute(rowcount);
			rowcount++;
			return rs;
		}
		
		public ResultSet getUniqueValue() throws SQLException, ClassNotFoundException{
			urs.absolute(uniqueRowcount);
			uniqueRowcount++;
			return urs;
		}

		public void closeConnection() throws SQLException{
			e.closeConnection();
		}
		
		//mira el typo de dato de la  columna
		public TypeVO getExternalColumnDataType() throws SQLException {
			TypeVO typ = null;
			ResultSet rs=e.getConnection().getMetaData().getColumns(null, null,(String) attributeValues.get(TABLE) , null);
			if(rs != null){
				while(rs.next()){
					if(rs.getString("COLUMN_NAME").equals((String) attributeValues.get(COLUMN))){
						typ= new MySqlTypeVO(rs.getString("TYPE_NAME"),
								rs.getInt("COLUMN_SIZE"),rs.getInt("DECIMAL_DIGITS"));
					}
				}

			}
			return typ;
		}
	}

	@Override
	public void addAttributeValue(String attribute, Object value)
	throws UnsupportedAttributeValueException, UnsupportedAttribute {
		if(attribute.equals(URL)){
			String val = (String) value;
			if(!val.contains("jdbc") && !val.contains(":") && !val.contains("/")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				throw new UnsupportedAttributeValueException(URL_MESSAGE);
		}
		this.attributeValues.put(attribute,value);
	}
	
	@Override
	public void doneConfiguration()
			throws UnsupportedAttributeValueException, UnsupportedAttribute {
		try{
			ExternalRetriever e = new ExternalRetriever();
			//probar la coneccion, si no se puede conectar lanzzara una excepcion y esto
			//quiere decir que hay algun parametro malo
			e.getValue();
			e.closeConnection();
			//si no ha lanzado excepcion entonces inicializa el retriever externo 
			er = new ExternalRetriever();
			TypeVO extType = er.getExternalColumnDataType();
			if(!extType.getName().equals(this.type.getName()))
				throw new UnsupportedAttributeValueException(DATA_MESSAGE);
			if(extType.getRange()>this.type.getRange())
				throw new UnsupportedAttributeValueException(RANGE_MESSAGE);
		}catch(SQLException e){
			throw new UnsupportedAttributeValueException(TABLE_COLUMN_MESSAGE +
					VALID_MESSAGE+e.getMessage(),e);

		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new UnsupportedAttributeValueException(DRIVER_MESSAGE+e.getMessage(),e);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			throw new UnsupportedAttribute(NULL_MESSAGE);
		}catch(Exception e){
			e.printStackTrace();
			throw new UnsupportedAttribute(PARAMETERS_MESSAGE+e.getMessage(),e);
		}
	}
	@Override
	public long getMaximumUniqueValues() {
		return maximum;
	}
}
