package edu.eci.pgr.properties;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Properties;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.eci.pgr.exceptions.LoaderException;
import edu.eci.pgr.i18n.MessageBundleManager;



public class DaoProperties {
		private static final String EXCEPTION = MessageBundleManager.getString("DaoProperties.0"); //$NON-NLS-1$
		private static final String FILE_NOT_FOUND = MessageBundleManager.getString("DaoProperties.1"); //$NON-NLS-1$
		public static final String DATABASEFILE="databaseMySql.xml"; //$NON-NLS-1$
		public static final int URL=0;
		public static final int USER=1;
		public static final int PASSWORD=2;
		public static final int DRIVER=3;
		public static final int ENGINE=4;
		private static DaoProperties instance;
		private XStream x;
		
		/**
		 * @return the instance
		 */
		public static DaoProperties getInstance() {
			if(instance==null){
				instance=new DaoProperties();
			}
			return instance;
		}
		
		public DaoProperties(){
			x=new XStream(new DomDriver());
		}
		
		
		/**
		 * @return the configurations
		 * @throws LoaderException 
		 */
		@SuppressWarnings("unchecked") //$NON-NLS-1$
		public LinkedList<String> LoadBdConfig() throws LoaderException {
			String filename=DATABASEFILE;
			LinkedList<String> retorno=null;
			try {
				new Properties();
				URL url=DaoProperties.class.getResource(filename);
				String ruta=filename;
				if(url!=null){			
					ruta=url.getPath().replaceAll("%20"," "); //$NON-NLS-1$ //$NON-NLS-2$
					if(ruta.contains("!")) //$NON-NLS-1$
						ruta = DATABASEFILE;
				}
				File archivo=new File(ruta);
			    FileInputStream propsfile = new FileInputStream(archivo);
			    retorno=(LinkedList<String>) x.fromXML(propsfile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new LoaderException(FILE_NOT_FOUND+filename,e);
			} catch (Exception e){
				e.printStackTrace();
				throw new LoaderException(EXCEPTION+filename,e);
			}
			return retorno;
		}

		
}
