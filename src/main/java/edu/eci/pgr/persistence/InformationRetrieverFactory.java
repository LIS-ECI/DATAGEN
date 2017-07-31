package edu.eci.pgr.persistence;
//=======================================================================
//FILE InformationRetreiverFactory
//CREATE DATE: August 2008
//AUTHORS: Beatriz Rojas, Javier Villamil
//Creates the appropriate InformationRetreiver 
//=======================================================================
//=======================================================================
//REQUERIED LIBRARIES
//=======================================================================

import edu.eci.pgr.components.instantiation.InstantiationInformationRetreiverComponent;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
//=======================================================================
//CLASS InformationRetreiverFactory.java
//=======================================================================
public class InformationRetrieverFactory {

//-----------------------------------------------------------------------
// INSTANCE ATRIBUTES
//-----------------------------------------------------------------------
	/** Defines the InformationRetreiverFactory as a singleton*/
	private static InformationRetrieverFactory instance;
//-----------------------------------------------------------------------
//CONSTRUCTOR METHODS
//-----------------------------------------------------------------------
	/** 
	 * Creates a new InformationRetreiverFactory
	 * @pre No instance has been created 
	 * @pos A new instance is created
	 */
	private InformationRetrieverFactory(){
	}
//-----------------------------------------------------------------------
// CLASS METHODS
//-----------------------------------------------------------------------
	/** 
	* Returns the singleton of the class
	* @pre True
	* @return the only instance of InformationRetreiverFactory
	*/
	public static InformationRetrieverFactory getInstance(){
		if(instance == null)
			instance = new InformationRetrieverFactory();
		return instance;
	}
//-----------------------------------------------------------------------
// INSTANCE METHODS
//-----------------------------------------------------------------------
	
	/**
	 * Returns the new InformationRetreiver requested
	 * @pre The only instance of InformationRetreiver has been created
	 * @param key , key of the InformationRetreiver requested
	 * @param database, the database value object
	 * @return the appropiate InformationRetreiver 
	 * @throws ConnectionException 
	 * @throws RetrieverException 
	 * @throws RetrieverException 
	 */
	public InformationRetriever getRetriever(edu.eci.pgr.business.InfoDatabaseVO database) throws ConnectionException, InstantException, RetrieverException{
		InformationRetriever retriever = (InformationRetriever) InstantiationInformationRetreiverComponent.getInstance().InstantiateObject(database.getEngine()) ;
		retriever.setDataBaseMetadata(database);
		return retriever;
	}
	
	/**
	 * @return the information retriever associated with engine in {@link ConnectionFactory}
	 * @throws InstantException
	 * @throws ConnectionException 
	 * @throws RetrieverException 
	 */
	public InformationRetriever getRetriever() throws InstantException, ConnectionException, RetrieverException {
		return (InformationRetriever) InstantiationInformationRetreiverComponent.getInstance().InstantiateObject(ConnectionFactory.getInstance().getInfoDatabaseVO().getEngine());
	}
}
