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

import edu.eci.pgr.business.InfoDatabaseVO;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.i18n.MessageBundleManager;



public class EmbededDataBaseConnectionLoader {
    
    private static final String ERROR = MessageBundleManager.getString("EmbededDataBaseConnectionLoader.0"); //$NON-NLS-1$
	private static final String NOT_FOUND = MessageBundleManager.getString("EmbededDataBaseConnectionLoader.1"); //$NON-NLS-1$
	private static final String PROP_FILE = MessageBundleManager.getString("EmbededDataBaseConnectionLoader.2"); //$NON-NLS-1$
	private static EmbededDataBaseConnectionLoader instance;
	private final String CONFIGURATION_FILE="derbyConnectionCommands.properties"; //$NON-NLS-1$
    private String configFilesPath;
    private File propsfile;
	private Properties properties;
	private HashMap<String,String>map;
	private static final String USER_NAME = "userName"; //$NON-NLS-1$
	private static final String PASSWORD = "password"; //$NON-NLS-1$
	private static final String URL = "connectionUrl"; //$NON-NLS-1$
	private static final String DRIVER = "driver"; //$NON-NLS-1$
	private static final String ENGINE = "engine"; //$NON-NLS-1$
    
    public static EmbededDataBaseConnectionLoader getInstance() throws InstantException{
        if(instance==null){
            instance=new EmbededDataBaseConnectionLoader();
        }
        return instance;
    }

    /**
     * constructor
     * @throws InstantException
     */
    private EmbededDataBaseConnectionLoader() throws InstantException{
    		map = new HashMap<String, String>();
    		URL url=DaoProperties.class.getResource(CONFIGURATION_FILE);
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
    public InfoDatabaseVO getConnectionInformation(){
    	InfoDatabaseVO info = new InfoDatabaseVO();
    	info.setDriver(map.get(DRIVER));
    	info.setEngine(map.get(ENGINE));
    	info.setPassword(map.get(PASSWORD));
    	info.setUrl(map.get(URL));
    	info.setUsername(map.get(USER_NAME));
    	return info;
    }
}
