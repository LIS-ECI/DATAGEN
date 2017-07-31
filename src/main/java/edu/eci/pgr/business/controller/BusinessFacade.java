package edu.eci.pgr.business.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.components.ControlProcessComponent;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.LoaderException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.persistence.ConnectionFactory;
import edu.eci.pgr.persistence.PlanDAO;
import edu.eci.pgr.persistence.PlanVO;
import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.dao.FillerDao;
import edu.eci.pgr.sorting.Task;
import edu.eci.pgr.view.Mediator;
import edu.eci.pgr.view.MediatorPlanVO;
import edu.eci.pgr.view.ViewConstants;

/**
 * The <code> BusinessFacade</code> class is the logic controller
 * This class centralizes all known components of the logic layer 
 * that make calls to {@link Mediator}.
 * the class is a static class (solitaire) because all components in
 * the logic layer must be see this.
 * on the on the <code> BusinessFacade</code> are the method calls 
 * of the business and logic layers to be run as a result of the 
 * actions make in the GUI.
 * Also this class creates the plan to be saved for configurations.
 * 
 * @author Beatriz Rojas
 * @author Felipe Villamil
 * @date 21/04/2009
 * @version 1.0
 * @class BusinessFacade
 * @project MetaDataBrowsing
 * @company Escuela Colombiana de Ingenieria 
 * 
 */
public class BusinessFacade {

	private static final String FILELOG = ViewConstants.FILELOG;
	private static BusinessFacade instance;
	private BusinessFacadePlanVO plan;
	private ControlProcessComponent controlProccess;

	private BusinessFacade(){
		plan = new BusinessFacadePlanVO();
		controlProccess = new ControlProcessComponent();
	}
	public static BusinessFacade getInstance(){
		if(instance == null)
			instance = new BusinessFacade();
		return instance;
	}
	
	public void setDataBase(String username,String password,String url,String driver,String engine) throws ConnectionException, RetrieverException, InstantException{
		plan.setDatabase(new InfoDatabaseVO(url,username,password,driver,engine));
	}

	public void setDataBase(InfoDatabaseVO database) throws ConnectionException, RetrieverException, InstantException{
		plan.setDatabase(database);
	}

	public HashMap<String, HashMap<String, Object[]>> getTableNColumnDetail() throws InstantException {
		return plan.getTableNColumnDetail();
	}
	public DataGenerator getGeneratorPerName(String name) throws InstantException{
		return	plan.getGeneratorPerName(name);
	}

	public void setInsertTimeSelectedTable(String tableName,long insertTimes){
		plan.setInsertTimes(tableName,insertTimes);
	}

	public long getInsertTimesSelectedTable(String tableName){
		return plan.getInsertTimesSelectedTable(tableName);
	}
	public void setNullablePercentageColumnInTable(String tableName,String columnName,double nullablepercentage){
		plan.setNullablePercentageColumnInTable(tableName,columnName,nullablepercentage);
	}
	public double getNullablePercentageColumnInTable(String tableName,String columnName){
		return plan.getNullablePercentageColumnInTable(tableName,columnName);
	}

	public void setColumnAttributesInTable(String tableName,String columnName) throws UnsupportedAttributeValueException, UnsupportedAttribute{
		plan.setColumnAttributesInTable(tableName, columnName);
	}
	public void addColumnAttributeInTable(String tableName,String columnName,String attname,String attvalue) throws UnsupportedAttributeValueException, UnsupportedAttribute{
		plan.addColumnAttributeInTable(tableName, columnName, attname, attvalue);
	}

	public void setGenerator(String tableName,String columnName,String datagenname) throws InstantException{
		plan.setGenerator(tableName, columnName, datagenname);
	}

	public String getGenerator(String tableName,String columnName){
		return plan.getGenerator(tableName, columnName);
	}

	/**
	 * this method returns the maximum amount to can be assigned to some
	 * {@link TableVO}, and detail information.   
	 * 
	 * @param tableName
	 * 				the name of the table
	 * @return the list with the maximum value and detail information
	 * 			the maximum value is in the 0(zero) position, the 
	 * 			amount is in the 1st position, <code>amount</code> 
	 * 			is the reason of the amount:  
	 * 			<p><b>Reason 1: Reference amount</b>
	 * 			<p><b>Reason 2: Generator amount</b>
	 * 			<p><b>Reason 3: User store data amount</b>
	 * 			<p> the 2nd position is the cause. for example:
	 * 			in the reason 1 or 3 the cause can be the referenced 
	 * 			tables and columns, or in the reason 2 the cause can
	 * 			be the name generator.   
	 */
	public String[] getMaximumRowsForTable(String tableName){
		return plan.getMaximumRowsForTable(tableName);
	}
	public void generateData() throws ConnectionException, InstantException, GeneratorException, UnsupportedDataException, WriterException{
		plan.generateData();
	}
	/**
	 * @return the actualGenerator
	 */
	public DataGenerator getActualGenerator() {
		return plan.getActualGenerator();
	}
	/**
	 * @param actualGenerator
	 *            the actualGenerator to set
	 */
	public void setActualGenerator(DataGenerator actualGenerator) {
		plan.setActualGenerator(actualGenerator);
	}
	public void setActualGeneratorPerName(String datagen) throws InstantException {
		this.setActualGenerator(this.getGeneratorPerName(datagen));
	}
	/**
	 * @return the database
	 */
	public DatabaseVO getDatabase() {
		return plan.getDatabase();
	}
	public boolean columnIsNullable(String tableName,String columnName) {
		return plan.columnIsNullable(tableName, columnName);
	}
	/**
	 * @return the filler
	 */
	public RowGenerator getFiller() {
		return plan.getFiller();
	}
	/**
	 * @param filler
	 *            the filler to set
	 */
	public void setFiller(RowGenerator filler) {
		plan.setFiller(filler);
	}

