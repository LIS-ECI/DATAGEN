package edu.eci.pgr.components.instantiation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

import edu.eci.pgr.business.controller.BusinessFacade;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.properties.DaoProperties;



/*
 * this file has been created by Beatriz Rojas and Felipe Villamil.
 * this file is a component of MetaDatBrowsing  
 */
/** 
 * this component instantiates a {@link DataGenerator}, from the configuration file
 * in the table of <code>classes</code> are stored the data Generators that are
 * instantiated, this classes also are saved in a cache container that is called
 * <code> cache. 
 *  
 * @author Beatriz Rojas
 * @author Felipe Villamil
 * @date 30/03/2009 
 * @version 1.0
 * @class InstantiationDataGeneratorComponent
 * @project MetaDataBrowsing
 * @company Escuela Colombiana de Ingenieria 
 * 
 * @see BusinessFacade
 * @see DataGenerator
 */
public class InstantiationDataGeneratorComponent {
    
    private static InstantiationDataGeneratorComponent instance;
	private final String CONFIGURATION_FILE="datageneratorcommands.properties"; 
    private Hashtable<String, String> classes;	
    private String configFilesPath;
    private File propsfile;
  
    
    
    public static InstantiationDataGeneratorComponent getInstance() throws InstantException{
        
        if(instance==null){
            instance=new InstantiationDataGeneratorComponent();
        }
        
        return instance;
    }

    /**
     * constructor
     * @throws InstantException
     */
    private InstantiationDataGeneratorComponent() throws InstantException{
    		URL url=DaoProperties.class.getResource(CONFIGURATION_FILE);
    		if(url!=null){
    			configFilesPath=url.getPath().replaceAll("%20"," "); 
    			if(configFilesPath.contains("!"))
    				configFilesPath = CONFIGURATION_FILE;
    		}
            propsfile=new File(configFilesPath);
			classes=new Hashtable<String,String>();
            loadClasses(classes);
    }
	
	
	/**
	 * load properties in properties hash table
	 * @param map
	 * @throws InstantException
	 */
	private void loadClasses(Hashtable<String, String> map) throws InstantException {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propsfile));
			Set<Object> actk=properties.keySet();
			Iterator<Object> itk=actk.iterator();
			 while (itk.hasNext()){
	                String key=(String)itk.next();                
	                map.put(key,properties.getProperty(key));
	         }
		} catch (FileNotFoundException e) {
			throw new InstantException(e.getMessage()); 
		} catch (IOException e) {
			throw new InstantException(e.getMessage()); 
		}
	}


	public Object InstantiateObject(String key) throws InstantException {
		Class<?> concreteClass;
		String classname=null;
		Object obj;
		try {
			//Se captura el nombre de la clase que se va a instanciar
			boolean is=classes.containsKey(key);
			if(is)classname=classes.get(key);
			else classname=key;
			/*
			 * verifica si una instancia esta en cache, y si esta la retorna
			 * si la instancia no esta, la crea y la pone en cache
			 */
			concreteClass = Class.forName(classname);
            Constructor<?> classConstructor=concreteClass.getConstructor(new Class<?>[]{});
            obj=classConstructor.newInstance(new Object[]{});
            return obj;
        } catch (ClassNotFoundException e) {
            throw new InstantException(e.getMessage(),e); 
        } catch (NoSuchMethodException e) {
            throw new InstantException(e.getMessage(),e); 
        } catch (InvocationTargetException e) {
            throw new InstantException(e.getMessage(),e); 
        } catch (IllegalAccessException e) {
            throw new InstantException(e.getMessage(),e); 
        } catch (InstantiationException e) {
            throw new InstantException(e.getMessage(),e); 
        } catch (ClassCastException e){
            throw new InstantException(e.getMessage(),e); 
        }
	
	}
	public LinkedList<String> getDataGeneratorsAvailable(){
		LinkedList<String> response = new LinkedList<String>();
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propsfile));
			Set<Object> actk=properties.keySet();
			Iterator<Object> itk=actk.iterator();
			while (itk.hasNext()){
				String key=(String)itk.next();                
				response.add(key);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public DataGenerator getGeneratorPerName(String name) throws InstantException{
		return (DataGenerator) InstantiateObject(name);
	}
}
