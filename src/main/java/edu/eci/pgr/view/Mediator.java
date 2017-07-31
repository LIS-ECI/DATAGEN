
package edu.eci.pgr.view;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.controller.BusinessFacade;
import edu.eci.pgr.components.ScannerComponent;
import edu.eci.pgr.components.ScannerComponent.InvalidAmountError;
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
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.sorting.Task;
import edu.eci.pgr.view.graph.GraphDrawer;
import edu.eci.pgr.view.swing.AboutUsPanel;
import edu.eci.pgr.view.swing.ApplicationNamePanel;
import edu.eci.pgr.view.swing.ColorChooser;
import edu.eci.pgr.view.swing.DataBaseInformation;
import edu.eci.pgr.view.swing.FileChooserFrame;
import edu.eci.pgr.view.swing.FileOptionsPanel;
import edu.eci.pgr.view.swing.FontColorChooser;
import edu.eci.pgr.view.swing.GeneratorAttributes;
import edu.eci.pgr.view.swing.GeneratorChooser;
import edu.eci.pgr.view.swing.InitialFrame;
import edu.eci.pgr.view.swing.ProgressFrame;
import edu.eci.pgr.view.swing.TablesRowInformation;
import edu.eci.pgr.view.swing.panel.GeneratorsPanel;