	public void addWarningMessage(String mess){
		controlProccess.addMessages(mess);
	}

	public void initialiceProgressComponent(int val) {
		controlProccess.setAmount(val);
	}

	public void addToProcessComponent(double add){
		controlProccess.addToProcess(add);
	}
	/**
	 * @return the controlProcces
	 */
	public ControlProcessComponent getControlProcces() {
		return controlProccess;
	}
	/**
	 * @param controlProcces
	 *            the controlProcces to set
	 */
	public void setControlProcces(ControlProcessComponent controlProcces) {
		controlProccess = controlProcces;
	}
	public void savePlan(String path, MediatorPlanVO plan2) throws  WriterException {
		PlanVO plansave = new PlanVO(this.plan,plan2);
		PlanDAO.getInstance().savePlan(path,plansave);
	}

	public MediatorPlanVO loadPlan(String path) throws LoaderException {
		PlanVO plan = PlanDAO.getInstance().loadPlan(path);
		this.plan = plan.getBfplan();
		return plan.getMplan();
	}
	public void exportFileProcess() throws IOException {
		controlProccess.exportToFile(FILELOG);
	}
	public void savePassword(String url, String password) throws WriterException {
		PlanDAO.getInstance().savePassword(password, url);
	}
	public String recoverPassword(String url) throws LoaderException {
		return PlanDAO.getInstance().recoverPassword(url);
	}
	public List<Task> getTasks(){
		return plan.getTasks();
	}
	public String getServerAddress() {
		if(plan != null && plan.getDatabase()!= null && 
				plan.getDatabase().getInfo() != null && plan.getDatabase().getInfo().getUrl()!= null 
				&& !plan.getDatabase().getInfo().getUrl().equals("")){ //$NON-NLS-1$
			System.out.println(plan.getDatabase().getInfo().getUrl());
			return plan.getDatabase().getInfo().getUrl();
		}
		else 
			return null;
	}
	public String getPassword() {
		if(plan != null && plan.getDatabase()!= null && 
				plan.getDatabase().getInfo() != null && plan.getDatabase().getInfo().getPassword()!= null 
				&& !plan.getDatabase().getInfo().getPassword().equals("")){ //$NON-NLS-1$
			return plan.getDatabase().getInfo().getPassword();
		}
		else 
			return null;
	}
	public String getUser() {
		if(plan != null && plan.getDatabase()!= null && 
				plan.getDatabase().getInfo() != null && plan.getDatabase().getInfo().getUsername()!= null 
				&& !plan.getDatabase().getInfo().getUsername().equals("")){ //$NON-NLS-1$
			System.out.println(plan.getDatabase().getInfo().getUsername());
			return plan.getDatabase().getInfo().getUsername();
		}
		else 
			return null;
	}
	public String getEngine() {
		if(plan != null && plan.getDatabase()!= null && 
				plan.getDatabase().getInfo() != null && plan.getDatabase().getInfo().getEngine()!= null 
				&& !plan.getDatabase().getInfo().getEngine().equals("")){ //$NON-NLS-1$
			return plan.getDatabase().getInfo().getEngine();
		}
		else 
			return null;
	}
	public void setUseStoredData(String table, boolean b) {
		plan.setUseStoredData(table, b);
	}
	public boolean hasStoredData(String tableName) {
		try {
			return (FillerDao.getInstance().rowsCount(tableName, ConnectionFactory.getInstance().getConnection(plan.getDatabase().getInfo()))>0);
		} catch (GeneratorException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<String> getTablesThatNeedInsertionNumber() {
		return plan.getTablesThatNeedInsertionNumber();
	}
	public boolean doesntNeedGenerators(String tableName) {
		return plan.doesntNeedGenerators(tableName);
	}
	public void showGenerators() {
		plan.showGenerators();
	}
	public boolean hasTables() {
		return plan.hasTables();
	}
}
