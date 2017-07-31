package edu.eci.pgr.tests.generic;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.mysql.jdbc.Connection;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.components.SortJgraphComponent;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.dataGenerators.concrete.DateNormalgenerator;
import edu.eci.pgr.dataGenerators.concrete.FileGenerator;
import edu.eci.pgr.dataGenerators.concrete.NameGenerator;
import edu.eci.pgr.dataGenerators.concrete.NumberNormalGenerator;
import edu.eci.pgr.dataGenerators.concrete.TelephoneDataGenerator;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.persistence.ConnectionFactory;
import edu.eci.pgr.persistence.Facade;
import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.dao.FillerDao;
import edu.eci.pgr.sorting.Task;


public abstract class RowGeneratorTest extends TestCase{
	private static final long INSERTTIMES = 500;
	private static final String USER="root";
	private static final String PASSWORD="pass";
	private RowGenerator rowgen;
	private long[] expectedamounts;
	private int[] realamounts;
	private SortJgraphComponent sorter;
	private ArrayList<TableVO> tables;
	private List<Task> tasks;
	private DatabaseVO database;
	private DataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		rowgen=getRowGeneratorConcrete();
		sorter= new SortJgraphComponent();
		tables = null;
	}
	@Override
	protected void tearDown() throws Exception {
		ConnectionFactory.resetInstance();
	}
	public abstract RowGenerator getRowGeneratorConcrete();
	public abstract String getDriverConcrete();
	public abstract String getEngineConcrete();
	public abstract String getUrl1Concrete();
	public abstract String getUrl2Concrete();
	public abstract String getUrl3Concrete();
	public abstract String getUrl4Concrete();

	public void test1_fill(){
		try {
			int i=0;
			boolean correct=true;
			database= new DatabaseVO(getUrl1Concrete(),USER,PASSWORD, getDriverConcrete(), getEngineConcrete());
			Connection con = (Connection) ConnectionFactory.getInstance().getConnection(database.getInfo());
			tables = new ArrayList<TableVO>(Facade.getInstance().getDatabase(database.getInfo()).getTables().values());
			tasks= sorter.sortTables(tables);
			TableVO tab;
			//data generator assignment
			for(Task task: tasks){
				task.getTable().setInsertTimes(INSERTTIMES);
				tab = task.getTable();
				for(ColumnVO col: tab.getColumns().values()){
					if(col.getType().isDate()){
						gen = new DateNormalgenerator();
						col.setGenerator(gen);
						//standar desv
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.8"), "2");
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.9"), "2000");
						//format
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.10") + 
								MessageBundleManager.getString("DateNormalgenerator.11") + 
								MessageBundleManager.getString("DateNormalgenerator.12") , 
								"yyyy-MM-dd EE hh:mm:ss");
					}else if(col.getType().isInteger()){
						System.out.println(col.getType().getRange());
						gen = new TelephoneDataGenerator();
						col.setGenerator(gen);
					}else if(col.getType().isDouble() ){
						gen = new NumberNormalGenerator();
						col.setGenerator(gen);
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.16"), "17000");
						//standard desv
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.15"), "10000");
						//decimal
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.17"), "2");
						//intengers
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.18"), "5");
						
					}else if(col.getType().isString()){
						gen = new NameGenerator();
						col.setGenerator(gen);
						//female percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.11"), "YES");
						//male percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.12"), "YES");
						//format
						String format = "Dear Ing. %FN %I %LN"; 
						if(col.getType().getRange()<30)
							format = "%FN ";
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.13")+"%FN"+", "
								+"%I"+MessageBundleManager.getString("NameGenerator.14")
								+"%LN"+MessageBundleManager.getString("NameGenerator.15"), 
								format);						
					}else if(col.getType().isFile()){
						gen = new FileGenerator();
						col.setGenerator(gen);
						// folder path
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.14"),"/home/felipe/Imágenes");
						//extension
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.15"),"gif");
					}
					col.getGenerator().doneConfiguration();
				}
			}
			expectedamounts= new long[tasks.size()];
			realamounts= new int[tasks.size()];
			rowgen.Fill(tasks);
			for(Task task: tasks){
				expectedamounts[i]=task.getTable().getInsertTimes();
				realamounts[i]=verifylength(task.getTable().getName(),con);
				i++;
			}
			for (int j = 0; j < expectedamounts.length && correct==true; j++) {
				if(expectedamounts[j]!=realamounts[j]) correct=false;
			}
			assertTrue(correct);
		} catch (GeneratorException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedDataException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		} catch (WriterException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			fail();
		}

	}

	public void test2_fill(){
		try {
			int i=0;
			boolean correct=true;
			database= new DatabaseVO(getUrl2Concrete(),USER,PASSWORD, getDriverConcrete(), getEngineConcrete());
			Connection con = (Connection) ConnectionFactory.getInstance().getConnection(database.getInfo());
			tables = new ArrayList<TableVO>(Facade.getInstance().getDatabase(database.getInfo()).getTables().values());
			tasks= sorter.sortTables(tables);
			TableVO tab;
			//data generator assignment
			for(Task task: tasks){
				task.getTable().setInsertTimes(INSERTTIMES);
				tab = task.getTable();
				for(ColumnVO col: tab.getColumns().values()){
					if(col.getType().isDate()){
						gen = new DateNormalgenerator();
						col.setGenerator(gen);
						//standar desv
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.8"), "2");
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.9"), "2000");
						//format
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.10") + 
								MessageBundleManager.getString("DateNormalgenerator.11") + 
								MessageBundleManager.getString("DateNormalgenerator.12") , 
								"yyyy-MM-dd EE hh:mm:ss");
					}else if(col.getType().isInteger()){
						gen = new TelephoneDataGenerator();
						col.setGenerator(gen);
					}else if(col.getType().isDouble() ){
						gen = new NumberNormalGenerator();
						col.setGenerator(gen);
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.16"), "17000");
						//standard desv
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.15"), "10000");
						//decimal
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.17"), "2");
						//intengers
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.18"), "5");
						
					}else if(col.getType().isString()){
						gen = new NameGenerator();
						col.setGenerator(gen);
						//female percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.11"), "YES");
						//male percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.12"), "YES");
						//format
						String format = "Dear Ing. %FN %I %LN"; 
						if(col.getType().getRange()<30)
							format = "%FN ";
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.13")+"%FN"+", "
								+"%I"+MessageBundleManager.getString("NameGenerator.14")
								+"%LN"+MessageBundleManager.getString("NameGenerator.15"), 
								format);						
					}else if(col.getType().isFile()){
						gen = new FileGenerator();
						col.setGenerator(gen);
						// folder path
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.14"),"/home/felipe/Imágenes");
						//extension
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.15"),"gif");
					}
					col.getGenerator().doneConfiguration();
				}
			}
			expectedamounts= new long[tasks.size()];
			realamounts= new int[tasks.size()];
			rowgen.Fill(tasks);
			for(Task task: tasks){
				expectedamounts[i]=task.getTable().getInsertTimes();
				realamounts[i]=verifylength(task.getTable().getName(),con);
				i++;
			}
			for (int j = 0; j < expectedamounts.length && correct==true; j++) {
				if(expectedamounts[j]!=realamounts[j]) correct=false;
			}
			assertTrue(correct);
		} catch (GeneratorException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedDataException e) {
			e.printStackTrace();
			fail();
		}catch (InstantException e) {
			e.printStackTrace();
			fail();
		} catch (WriterException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			fail();
		}


	}

	public void test3_fill(){
		try {
			int i=0;
			boolean correct=true;
			database= new DatabaseVO(getUrl3Concrete(),USER,PASSWORD, getDriverConcrete(), getEngineConcrete());
			Connection con = (Connection) ConnectionFactory.getInstance().getConnection(database.getInfo());
			tables = new ArrayList<TableVO>(Facade.getInstance().getDatabase(database.getInfo()).getTables().values());
			tasks= sorter.sortTables(tables);
			TableVO tab;
			//data generator assignment
			for(Task task: tasks){
				task.getTable().setInsertTimes(INSERTTIMES);
				tab = task.getTable();
				for(ColumnVO col: tab.getColumns().values()){
					if(col.getType().isDate()){
						gen = new DateNormalgenerator();
						col.setGenerator(gen);
						//standar desv
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.8"), "2");
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.9"), "2000");
						//format
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.10") + 
								MessageBundleManager.getString("DateNormalgenerator.11") + 
								MessageBundleManager.getString("DateNormalgenerator.12") , 
								"yyyy-MM-dd EE hh:mm:ss");
					}else if(col.getType().isInteger()){
						gen = new TelephoneDataGenerator();
						col.setGenerator(gen);
					}else if(col.getType().isDouble() ){
						gen = new NumberNormalGenerator();
						col.setGenerator(gen);
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.16"), "17000");
						//standard desv
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.15"), "10000");
						//decimal
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.17"), "2");
						//intengers
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.18"), "5");
						
					}else if(col.getType().isString()){
						gen = new NameGenerator();
						col.setGenerator(gen);
						//female percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.11"), "YES");
						//male percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.12"), "YES");
						//format
						String format = "Dear Ing. %FN %I %LN"; 
						if(col.getType().getRange()<30)
							format = "%FN ";
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.13")+"%FN"+", "
								+"%I"+MessageBundleManager.getString("NameGenerator.14")
								+"%LN"+MessageBundleManager.getString("NameGenerator.15"), 
								format);						
					}else if(col.getType().isFile()){
						gen = new FileGenerator();
						col.setGenerator(gen);
						// folder path
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.14"),"/home/felipe/Imágenes");
						//extension
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.15"),"gif");
					}
					col.getGenerator().doneConfiguration();
				}
			}
			expectedamounts= new long[tasks.size()];
			realamounts= new int[tasks.size()];
			rowgen.Fill(tasks);
			for(Task task: tasks){
				expectedamounts[i]=task.getTable().getInsertTimes();
				realamounts[i]=verifylength(task.getTable().getName(),con);
				i++;
			}
			for (int j = 0; j < expectedamounts.length && correct==true; j++) {
				if(expectedamounts[j]!=realamounts[j]) correct=false;
			}
			assertTrue(correct);
		} catch (GeneratorException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedDataException e) {
			e.printStackTrace();
			fail();
		}catch (InstantException e) {
			e.printStackTrace();
			fail();
		} catch (WriterException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			fail();
		}


	}

	public void test4_fill(){
		try {
			int i=0;
			boolean correct=true;
			database= new DatabaseVO(getUrl4Concrete(),USER,PASSWORD, getDriverConcrete(), getEngineConcrete());
			Connection con = (Connection) ConnectionFactory.getInstance().getConnection(database.getInfo());
			tables = new ArrayList<TableVO>(Facade.getInstance().getDatabase(database.getInfo()).getTables().values());
			tasks= sorter.sortTables(tables);
			TableVO tab;
			//data generator assignment
			for(Task task: tasks){
				task.getTable().setInsertTimes(INSERTTIMES);
				tab = task.getTable();
				for(ColumnVO col: tab.getColumns().values()){
					if(col.getType().isDate()){
						gen = new DateNormalgenerator();
						col.setGenerator(gen);
						//standar desv
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.8"), "2");
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.9"), "2000");
						//format
						gen.addAttributeValue(MessageBundleManager.getString("DateNormalgenerator.10") + 
								MessageBundleManager.getString("DateNormalgenerator.11") + 
								MessageBundleManager.getString("DateNormalgenerator.12") , 
								"yyyy-MM-dd EE hh:mm:ss");
					}else if(col.getType().isInteger() ){
						gen = new TelephoneDataGenerator();
						col.setGenerator(gen);
					}else if(col.getType().isDouble() ){
						gen = new NumberNormalGenerator();
						col.setGenerator(gen);
						//mean
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.16"), "17000");
						//standard desv
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.15"), "10000");
						//decimal
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.17"), "2");
						//intengers
						gen.addAttributeValue(MessageBundleManager.getString("NumberNormalGenerator.18"), "5");
						
					}else if(col.getType().isString()){
						gen = new NameGenerator();
						col.setGenerator(gen);
						//female percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.11"), "YES");
						//male percentage
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.12"), "YES");
						//format
						String format = "Dear Ing. %FN %I %LN"; 
						if(col.getType().getRange()<30)
							format = "%FN ";
						gen.addAttributeValue(MessageBundleManager.getString("NameGenerator.13")+"%FN"+", "
								+"%I"+MessageBundleManager.getString("NameGenerator.14")
								+"%LN"+MessageBundleManager.getString("NameGenerator.15"), 
								format);						
					}else if(col.getType().isFile()){
						gen = new FileGenerator();
						col.setGenerator(gen);
						// folder path
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.14"),"/home/felipe/Imágenes");
						//extension
						gen.addAttributeValue(MessageBundleManager.getString("FileGenerator.15"),"gif");
					}
					col.getGenerator().doneConfiguration();
				}
			}
			expectedamounts= new long[tasks.size()];
			realamounts= new int[tasks.size()];
			rowgen.Fill(tasks);
			for(Task task: tasks){
				expectedamounts[i]=task.getTable().getInsertTimes();
				realamounts[i]=verifylength(task.getTable().getName(),con);
				i++;
			}
			for (int j = 0; j < expectedamounts.length && correct==true; j++) {
				if(expectedamounts[j]!=realamounts[j]) correct=false;
			}
			assertTrue(correct);
		} catch (GeneratorException e) {
			e.printStackTrace();
			fail();
		} catch (ConnectionException e) {
			e.printStackTrace();
			fail();
		} catch (RetrieverException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedDataException e) {
			e.printStackTrace();
			fail();
		} catch (InstantException e) {
			e.printStackTrace();
			fail();
		} catch (WriterException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			fail();
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			fail();
		} 


	}

	private int verifylength(String name, Connection con) throws ConnectionException, GeneratorException {
		return (int) FillerDao.getInstance().rowsCount(name, con);
	}


}
