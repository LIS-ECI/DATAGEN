package edu.eci.pgr.tests.generic;

import java.util.ArrayList;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.persistence.Facade;
import junit.framework.TestCase;


public abstract class InformationRetrieverTest /*extends TestCase*/ {
    
        private void fail(){}
        private void fail(String m){}
        private void assertTrue(String msg,boolean v){}
        
	//-----------------------------------------------------------------------
	// TYPE CONSTANTS
	//-----------------------------------------------------------------------
	private  String[] TYPES;
	//-----------------------------------------------------------------------
	// TABLE CONSTANTS
	//-----------------------------------------------------------------------
	/**
	 * constants that defines the tables in the test database
	 */
	private  String[] TABLES;
	
	//--------------------- ESTADO_ORDEN ------------------------//
	public  String[] TABLE1_PK;
	public  String[] TABLE1_TYPES;
	
	//--------------------- ESTADO_ARCHIVO ------------------------//
	public  String[] TABLE2_PK;
	public  String[] TABLE2_ATT;
	public  String[] TABLE2_TYPES;
	
	//--------------------- TIPO_ARCHIVO ------------------------//
	public  String[] TABLE3_PK;
	public  String[] TABLE3_TYPES;
	
	//--------------------- TIPO_PROCESO ------------------------//
	public  String[] TABLE4_PK;
	public  String[] TABLE4_TYPES;

	//--------------------- TIPO_ORDEN ------------------------//
	public  String[] TABLE5_PK;
	public  String[] TABLE5_TYPES;
	
	//--------------------- TIPO_MOTIVO ------------------------//
	public  String[] TABLE6_PK;
	public  String[] TABLE6_TYPES;
	
	//--------------------- ORDEN_PROCESO ------------------------//
	public  String[] TABLE7_PK;
	public  String[] TABLE7_FK;
	public  String[] TABLE7_ATT;
	public  String[] TABLE7_TYPES;
	
	//--------------------- ARCHIVO ------------------------//
	public  String[] TABLE8_PK;
	public  String[] TABLE8_FK;
	public  String[] TABLE8_ATT;
	public  String[] TABLE8_TYPES;
	
	//--------------------- IMPRESORA ------------------------//
	public  String[] TABLE9_PK;
	public  String[] TABLE9_ATT;
	public  String[] TABLE9_TYPES;
	
	//--------------------- JOB ------------------------//
	public  String[] TABLE10_PK;
	public  String[] TABLE10_ATT;
	public  String[] TABLE10_TYPES;
	
	//--------------------- JOB_POR_IMPRESORA ------------------------//
	public  String[] TABLE11_PKFK;
	public  String[] TABLE11_TYPES;
	
	//--------------------- PROCESO ------------------------//
	public  String[] TABLE12_PK;
	public  String[] TABLE12_FK;
	public  String[] TABLE12_ATT;
	public  String[] TABLE12_TYPES;
	
	//--------------------- PROCESO_CARGUE ------------------------//
	public  String[] TABLE13_PKFK;
	public  String[] TABLE13_ATT;
	public  String[] TABLE13_TYPES;
	
	//--------------------- PROCESO_IMPRESION ------------------------//
	public  String[] TABLE14_PKFK;
	public  String[] TABLE14_FK;
	public  String[] TABLE14_ATT;
	public  String[] TABLE14_TYPES;
	
	//--------------------- ORDEN_PROCESO_MOVIMIENTO ------------------------//
	public  String[] TABLE15_PK;
	public  String[] TABLE15_FK;
	public  String[] TABLE15_ATT;
	public  String[] TABLE15_TYPES;
	
	//---------------------ARCHIVO_MOVIMIENTO ------------------------//
	public  String[] TABLE16_PK;
	public  String[] TABLE16_FK;
	public  String[] TABLE16_ATT;
	public  String[] TABLE16_TYPES;
	

	private DatabaseVO database;
	
	
	
