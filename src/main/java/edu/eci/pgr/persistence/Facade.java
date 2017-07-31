package edu.eci.pgr.persistence;
//=======================================================================
//FILE Broker
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Defines a Facade between the persistent layer and the business logic layer
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.business.controller.BusinessFacade;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.InterfaceLoaderException;
import edu.eci.pgr.exceptions.LoaderException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.properties.DaoProperties;
import edu.eci.pgr.properties.SortingProperties;



public class Facade {
	private static final String FACADE_ERROR = MessageBundleManager.getString("Facade.0"); //$NON-NLS-1$
	//-----------------------------------------------------------------------
	// INSTANCE ATRIBUTES
	//-----------------------------------------------------------------------
	/** Defines the Broker as a singleton*/
	private static Facade instance;
	private InformationRetriever retriever;
	//-----------------------------------------------------------------------
	//CONSTRUCTOR METHODS
	//-----------------------------------------------------------------------
	/** 
	 * Creates a new Broker
	 * @pre No instance has been created 
	 * @pos A new instance is created
	 */
	private Facade(){

	}
	//-----------------------------------------------------------------------
	// CLASS METHODS
	//-----------------------------------------------------------------------
	/** 
	 * Returns the singleton of the class
	 * @pre True
	 * @return the only instance of InformationRetreiverFactory
	 */
	public static Facade getInstance(){
		if(instance == null)
			instance = new Facade();
		return instance;
	}
	//-----------------------------------------------------------------------
	// INSTANCE METHODS
	//-----------------------------------------------------------------------

	public DatabaseVO getDatabase() throws InstantException, ConnectionException, RetrieverException{
		retriever = InformationRetrieverFactory.getInstance().getRetriever();
		List<TableVO> tables = new ArrayList<TableVO>();
		for(TableVO tab: retriever.getTableVOs()){
			tables.add(tab);
			retriever.getColumns(tab.getName());
			retriever.getPrimaryKeys(tab.getName());
			retriever.getForeignKeys(tab.getName());
		}
		return retriever.getDatabase();
	}

	public DatabaseVO getDatabase(InfoDatabaseVO database) throws RetrieverException, ConnectionException, InstantException{
		retriever = InformationRetrieverFactory.getInstance().getRetriever(database);
		for(TableVO tab: retriever.getTableVOs()){
			retriever.getColumns(tab.getName());
			retriever.getPrimaryKeys(tab.getName());
			retriever.getForeignKeys(tab.getName());
		}
		return retriever.getDatabase();
	}

	public LinkedList<String> LoadBddata() throws InterfaceLoaderException{
		LinkedList<String> properties= new LinkedList<String>();
		try{
			properties.add(DaoProperties.URL, BusinessFacade.getInstance().getDatabase().getInfo().getUrl());
			properties.add(DaoProperties.USER, BusinessFacade.getInstance().getDatabase().getInfo().getUsername());
			properties.add(DaoProperties.PASSWORD, BusinessFacade.getInstance().getDatabase().getInfo().getPassword());
			properties.add(DaoProperties.DRIVER, BusinessFacade.getInstance().getDatabase().getInfo().getDriver());
			properties.add(DaoProperties.ENGINE, BusinessFacade.getInstance().getDatabase().getInfo().getEngine());
		}
		catch(Exception e){
			throw new InterfaceLoaderException(FACADE_ERROR,e);
		}
		return properties;
	}

	public LinkedList<String> LoadSortingData() throws LoaderException{
		return SortingProperties.getInstance().LoadSortingConfig();
	}
	
	public LinkedList<String> loadBdDataFromConfigurationFile() throws LoaderException{
		return DaoProperties.getInstance().LoadBdConfig();
	}
}
