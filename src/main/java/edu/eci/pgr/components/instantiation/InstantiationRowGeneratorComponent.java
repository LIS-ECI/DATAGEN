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
import java.util.Properties;
import java.util.Set;

import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.properties.DaoProperties;
import edu.eci.pgr.rowGenerators.RowGenerator;



public class InstantiationRowGeneratorComponent {
    
    private static InstantiationRowGeneratorComponent instance;
	private final String CONFIGURATION_FILE="rowgeneratorcommands.properties";
    private Hashtable<String, String> classes;	
    private String configFilesPath;
    private File propsfile;
    private Hashtable<String,Object> cache;
  
    
    
    public static InstantiationRowGeneratorComponent getInstance() throws InstantException{
        
        if(instance==null){
            instance=new InstantiationRowGeneratorComponent();
        }
        
        return instance;
    }

    /**
     * constructor
     * @throws InstantException
     */
    private InstantiationRowGeneratorComponent() throws InstantException{
    		URL url=DaoProperties.class.getResource(CONFIGURATION_FILE);
    		if(url!=null){
    			configFilesPath=url.getPath().replaceAll("%20"," ");
    			if(configFilesPath.contains("!"))
    				configFilesPath = CONFIGURATION_FILE;
    		}
            propsfile=new File(configFilesPath);
			classes=new Hashtable<String,String>();
            cache=new Hashtable<String, Object>();
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
			obj=cache.get(key);
			if(obj==null){
				concreteClass = Class.forName(classname);
	            Constructor<?> classConstructor=concreteClass.getConstructor(new Class<?>[]{});
	            obj=classConstructor.newInstance(new Object[]{});
	            cache.put(key, obj);
			}
            return obj;
        } catch (ClassNotFoundException e) {
            throw new InstantException(e.getMessage()); 
        } catch (NoSuchMethodException e) {
            throw new InstantException(e.getMessage()); 
        } catch (InvocationTargetException e) {
            throw new InstantException(e.getMessage()); 
        } catch (IllegalAccessException e) {
             throw new InstantException(e.getMessage()); 
        } catch (InstantiationException e) {
            throw new InstantException(e.getMessage()); 
        } catch (ClassCastException e){
            throw new InstantException(e.getMessage()); 
        }
	
	}
	public RowGenerator getRowGeneratorForEngine(String name) throws InstantException{
		return (RowGenerator) InstantiateObject(name);
	}
}