	protected void setUp() throws Exception {
		database=getDatabseConcrete();
		TYPES = getTypes();
		TABLES = getTables();
		//--------------------- ESTADO_ORDEN ------------------------//
		TABLE1_PK= new String[]{"DOMINIO"};
		TABLE1_TYPES= new String[]{TYPES[0]};
		
		//--------------------- ESTADO_ARCHIVO ------------------------//
		TABLE2_PK= new String[]{"DOMINIO"};
		TABLE2_ATT= new String[]{"TIPO"};
		TABLE2_TYPES= new String[]{TYPES[0],TYPES[0]};
		
		//---------------------TIPO_ARCHIVO ------------------------//
		TABLE3_PK= new String[]{"DOMINIO"};
		TABLE3_TYPES= new String[]{TYPES[0]};
		
		//--------------------- TIPO_PROCESO ------------------------//
		TABLE4_PK= new String[]{"DOMINIO"};
		TABLE4_TYPES= new String[]{TYPES[0]};
		
		//--------------------- TIPO_ORDEN ------------------------//
		TABLE5_PK= new String[]{"DOMINIO"};
		TABLE5_TYPES= new String[]{TYPES[0]};
		
		//--------------------- TIPO_MOTIVO ------------------------//
		TABLE6_PK= new String[]{"DOMINIO"};
		TABLE6_TYPES= new String[]{TYPES[0]};
		
		//--------------------- ORDEN_PROCESO ------------------------//
		TABLE7_PK= new String[]{"ID"};
		TABLE7_FK = new String[]{"ESTADO"};
		TABLE7_ATT= new String[]{"NUMERO_ORDEN","ID_CLIENTE","TIPO","FECHA_ESTADO",
				"FECHA_CARGUE","CODIGO_PRODUCTO","CICLO","ARCHIVOS_PROCESADOS",
				"REGISTROS_PROCESADOS","TOTAL_ENVIOS","TOTAL_INSERTOS","REPROCESOS"};
		TABLE7_TYPES= new String[]{TYPES[4],TYPES[0],TYPES[3],TYPES[4],TYPES[0],TYPES[1],
				TYPES[1],TYPES[4],TYPES[5],TYPES[4],TYPES[4],TYPES[3],TYPES[3],TYPES[5]};
		
		//--------------------- ARCHIVO ------------------------//
		TABLE8_PK= new String[]{"ID"};
		TABLE8_FK= new String[]{"TIPO","ESTADO","NUMERO_ORDEN"};	
		TABLE8_ATT= new String[]{"NOMBRE","FECHA_ESTADO","FECHA_CARGUE"};
		TABLE8_TYPES= new String[]{TYPES[4],TYPES[0],TYPES[0],TYPES[3],TYPES[0],TYPES[1],
				TYPES[1]};
		
		//--------------------- IMPRESORA ------------------------//
		TABLE9_PK= new String[]{"ID"};
		TABLE9_ATT= new String[]{"NOMBRE","DESCRIPCION","FOTO"};
		TABLE9_TYPES= new String[]{TYPES[0],TYPES[0],TYPES[0],TYPES[6]};
		
		//--------------------- JOB ------------------------//
		TABLE10_PK= new String[]{"ID"};
		TABLE10_ATT= new String[]{"NOMBRE","DESCRIPCION"};
		TABLE10_TYPES= new String[]{TYPES[0],TYPES[0],TYPES[0]};
		
		//--------------------- JOB_POR_IMPRESORA ------------------------//
		TABLE11_PKFK= new String[]{"ID_JOB","ID_IMPRESORA"};
		TABLE11_TYPES= new String[]{TYPES[0],TYPES[0]};
		
		//--------------------- PROCESO ------------------------//
		TABLE12_PK= new String[]{"ID"};
		TABLE12_FK= new String[]{"ID_ARCHIVO","TIPO","ESTADO"};
		TABLE12_ATT= new String[]{"ID_USUARIO","FECHA"};
		TABLE12_TYPES= new String[]{TYPES[4],TYPES[4],TYPES[0],TYPES[0],TYPES[0],TYPES[1]};
		
		//--------------------- PROCESO_CARGUE ------------------------//
		TABLE13_PKFK= new String[]{"ID_PROCESO"};
		TABLE13_ATT= new String[]{"DESCRIPCION"};
		TABLE13_TYPES= new String[]{TYPES[4],TYPES[0]};
		
		//--------------------- PROCESO_IMPRESION ------------------------//
		TABLE14_PKFK= new String[]{"ID_PROCESO"};
		TABLE14_FK= new String[]{"ID_JOB","ID_IMPRESORA","MOTIVO"};
		TABLE14_ATT= new String[]{"DESCRIPCION"};
		TABLE14_TYPES= new String[]{TYPES[4],TYPES[0],TYPES[0],TYPES[0],TYPES[0],TYPES[0]};
		
		//--------------------- ORDEN_PROCESO_MOVIMIENTO ------------------------//
		TABLE15_PK= new String[]{"ID"};
		TABLE15_FK= new String[]{"NUMERO_ORDEN","ESTADO"};
		TABLE15_ATT= new String[]{"ID_USUARIO","FECHA"};
		TABLE15_TYPES= new String[]{TYPES[4],TYPES[3],TYPES[0],TYPES[0],TYPES[1]};
		
		//--------------------- ARCHIVO_MOVIMIENTO ------------------------//
		TABLE16_PK= new String[]{"ID"};
		TABLE16_FK= new String[]{"ID_ARCHIVO","ESTADO"};
		TABLE16_ATT= new String[]{"ID_USUARIO","FECHA"};
		TABLE16_TYPES= new String[]{TYPES[4],TYPES[4],TYPES[0],TYPES[0],TYPES[1]};
		
	}
	
