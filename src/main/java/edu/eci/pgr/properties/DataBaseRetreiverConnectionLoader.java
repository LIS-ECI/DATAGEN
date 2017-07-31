package edu.eci.pgr.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.i18n.MessageBundleManager;



public class DataBaseRetreiverConnectionLoader {
    
    private static final String ERROR = MessageBundleManager.getString("DataBaseRetreiverConnectionLoader.0"); //$NON-NLS-1$
	private static final String NOT_FOUND = MessageBundleManager.getString("DataBaseRetreiverConnectionLoader.1"); //$NON-NLS-1$
	private static final String PROP_FILE = MessageBundleManager.getString("DataBaseRetreiverConnectionLoader.2"); //$NON-NLS-1$
	private static DataBaseRetreiverConnectionLoader instance;
	private final String CONFIGURATION_FILE="databaseretrievercommands.properties"; //$NON-NLS-1$
    private String configFilesPath;
    private File propsfile;
	private Properties properties;
	private HashMap<String,String>map;
	private static final String USER_NAME = "userName"; //$NON-NLS-1$
	private static final String PASSWORD = "password"; //$NON-NLS-1$
	private static final String URL = "connectionUrl"; //$NON-NLS-1$
	private static final String DRIVER = "driver"; //$NON-NLS-1$
	private static final String ENGINE = "engine"; //$NON-NLS-1$
    
    
    public static DataBaseRetreiverConnectionLoader getInstance() throws InstantException{
        if(instance==null){
            instance=new DataBaseRetreiverConnectionLoader();
        }
        return instance;
    }

    /**
     * constructor
     * @throws InstantException
     */
    private DataBaseRetreiverConnectionLoader() throws InstantException{
    		URL url=DaoProperties.class.getClassLoader().getResource(CONFIGURATION_FILE);
    		if(url!=null){
    			configFilesPath=url.getPath().replaceAll("%20"," "); //$NON-NLS-1$ //$NON-NLS-2$
    			if(configFilesPath.contains("!")) //$NON-NLS-1$
    				configFilesPath = CONFIGURATION_FILE;
    		}
            propsfile=new File(configFilesPath);
    		properties = new Properties();
			try {
    			properties.load(new FileInputStream(propsfile));
    			Set<Object> actk=properties.keySet();
    			Iterator<Object> itk=actk.iterator();
    			 while (itk.hasNext()){
    	                String key=(String)itk.next();                
    	                map.put(key,properties.getProperty(key));
    	         }
    		} catch (FileNotFoundException e) {
    			throw new InstantException(PROP_FILE+configFilesPath+NOT_FOUND,e);
    		} catch (IOException e) {
    			throw new InstantException(ERROR+configFilesPath,e);
    		}

    }
    
    public String getUserName(){
    	return map.get(USER_NAME);
    }
    
    public String getPassword(){
    	return map.get(PASSWORD);
    }
	
    public String getConnectionUrl(){
    	return map.get(URL);
    }
    
    public String getDriver(){
    	return map.get(DRIVER);
    }
	
    public String getEngine(){
    	return map.get(ENGINE);
    }
}