public class Mediator extends JFrame{
	private static final String NO_TABLES = MessageBundleManager.getString("Mediator.35");
	private static String NULL_PER = MessageBundleManager.getString("Mediator.0"); //$NON-NLS-1$
	private static String GEN = MessageBundleManager.getString("Mediator.1"); //$NON-NLS-1$
	private static String COL_NAME = MessageBundleManager.getString("Mediator.2"); //$NON-NLS-1$
	private static String VOL_DATA = MessageBundleManager.getString("Mediator.3"); //$NON-NLS-1$
	private static String STORED_DATA = MessageBundleManager.getString("Mediator.4"); //$NON-NLS-1$
	private static String IS_CONFG = MessageBundleManager.getString("Mediator.5"); //$NON-NLS-1$
	private static String TABLES_NAME = MessageBundleManager.getString("Mediator.6"); //$NON-NLS-1$
	private static String CLOSE = MessageBundleManager.getString("Mediator.7"); //$NON-NLS-1$
	private static String EXIT_MSG = MessageBundleManager.getString("Mediator.8"); //$NON-NLS-1$
	private static String LOADING_ERROR = MessageBundleManager.getString("Mediator.9"); //$NON-NLS-1$
	private static String SAVING_ERROR = MessageBundleManager.getString("Mediator.10"); //$NON-NLS-1$
	private static String SAVING = MessageBundleManager.getString("Mediator.11"); //$NON-NLS-1$
	private static String SAVE_SUCCESS = MessageBundleManager.getString("Mediator.12"); //$NON-NLS-1$
	private static String VOL_ASIG = MessageBundleManager.getString("Mediator.13"); //$NON-NLS-1$
	private static String PRINCIPAL = MessageBundleManager.getString("Mediator.14"); //$NON-NLS-1$
	private static String CONF_TITLE = MessageBundleManager.getString("Mediator.15"); //$NON-NLS-1$
	private static String PROGRESS_TITLE = MessageBundleManager.getString("Mediator.16"); //$NON-NLS-1$
	private static String ROW_MSG = MessageBundleManager.getString("Mediator.17"); //$NON-NLS-1$
	private static String ROW_ERROR = MessageBundleManager.getString("Mediator.18"); //$NON-NLS-1$
	private static String MODEL_ERROR = MessageBundleManager.getString("Mediator.19"); //$NON-NLS-1$
	private static String TABLE_CONFG_MSG = MessageBundleManager.getString("Mediator.20"); //$NON-NLS-1$
	private static String SAVE_PLAN = MessageBundleManager.getString("Mediator.21"); //$NON-NLS-1$
	private static String SAVE_MSG = MessageBundleManager.getString("Mediator.22"); //$NON-NLS-1$
	private static String CANCEL_ERROR = MessageBundleManager.getString("Mediator.23"); //$NON-NLS-1$
	private static String ATT_ERROR = MessageBundleManager.getString("Mediator.24"); //$NON-NLS-1$
	private static String VALUE_ERROR = MessageBundleManager.getString("Mediator.25"); //$NON-NLS-1$
	private static String GEN_ATT = MessageBundleManager.getString("Mediator.26"); //$NON-NLS-1$
	private static String GEN_ERROR = MessageBundleManager.getString("Mediator.27"); //$NON-NLS-1$
	private static String GENERATOR_X_COLUMNS = MessageBundleManager.getString("Mediator.28"); //$NON-NLS-1$
	private static String PER_NULL = MessageBundleManager.getString("Mediator.29"); //$NON-NLS-1$
	private static String MAX_ROWS = MessageBundleManager.getString("Mediator.30"); //$NON-NLS-1$
	private static String TABLE_COLUMNS = MessageBundleManager.getString("Mediator.31"); //$NON-NLS-1$
	private static String MER = MessageBundleManager.getString("Mediator.32"); //$NON-NLS-1$
	private static String CONFIGURATION_CONN = MessageBundleManager.getString("Mediator.33"); //$NON-NLS-1$
	private static String FILLING_ERROR = MessageBundleManager.getString("Mediator.34"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	private static final Color GRAY = new Color(235,235,235);

	public Color BACKGROUND_COLOR = GRAY;
	public Color FOREGROUND_COLOR = Color.BLACK;
	public static final String[] LANGUAGES = new String[]{"English","Espa�ol"}; //$NON-NLS-1$ //$NON-NLS-2$
	public static final Locale[] LOCALES = new Locale[]{new Locale("en"),new Locale("es")}; //$NON-NLS-1$ //$NON-NLS-2$

	private static Mediator instance;
	private MediatorPlanVO plan;
	private GeneratorAttributes generatorAttributes;
	private DataBaseInformation dataBaseInformation;
	private GraphDrawer drawer;
	private GeneratorChooser generatorChooser;
	private InitialFrame initialFrame;
	private ProgressFrame progress;
	private TablesRowInformation tablesRows;
	private FileChooserFrame fileChooser;
	private ApplicationNamePanel namePanel;
	private FileOptionsPanel fileOptionsPanel;
	private JPanel actualPanel;
	private AboutUsPanel aboutUs;
	private ColorChooser colorChooser;
	private JFrame aboutUsFrame;
	private Mediator(){
		this.setBackground(BACKGROUND_COLOR);
		this.getContentPane().setBackground(BACKGROUND_COLOR);
		this.setIconImage(new ImageIcon(ClassLoader.getSystemResource("logo.png")).getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("ECIDBDATAGEN"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Mediator getInstance(){
		if(instance == null)
			instance = new Mediator();
		return instance;
	}
	public Object[][] getTableList() {
		return plan.getTableList();
	}
	public int getTotalColumnCount() {
		return plan.getTotalColumnCount();
	}
	public Object[][] getColumnNames() {
		return plan.getColumnNames();
	}
	public String getColumnName(int i) {
		return plan.getColumnName(i);
	}
	public Object[] getGeneratorsFor(String columnName) {
		return plan.getGeneratorsFor(columnName);
	}
	public void setSelectedTable(String selectedValue) {
		plan.setSelectedTable(selectedValue);
	}

	public void goDataBaseInformation(){
		if(dataBaseInformation != null)
			this.getContentPane().remove(dataBaseInformation);
		dataBaseInformation = new DataBaseInformation();
		this.getContentPane().add(dataBaseInformation,"1,5"); //$NON-NLS-1$
		dataBaseInformation.setVisible(true);
		actualPanel = dataBaseInformation;
		fileOptionsPanel.setVisible(false);
		initialFrame.setVisible(false);
		this.setTitle(CONFIGURATION_CONN);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
	}
	/**
	 * used to connect from GUI
	 * @param username
	 * @param password
	 * @param url
	 * @param driver
	 * @param engine
	 */
	public void connectButtonAction(String username,String password,String url, String driver, 
			String engine, Boolean savePass){
		try{
			setLoadInformation(new InfoDatabaseVO(url,username,password,driver,engine));
			if(BusinessFacade.getInstance().hasTables()){
				actualPanel.setVisible(false);
				if(savePass) BusinessFacade.getInstance().savePassword(url+username, password);
				plan.setTableListInitialized(false);
				this.showDrawer();
			}else{
				JOptionPane.showMessageDialog(this, NO_TABLES);
			}
		} catch (InstantException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage());
		} catch (WriterException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage());
		} catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	/**
	 * used to connect from dbd
	 * @param database
	 */
	public void connectButtonAction(InfoDatabaseVO database) {
		try {
			setLoadInformationFromLoadPlan();
			actualPanel.setVisible(false);
			this.showDrawer();
		} catch (InstantException e) {
			e.printStackTrace();
		}
	}
	private void setLoadInformationFromLoadPlan() throws InstantException {
		plan.setTableNcolumnDetail();
		plan.validateExistingValues();
	}
	public void setLoadInformation(InfoDatabaseVO database) throws InstantException, ConnectionException, RetrieverException {
			BusinessFacade.getInstance().setDataBase(database);
			plan.setTableNcolumnDetail();
			plan.validateExistingValues();
	}

	private void showDrawer() {
		if(drawer != null)
			this.getContentPane().remove(drawer);
		drawer = new GraphDrawer();
		this.getContentPane().add(drawer,"1,5"); //$NON-NLS-1$
		drawer.setVisible(true);
		actualPanel.setVisible(false);
		actualPanel = drawer;
		fileOptionsPanel.setVisible(true);
		this.setTitle(MER);
		this.setSize(drawer.getSize());
		this.setLocationRelativeTo(null);
	}

	public void selectButtonAction(int i) {
		String selectedTable = plan.getTableName(i);
		selectButtonAction(selectedTable);
	}
	public void selectButtonAction(String name) {
		plan.setSelectedTable(name);
		drawer.setVisible(false);
		if(generatorChooser != null)
			this.getContentPane().remove(generatorChooser);
		generatorChooser = new GeneratorChooser();
		this.getContentPane().add(generatorChooser,"1,5"); //$NON-NLS-1$
		generatorChooser.setVisible(true);
		actualPanel = generatorChooser;
		fileOptionsPanel.setVisible(true);
		this.setTitle(TABLE_COLUMNS+name);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
	}

	public String[] getMaximumRowsForTable(String table){
		return BusinessFacade.getInstance().getMaximumRowsForTable(table);
	}
	public void rowsToGenerateAction(String rowsToGenerateSelectedTable){
		long insertTimes =Long.parseLong(rowsToGenerateSelectedTable); 
		long maxTimes =Long.parseLong(BusinessFacade.getInstance().getMaximumRowsForTable
				(plan.getSelectedTable())[0]);
		if(insertTimes>maxTimes){
			JOptionPane.showMessageDialog(this, MAX_ROWS+maxTimes); //$NON-NLS-1$
		}
	}
	public void doneConfigurationAction() {
		JTextField[] nullablePercents= generatorChooser.getJPanel().getNullablePercents();
		int response1 = 0;
		boolean flag = false;
		boolean done = true;
		if(response1 == 0){
			if(!flag){	
				Iterator<String> columns = plan.getTableNcolumnDetail().get(plan.getSelectedTable()).keySet().iterator();
				int j = 0;
				while(columns.hasNext() && done){
					if(BusinessFacade.getInstance().getGenerator(plan.getSelectedTable(), columns.next()) == null)
						done = false;
					j++;
				}
			}
			if(done){
				int response2=0;
				JTextField text;
				double value;
				for (int i = 0; i < nullablePercents.length && !flag; i++) {
					text=nullablePercents[i];
					if(!text.getText().equals(GeneratorsPanel.NA)){
						try{
							value=Double.parseDouble(text.getText());
							BusinessFacade.getInstance().setNullablePercentageColumnInTable(plan.getSelectedTable(),text.getName(), value);
							if(value>100 || value<0) throw new  NumberFormatException();
						}catch (NumberFormatException ne){
							JOptionPane.showMessageDialog(this,PER_NULL);
							response2 = 1;
						}catch(Exception e){
							JOptionPane.showMessageDialog(this,PER_NULL);
						}
					}
				}
				if(response2 == 0){
					plan.setDoneConfigurationSelectedTable();
					fromTableDetailToTableList();
				}
			}else{
				JOptionPane.showMessageDialog(this,GENERATOR_X_COLUMNS); //$NON-NLS-1$
			}
		}
	}

	private void fromTableDetailToTableList() {
		generatorChooser.setVisible(false);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
		showDrawer();
	}

	public String getRowsToGenerateSelectedTable(){
		return ""+BusinessFacade.getInstance().getInsertTimesSelectedTable(plan.getSelectedTable()); //$NON-NLS-1$
	}

	public String[] getAttributesOfGenerator() {
		return this.getActualGenerator().getAttributes();
	}
	/**
	 * @return the actualGenerator
	 */
	public DataGenerator getActualGenerator() {
		return BusinessFacade.getInstance().getActualGenerator();
	}

	public String getNNameAttribute(int j) {
		return getAttributesOfGenerator()[j];
	}

	public void setActualGeneratorName(String name, String selectedCol) {
		plan.setSelectedColumn(selectedCol);
		plan.setActualGeneratorName(name);
		try {
			BusinessFacade.getInstance().setGenerator(plan.getSelectedTable(), selectedCol, name);
		} catch (InstantException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),GEN_ERROR,JOptionPane.ERROR_MESSAGE);
		}
	}
	public String getActualGeneratorName() {
		return plan.getActualGeneratorName();
	}
	public void comboBoxAction() {
		if(BusinessFacade.getInstance().getActualGenerator().getAttributes().length>0){
			if(generatorAttributes != null)
				this.getContentPane().remove(generatorAttributes);
			generatorAttributes = new GeneratorAttributes(plan.getActualGeneratorName(), getSelectedColumn());
			this.getContentPane().add(generatorAttributes,"1,5"); //$NON-NLS-1$
			generatorAttributes.setVisible(true);
			actualPanel = generatorAttributes;
			fileOptionsPanel.setVisible(true);
			this.setTitle(GEN_ATT+plan.getActualGeneratorName());
			this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
			this.setLocationRelativeTo(null);
			this.generatorChooser.setVisible(false);
		}
	}

	public void okGenAttributesAction() {
		try {
			BusinessFacade.getInstance().setColumnAttributesInTable(plan.getSelectedTable(), plan.getSelectedColumn());
			fromGeneratorAttributesToTableDetailInformation();
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),VALUE_ERROR,JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),ATT_ERROR,JOptionPane.ERROR_MESSAGE);
		}
	}
	public void addGenAttributeAction(String attName, String attValue) {
		try {
			BusinessFacade.getInstance().addColumnAttributeInTable(plan.getSelectedTable(), plan.getSelectedColumn(), attName, attValue);
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),VALUE_ERROR,JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),ATT_ERROR,JOptionPane.ERROR_MESSAGE);
		}
	}
	private void fromGeneratorAttributesToTableDetailInformation() {
		generatorAttributes.setVisible(false);
		generatorChooser.setVisible(true);
		actualPanel = generatorChooser;
		fileOptionsPanel.setVisible(true);
		this.setTitle(TABLE_COLUMNS+plan.getSelectedTable());
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
	}
	public void cancelButtonAction() {
		try {
			BusinessFacade.getInstance().setGenerator(plan.getSelectedTable(), plan.getSelectedColumn(), null);
			generatorChooser.getJPanel().getGeneratorOptions().get(plan.getSelectedColumn()).
			setSelectedItem(MessageBundleManager.getString("SELECT_ONE_LABEL")); //$NON-NLS-1$
			generatorChooser.getJPanel().getGeneratorOptions().get(plan.getSelectedColumn()).setSelectedItem(
					MessageBundleManager.getString("SELECT_ONE_LABEL")); //$NON-NLS-1$
			fromGeneratorAttributesToTableDetailInformation();
		} catch (InstantException e) {
			JOptionPane.showMessageDialog(this,e.getMessage(),CANCEL_ERROR,JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public String getSelectedColumn() {
		return plan.getSelectedColumn();
	}
	public void generateDataButtonAction() {
		if(plan.getFileName() == null){
			int result = JOptionPane.showConfirmDialog(null, SAVE_MSG,SAVE_PLAN,JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
			if(result == JOptionPane.YES_OPTION){
				savePlanAction();
			}
		}
		boolean correct=true;
		for (int i = 0; i < plan.getTableNcolumnDetail().size() && correct; i++) {
			if(!plan.isTableConfigured(i)) correct=false;
		}
		if(!correct) JOptionPane.showMessageDialog(this, TABLE_CONFG_MSG
				,MODEL_ERROR,JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		else{
			ArrayList<InvalidAmountError> errors=
				(ArrayList<InvalidAmountError>) ScannerComponent.getInstance().verrifyInsertAmount(BusinessFacade.getInstance().getDatabase());
			if(errors.size()>0){
				String sErrors=""; //$NON-NLS-1$
				for(InvalidAmountError error: errors) sErrors+=error.getMessage();  
				JOptionPane.showMessageDialog(this, ROW_ERROR+sErrors //$NON-NLS-1$
						,ROW_MSG,JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}else{
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if(progress != null)
							instance.getContentPane().remove(progress);
						progress = new ProgressFrame();
						instance.getContentPane().add(progress,"1,5"); //$NON-NLS-1$
						BusinessFacade.getInstance().getControlProcces().addObserver(progress);
						progress.setVisible(true);
						actualPanel = progress;
						fileOptionsPanel.setVisible(true);
						fileOptionsPanel.deactivateButtons();
						instance.setTitle(PROGRESS_TITLE);
						tablesRows.setVisible(false);
						instance.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
						instance.setLocationRelativeTo(null);
					}
				});
				FillerThread fillThread= new FillerThread();
				fillThread.start();
			}
		}
	}
	/**
	 * @return the selectedTable
	 */
	public String getSelectedTable() {
		return plan.getSelectedTable();
	}
	/**
	 * @param selectedColumn the selectedColumn to set
	 */
	public void setSelectedColumn(String selectedColumn) {
		plan.setSelectedColumn(selectedColumn);
	}
	public boolean columnIsNull(String column) {
		return BusinessFacade.getInstance().columnIsNullable(plan.getSelectedTable(), column);
	}



	/**
	 * @return the progress
	 */
	public ProgressFrame getProgress() {
		return progress;
	}
	/**
	 * @param progress the progress to set
	 */
	public void setProgress(ProgressFrame progress) {
		this.progress = progress;
	}

	/**
	 * 
	 * @author felipe
	 * thread to launch progress frame  
	 */
	class FillerThread extends Thread{

		@Override
		public void run() {
			try {
				BusinessFacade.getInstance().generateData();
			} catch (ConnectionException e) {
				BusinessFacade.getInstance().addWarningMessage(e.getMessage());
				JOptionPane.showMessageDialog(instance,e.getMessage(),FILLING_ERROR,JOptionPane.ERROR_MESSAGE);
				fileOptionsPanel.activateButtons();
				e.printStackTrace();
			} catch (InstantException e) {
				BusinessFacade.getInstance().addWarningMessage(e.getMessage());
				JOptionPane.showMessageDialog(instance,e.getMessage(),FILLING_ERROR,JOptionPane.ERROR_MESSAGE);
				fileOptionsPanel.activateButtons();
				e.printStackTrace();
			} catch (GeneratorException e) {
				BusinessFacade.getInstance().addWarningMessage(e.getMessage());
				JOptionPane.showMessageDialog(instance,e.getMessage(),FILLING_ERROR,JOptionPane.ERROR_MESSAGE);
				fileOptionsPanel.activateButtons();
				e.printStackTrace();
			} catch (UnsupportedDataException e) {
				BusinessFacade.getInstance().addWarningMessage(e.getMessage());
				JOptionPane.showMessageDialog(instance,e.getMessage(),FILLING_ERROR,JOptionPane.ERROR_MESSAGE);
				fileOptionsPanel.activateButtons();
				e.printStackTrace();
			} catch (WriterException e) {
				BusinessFacade.getInstance().addWarningMessage(e.getMessage());
				JOptionPane.showMessageDialog(instance,e.getMessage(),FILLING_ERROR,JOptionPane.ERROR_MESSAGE);
				fileOptionsPanel.activateButtons();
				e.printStackTrace();
			}
		}
	}

	public void fromTableDetailToDatabase() {
		generatorChooser.setVisible(false);
		dataBaseInformation.setVisible(true);
		actualPanel = dataBaseInformation;
		fileOptionsPanel.setVisible(false);
		this.setTitle(CONF_TITLE);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
	}
	public void fromTableListToDatabaseInformation() {
		if(dataBaseInformation != null)
			this.getContentPane().remove(dataBaseInformation);
		dataBaseInformation = new DataBaseInformation();
		dataBaseInformation.setVisible(true);
		actualPanel = dataBaseInformation;
		fileOptionsPanel.setVisible(false);
		this.setTitle(CONF_TITLE);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
		this.getContentPane().add(dataBaseInformation,"1,5"); //$NON-NLS-1$
		drawer.setVisible(false);
	}

	public void fromDataBaseInformationToInitialFrame(){
		dataBaseInformation.setVisible(false);
		if(initialFrame != null)
			this.getContentPane().remove(initialFrame);
		initialFrame = new InitialFrame();
		initialFrame.setVisible(true);
		fileOptionsPanel.setVisible(false);
		this.setTitle(PRINCIPAL);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.getContentPane().add(initialFrame,"1,5"); //$NON-NLS-1$
		this.setLocationRelativeTo(null);
	}

	public void fromTableRowsToTableListInformation() {
		tablesRows.setVisible(false);
		showDrawer();
	}
	public void fromTableDetailToTablesList() {
		generatorChooser.setVisible(false);
		showDrawer();
	}
	public Object[][] getTableRowList() {
		Object [][]response = new Object[BusinessFacade.getInstance().getTablesThatNeedInsertionNumber().size()][2];
		List<String> values = BusinessFacade.getInstance().getTablesThatNeedInsertionNumber();
		Collections.sort(values);
		for(int i=0;i<values.size();i++){
			String tableName = values.get(i);
			response[i][0] = tableName;
			response[i][1] = ""+(BusinessFacade.getInstance().getInsertTimesSelectedTable(tableName)); //$NON-NLS-1$
		}
		return response;
	}
	public void fromTablesListToTablesRows() {
		if(tablesRows != null)
			this.getContentPane().remove(tablesRows);
		tablesRows = new TablesRowInformation();
		this.getContentPane().add(tablesRows,"1,5"); //$NON-NLS-1$
		tablesRows.setVisible(true);
		actualPanel = tablesRows;
		fileOptionsPanel.setVisible(true);
		this.setTitle(VOL_ASIG);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		drawer.setVisible(false);
		this.setLocationRelativeTo(null);
	}
	public void rowsToGenerateAction(String value, String tableName) {
		BusinessFacade.getInstance().setInsertTimeSelectedTable(tableName, Long.valueOf(value));
	}
	public String getSelectedIndexFor(String columnName) {
		String response = MessageBundleManager.getString("SELECT_ONE_LABEL"); //$NON-NLS-1$
		String generator = BusinessFacade.getInstance().getGenerator(plan.getSelectedTable(), columnName);
		if(generator != null)
			response = generator;
		return response;
	}
	public String getNullableValue(int j) {
		return ""+BusinessFacade.getInstance().getNullablePercentageColumnInTable(plan.getSelectedTable(),getColumnName(j)); //$NON-NLS-1$
	}
	public void loadPlanAction(JPanel panel) {
		if(panel != null)
			actualPanel = panel;
		fileChooser = new FileChooserFrame(FileChooserFrame.OPEN);
		fileChooser.setVisible(true);
	}

	public void savePlanAction() {
		if(plan.getFileName() == null){
			fileChooser = new FileChooserFrame(FileChooserFrame.SAVE);
			fileChooser.setVisible(true);
		}else{
			savePlan(plan.getFileName());
		}
	}
	public void newPlanAction() {
		actualPanel.setVisible(false);
		goDataBaseInformation();
	}

	public void initAplication(){
		this.selectLanguageAction(LANGUAGES[0]);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void savePlan(File file) {
		try {
			plan.setFileName(file);
			BusinessFacade.getInstance().savePlan(file.getPath(),plan);
			JOptionPane.showMessageDialog(this,SAVE_SUCCESS, SAVING,JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (WriterException e) {
			e.printStackTrace();
			plan.setFileName(null);
			JOptionPane.showMessageDialog(this,e.getMessage(),SAVING_ERROR,JOptionPane.ERROR_MESSAGE);
		}
	}
	public void loadPlan(File selectedFile) {
		try {
			plan = BusinessFacade.getInstance().loadPlan(selectedFile.getPath());
			plan.setFileName(selectedFile);
			connectButtonAction(BusinessFacade.getInstance().getDatabase().getInfo());
		} catch (LoaderException e) {
			e.printStackTrace();
			plan.setFileName(null);
			JOptionPane.showMessageDialog(this,e.getMessage(),LOADING_ERROR,JOptionPane.ERROR_MESSAGE);
		}
	}
	public String recoverPassword(String url) {
		try {
			return BusinessFacade.getInstance().recoverPassword(url);
		} catch (LoaderException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
		return null;
	}

	public int getTotalWidth(){
		int response = 0;
		for(int i=0;i<ViewConstants.TABLE_LIST_COLUMNS_WIDTHS.length;i++)
			response+=ViewConstants.TABLE_LIST_COLUMNS_WIDTHS[i];
		return response;
	}
	public boolean addAttribute(String attName, String value) {
		try {
			BusinessFacade.getInstance().addColumnAttributeInTable(plan.getSelectedTable(), plan.getSelectedColumn(),attName,value);
			return true;
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),VALUE_ERROR,JOptionPane.ERROR_MESSAGE);
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(),ATT_ERROR,JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	public String getUrl() {
		return BusinessFacade.getInstance().getServerAddress();
	}
	public String getPassword() {
		return BusinessFacade.getInstance().getPassword();
	}
	public String getUser() {
		return BusinessFacade.getInstance().getUser();
	}

	public String getEngine(){
		return BusinessFacade.getInstance().getEngine();
	}
	public void setUseStoredData(String tableName,boolean b) {
		BusinessFacade.getInstance().setUseStoredData(tableName,b);
	}
	public boolean hasStoredData(String tableName) {
		return BusinessFacade.getInstance().hasStoredData(tableName);
	}
	public boolean doesntNeedGenerators(String tableName){
		return BusinessFacade.getInstance().doesntNeedGenerators(tableName);
	}
	public void saveAsPlanAction() {
		fileChooser = new FileChooserFrame(FileChooserFrame.SAVE);
		fileChooser.setVisible(true);
	}
	public void activateButtons() {
		fileOptionsPanel.activateButtons();
	}
	public void closeAboutUsPanel() {
		aboutUsFrame.setVisible(false);
	}
	public void aboutUsAction() {
		aboutUsFrame.setVisible(true);
		aboutUsFrame.setSize(410, 520);
		aboutUsFrame.setLocationRelativeTo(this);
	}
	public void selectLanguageAction(String selectedValue) {
		setMessageBundle(selectedValue);
		TableLayout thisLayout = new TableLayout(new double[][] {{7.0, TableLayout.FILL, 7.0}, {7.0, 95, 0,50,0,TableLayout.FILL, 7.0}});
		thisLayout.setHGap(5);
		thisLayout.setVGap(5);
		getContentPane().setLayout(thisLayout);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				int value = JOptionPane.showConfirmDialog(null,EXIT_MSG,CLOSE,JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
				if(value ==JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		aboutUs = new AboutUsPanel();
		aboutUsFrame = new JFrame();
		aboutUsFrame.getContentPane().add(aboutUs);
		aboutUsFrame.setIconImage(new ImageIcon(ClassLoader.getSystemResource("logo.png")).getImage());
		aboutUsFrame.setTitle(MessageBundleManager.getString("AboutUsPanel.1")); //$NON-NLS-1$

		namePanel = new ApplicationNamePanel();
		this.getContentPane().add(namePanel,"1,1"); //$NON-NLS-1$
		fileOptionsPanel = new FileOptionsPanel();
		this.getContentPane().add(fileOptionsPanel,"1,3"); //$NON-NLS-1$
		fileOptionsPanel.setVisible(false);
		namePanel.setVisible(true);
		plan = new MediatorPlanVO();
		plan.setEngineMap(new HashMap<String, String>());
		for(int i=0;i<getDatabaseTemplates().length;i++)
			plan.getEngineMap().put(getDatabaseTemplates()[i],getDatabaseEngines()[i]);
		if(initialFrame != null)
			this.getContentPane().remove(initialFrame);
		initialFrame = new InitialFrame();
		this.getContentPane().add(initialFrame,"1,5"); //$NON-NLS-1$
		initialFrame.setVisible(true);
		actualPanel = initialFrame;
		this.setTitle(PRINCIPAL);
		this.setSize(new Dimension(ViewConstants.WIDTH,ViewConstants.HEIGHT));
		this.setLocationRelativeTo(null);
		this.setBackground(BACKGROUND_COLOR);
	}
	public void setMessageBundle(String selectedValue) {
		int pos = -1;
		for(int i=0;i<LANGUAGES.length && pos == -1;i++)
			if(LANGUAGES[i].equals(selectedValue))
				pos = i;
		if(pos != -1)
			MessageBundleManager.setBundleLocale(LOCALES[pos]);
		repaintFrame();
		
	}
	public void setBackGroundColor(){
		colorChooser = new ColorChooser();
		colorChooser.setVisible(true);
		colorChooser.setAlwaysOnTop(true);
		colorChooser.setLocation((ViewConstants.WIDTH-colorChooser.getWidth())/2,(ViewConstants.HEIGHT-colorChooser.getHeight())/2);
	}
	
	public void setBackGroundColor(Color color,JFrame frame){
		frame.setVisible(false);
		BACKGROUND_COLOR = color;
		repaintFrame();
	}

	private void repaintFrame(){
		this.getContentPane().setBackground(BACKGROUND_COLOR);
		for(int i=0;i<this.getContentPane().getComponentCount();i++){
			Component component = this.getContentPane().getComponent(i);
			if(component instanceof ViewPanel){
				((ViewPanel)component).repaintGUI();
			}
		}
		if(aboutUsFrame != null){
			aboutUsFrame.setBackground(BACKGROUND_COLOR);
			aboutUs.repaintGUI();
		}
	}
	public List<Task> getTasks() {
		return BusinessFacade.getInstance().getTasks();
	}
	public void setFontColor(Color color, FontColorChooser fontColorChooser) {
		fontColorChooser.setVisible(false);
		FOREGROUND_COLOR = color;
	}
	public String[] getTableListColumns() {
		return new String[]{TABLES_NAME,IS_CONFG,STORED_DATA};
	}
	public String[] getTablesRowColumns() {
		return new String[]{TABLES_NAME,VOL_DATA};
	}
	public String[] getTableDetailColumns() {
		return new String[]{COL_NAME,GEN,NULL_PER};
	}
	
	public String[] getDatabaseTemplates(){
		return ViewConstants.getDatabaseTemplates();
	}
	public String[] getDatabaseEngines(){
		return ViewConstants.getDatabaseEngines();
	}
	public String[] getDatabaseUrlConnection(){
		return ViewConstants.getDatabaseUrlConnection();
	}
	public String[] getDatabaseDriverClassName(){
		return ViewConstants.getDatabaseDriverClassName();
	}
}