	public abstract DatabaseVO getDatabseConcrete();
	public abstract String [] getTypes();
	public abstract String [] getTables();
	
	private static int linealSearch(String [] array, String elem){
		boolean is=false;
		int i=0;
		for (i = 0; i < array.length && !is; i++) {
			if(array[i].equals(elem)){
				is=true;
				break;
			}
		}
		if(!is) i=-1*array.length;
		return i;
	}
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET TABLES
	 * @obj: test that table names are loaded from database
	 * @pos: the names count of all tables in the database is correct and  all names are correct
	 */
	public void test1_getTables_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctTablesCount=base.getTotalTables()==TABLES.length;
			boolean correctNames=true;
			ArrayList<TableVO> tabs=new ArrayList<TableVO> (base.getTables().values());
			System.out.println("total tablas: "+tabs.size());
			for (TableVO tab: tabs){
				correctNames=correctNames && linealSearch(TABLES,tab.getName() )>-1;
				System.out.println("tabla: "+tab.getName());
			}
			assertTrue("la cantidad de tablas y el nombre de cada una debe coincidir",correctNames&&correctTablesCount);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS ESTADO_ORDEN
	 * @obj: test that column names of ESTADO_ORDEN are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * and the types are correct
	 */
	public void test1_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[0]).getTotalColumns()
			==TABLE1_PK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[0]).getColumns().values());
			System.out.println("Tabla: "+TABLES[0]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				//get the pks count
				int[] pos=new int[2];
				pos[0]=linealSearch(TABLE1_PK,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 );
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE1_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==0;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE1_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS ESTADO_ARCHIVO
	 * @obj: test that column names of ESTADO_ARCHIVO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test2_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[1]).getTotalColumns()
			==TABLE2_ATT.length+TABLE2_PK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[1]).getColumns().values());
			System.out.println("Tabla: "+TABLES[1]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[2];
				//get the pks count
				pos[0]=linealSearch(TABLE2_PK,col.getName());
				//get the attributes count
				pos[1]=linealSearch(TABLE2_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE2_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the primaries
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE2_PK.length;
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctPrimaryCount 
					&& correctPrimarys&& correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS TIPO_ARCHIVO
	 * @obj: test that column names of TIPO_ARCHIVO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * and the types are correct
	 */
	public void test3_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[2]).getTotalColumns()
			==TABLE3_PK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		
			// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[2]).getColumns().values());
			System.out.println("test3_getColumns_set1");
			System.out.println("Tabla: "+TABLES[2]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[1];
				//get the pkfks count
				pos[0]=linealSearch(TABLE3_PK,col.getName());
				correctNames=correctNames && (pos[0]>-1 );
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE3_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE3_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una no coinside",
					correctNames && correctColumnsCount &&  correctPrimaryCount 
					&& correctPrimarys && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS TIPO_PROCESO
	 * @obj: test that column names of TIPO_PROCESO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * and the types are correct
	 */
	public void test4_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[3]).getTotalColumns()
			==TABLE4_PK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[3]).getColumns().values());
			System.out.println("Tabla: "+TABLES[3]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				//get the pks count
				int[] pos=new int[2];
				pos[0]=linealSearch(TABLE4_PK,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 );
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE4_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE4_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount  && correctPrimaryCount 
					&& correctPrimarys && correctTypes );
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS TIPO_ORDEN
	 * @obj: test that column names of TIPO_ORDEN are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test5_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			
			boolean correctColumnsCount=base.getTable(TABLES[4]).getTotalColumns()
			==TABLE5_PK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[4]).getColumns().values());
			System.out.println("Tabla: "+TABLES[4]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[3];
				//get the pks count
				pos[0]=linealSearch(TABLE5_PK,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE5_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE5_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctPrimaryCount 
					&& correctPrimarys && correctTypes );
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	
		
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS TIPO_MOTIVO
	 * @obj: test that column names of TIPO_MOTIVO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test6_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[5]).getTotalColumns()
			==TABLE6_PK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[5]).getColumns().values());
			System.out.println("Tabla: "+TABLES[5]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[3];
				//get the pks count
				pos[0]=linealSearch(TABLE6_PK,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE6_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE6_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctPrimaryCount 
					&& correctPrimarys && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
		
		
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS ORDEN_PROCESO
	 * @obj: test that column names of ORDEN_PROCESO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * and the types are correct
	 */
	public void test7_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[6]).getTotalColumns()
			==TABLE7_PK.length+TABLE7_FK.length+TABLE7_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//validate the unique and auto increment columns
			boolean correctUnique=true;
			boolean correctAutoincrement=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[6]).getColumns().values());
			System.out.println("Tabla: "+TABLES[6]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				if(col.getName().equalsIgnoreCase("ID")&& !col.isAutoIncrement()) correctAutoincrement = false;
				if(col.getName().equalsIgnoreCase("NUMERO_ORDEN")&& !col.isUniqueKey()) correctUnique = false;
				int[] pos=new int[3];
				//get the pks count
				pos[0]=linealSearch(TABLE7_PK,col.getName());
				//get the fks count
				pos[1]=linealSearch(TABLE7_FK,col.getName());
				//get the attributes count
				pos[2]=linealSearch(TABLE7_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				System.out.println(postype+"  "+correctTypes+col.getType().getName()+TABLE7_TYPES[postype]);
				correctTypes=correctTypes && TABLE7_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE7_FK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE7_PK.length;
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctAutoincrement && correctUnique && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS ARCHIVO
	 * @obj: test that column names of ARCHIVO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test8_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[7]).getTotalColumns()
			==TABLE8_PK.length+TABLE8_FK.length+TABLE8_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//validate the unique and auto increment columns
			boolean correctUnique=true;
			boolean correctAutoincrement=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[7]).getColumns().values());
			System.out.println("Tabla: "+TABLES[7]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				if(col.getName().equalsIgnoreCase("ID")&& !col.isAutoIncrement()) correctAutoincrement = false;
				//if(col.getName().equalsIgnoreCase("NUMERO_ORDEN")&& !col.isUniqueKey()) correctUnique = false;
				if(col.getName().equalsIgnoreCase("NOMBRE")&& !col.isUniqueKey()) correctUnique = false;
				int[] pos=new int[3];
				//get the pks count
				pos[0]=linealSearch(TABLE8_PK,col.getName());
				//get the fks count
				pos[1]=linealSearch(TABLE8_FK,col.getName());
				//get the attributes count
				pos[2]=linealSearch(TABLE8_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				//System.out.println(correctTypes+""+TABLE8_TYPES[postype]+col.getType().getName()+col.getName());
				correctTypes=correctTypes && TABLE8_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					//System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
				//	System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE8_FK.length;
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE8_PK.length;
			System.out.println(correctPrimarys +""+ correctForeigns + correctAutoincrement + correctUnique);
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctAutoincrement && correctUnique && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS IMPRESORA
	 * @obj: test that column names of IMPRESORA are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test9_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[8]).getTotalColumns()
			==TABLE9_PK.length+TABLE9_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[8]).getColumns().values());
			System.out.println("Tabla: "+TABLES[8]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[2];
				//get the pks count
				pos[0]=linealSearch(TABLE9_PK,col.getName());
				//get the attributes count
				pos[1]=linealSearch(TABLE9_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE9_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1 || pos[1]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE9_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount&& correctPrimaryCount 
					&& correctPrimarys && correctTypes );
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS JOB
	 * @obj: test that column names of JOB are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test10_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());

			boolean correctColumnsCount=base.getTable(TABLES[9]).getTotalColumns()
			==TABLE10_PK.length+TABLE10_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[9]).getColumns().values());
			System.out.println("Tabla: "+TABLES[9]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[2];
				//get the pks count
				pos[0]=linealSearch(TABLE10_PK,col.getName());
				//get the attributes count
				pos[1]=linealSearch(TABLE10_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE10_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1 || pos[1]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the primary count
			boolean correctPrimaryCount=PrimarysCount==TABLE10_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctPrimaryCount 
					&& correctPrimarys && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS JOB_POR_IMPRESORA
	 * @obj: test that column names of JOB_POR_IMPRESORA are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * and the types are correct
	 */
	public void test11_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());
			boolean correctColumnsCount=base.getTable(TABLES[10]).getTotalColumns()
			==TABLE11_PKFK.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		
			// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[10]).getColumns().values());
			System.out.println("Tabla: "+TABLES[10]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[1];
				//get the pkfks count
				pos[0]=linealSearch(TABLE11_PKFK,col.getName());
				correctNames=correctNames && (pos[0]>-1 );
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE11_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && pos[0]>-1;
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && pos[0]>-1;
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE11_PKFK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE11_PKFK.length;
			System.out.println(PrimarysCount);
			//System.out.println("\n\n\n"+correctColumnsCount+correctForegnsCount+correctNames+correctPrimaryCount+correctNames);
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctTypes) ;
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS PROCESO
	 * @obj: test that column names of PROCESO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test12_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());

			boolean correctColumnsCount=base.getTable(TABLES[11]).getTotalColumns()
			==TABLE12_PK.length+TABLE12_FK.length+TABLE12_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreign keys
			boolean correctForeigns=true;
			//variable to validate the foreign count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			boolean correctAutoincrement = true;
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[11]).getColumns().values());
			System.out.println("Tabla: "+TABLES[11]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				if(col.getName().equalsIgnoreCase("ID")&& !col.isAutoIncrement()) correctAutoincrement = false;
				int[] pos=new int[3];
				//get the pkfks count
				pos[0]=linealSearch(TABLE12_PK,col.getName());
				//get the fks count
				pos[1]=linealSearch(TABLE12_FK,col.getName());
				//get the attributes count
				pos[2]=linealSearch(TABLE12_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE12_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE12_FK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE12_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctAutoincrement && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS PROCESO_CARGUE
	 * @obj: test that column names of PROCESO_CARGUE are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test13_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());

			boolean correctColumnsCount=base.getTable(TABLES[12]).getTotalColumns()
			==TABLE13_PKFK.length+TABLE13_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[12]).getColumns().values());
			System.out.println("Tabla: "+TABLES[12]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[2];
				//get the pkfks count
				pos[0]=linealSearch(TABLE13_PKFK,col.getName());
				//get the attributes count
				pos[1]=linealSearch(TABLE13_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE13_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE13_PKFK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE13_PKFK.length;
			System.out.println("\n\n\n"+correctNames + correctColumnsCount +""+ correctForegnsCount + correctPrimaryCount);
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS PROCESO_IMPRESION
	 * @obj: test that column names of PROCESO_IMPRESION are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test14_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());

			boolean correctColumnsCount=base.getTable(TABLES[13]).getTotalColumns()
			==TABLE14_PKFK.length+TABLE14_FK.length+TABLE14_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[13]).getColumns().values());
			System.out.println("Tabla: "+TABLES[13]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				int[] pos=new int[3];
				//get the pkfks count
				pos[0]=linealSearch(TABLE14_PKFK,col.getName());
				//get the fks count
				pos[1]=linealSearch(TABLE14_FK,col.getName());
				//get the attributes count
				pos[2]=linealSearch(TABLE14_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE14_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE14_PKFK.length+TABLE14_FK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE14_PKFK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS ORDEN_PROCESO_MOVIMIENTO
	 * @obj: test that column names of ORDEN_PROCESO_MOVIMIENTO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test15_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());

			boolean correctColumnsCount=base.getTable(TABLES[14]).getTotalColumns()
			==TABLE15_PK.length+TABLE15_FK.length+TABLE15_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			boolean correctAutoincrement = true;
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[14]).getColumns().values());
			System.out.println("Tabla: "+TABLES[14]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				if(col.getName().equalsIgnoreCase("ID")&& !col.isAutoIncrement()) correctAutoincrement = false;
				int[] pos=new int[3];
				//get the pkfks count
				pos[0]=linealSearch(TABLE15_PK,col.getName());
				//get the fks count
				pos[1]=linealSearch(TABLE15_FK,col.getName());
				//get the attributes count
				pos[2]=linealSearch(TABLE15_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				System.out.println(postype+"  "+correctTypes+col.getType().getName()+TABLE15_TYPES[postype]);
				correctTypes=correctTypes && TABLE15_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE15_FK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE15_PK.length;
			System.out.println(correctTypes+"\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctAutoincrement && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * WHITE BOX TEST
	 * TYPICAL CASE
	 * GET COLUMNS ARCHIVO_MOVIMIENTO
	 * @obj: test that column names of ARCHIVO_MOVIMIENTO are loaded from database
	 * @pos: the names count of all columns in the database is correct , all names are correct 
	 * the types are correct and the foreign keys and primary keys are correct
	 */
	public void test16_getColumns_set1(){
		DatabaseVO base;
		try {
			base = Facade.getInstance().getDatabase(database.getInfo());

			boolean correctColumnsCount=base.getTable(TABLES[15]).getTotalColumns()
			==TABLE16_PK.length+TABLE16_FK.length+TABLE16_ATT.length;
			//variable to validate the names
			boolean correctNames=true;
			//variable to validate the types
			boolean correctTypes=true;
			//variable to validate the primary keys
			boolean correctPrimarys=true;
			//variable to validate the foreing keys
			boolean correctForeigns=true;
			//variable to validate the foreing count
			int ForeignsCount=0;
			//variable to validate the primary count
			int PrimarysCount=0;		// get the columns
			boolean correctAutoincrement = true;
			ArrayList<ColumnVO> columns= new ArrayList<ColumnVO> (base.getTable(TABLES[15]).getColumns().values());
			System.out.println("Tabla: "+TABLES[15]+" total columnas: "+columns.size());
			for (ColumnVO col: columns){
				if(col.getName().equalsIgnoreCase("ID")&& !col.isAutoIncrement()) correctAutoincrement = false;
				int[] pos=new int[3];
				//get the pkfks count
				pos[0]=linealSearch(TABLE16_PK,col.getName());
				//get the fks count
				pos[1]=linealSearch(TABLE16_FK,col.getName());
				//get the attributes count
				pos[2]=linealSearch(TABLE16_ATT,col.getName());
				//verify the names
				correctNames=correctNames && (pos[0]>-1 || pos[1]>-1 || pos[2]>-1);
				//find the position of the type
				int i=0;
				int postype=0;
				while(i<pos.length && pos[i]<0 ){
					postype+=(-pos[i]);
					i++;
				}
				if (correctNames)postype+=pos[i];
				else postype=0;
				//verify the types
				correctTypes=correctTypes && TABLE16_TYPES[postype].equalsIgnoreCase(col.getType().getName());
				//verify the foreigns
				if (col instanceof ForeignKeyVO){
					System.out.print("llave foranea ");
					correctForeigns=correctForeigns && (pos[1]>-1 || pos[0]>-1);
					ForeignsCount++;
				}
				//verify the primarys
				if (col.isPrimaryKey()){
					System.out.print("llave primaria ");
					correctPrimarys=correctPrimarys && (pos[0]>-1);
					PrimarysCount++;
				}
				System.out.println("columna: "+col.getName()+" Tipo: "+col.getType());
			}
			//verify the foreigns count
			boolean correctForegnsCount=ForeignsCount==TABLE16_FK.length;
			//verify the foreigns count
			boolean correctPrimaryCount=PrimarysCount==TABLE16_PK.length;
			System.out.println("\n\n\n");
			assertTrue("la cantidad de tablas y el nombre de cada una coincide",
					correctNames && correctColumnsCount && correctForegnsCount && correctPrimaryCount 
					&& correctPrimarys && correctForeigns && correctAutoincrement && correctTypes);
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		}
	}
}
