package edu.eci.pgr.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;





/*
 * this file has been created by Beatriz Rojas and Felipe Villamil.
 * this file is a component of MetaDatBrowsing  
 */
/**
 * this class centralize a messages used in the
 * application, each message that is deployed in the GUI
 * is referenced in the file and is managed in this class.
 *    
 * @author Beatriz Rojas
 * @author Felipe Villamil
 * @date 30/03/2009 
 * @version 0.5.0 development 
 * @class MessageBundleManager
 * @project MetaDataBrowsing
 * @company Escuela Colombiana de Ingenieria Julio Garavito  
 */

public class MessageBundleManager {
	/** the message bundle name*/
	private static final String BUNDLE_NAME = "edu.eci.pgr.i18n.message"; //$NON-NLS-1$

	/** the resource bundle*/
	private static ResourceBundle RESOURCE_BUNDLE;
	
	private MessageBundleManager(){}
	/**
	 * this method returns a message for the given key referenced
	 * in the bundle
	 * 
	 * @param key
	 * 		the key of message
	 * @return the message associated to the key
	 */
	public static String getString(String key) {
		try {
			if(RESOURCE_BUNDLE == null)
				RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,new Locale("en"));
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static void setBundleLocale(Locale currentLocale){
            //
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,currentLocale);
	}

}
