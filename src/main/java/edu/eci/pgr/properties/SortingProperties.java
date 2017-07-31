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



public class SortingProperties {
		private static final String SORT_ERROR = MessageBundleManager.getString("SortingProperties.0"); //$NON-NLS-1$
		public static final String SORTINGFILE="sorting.xml"; //$NON-NLS-1$
		public static final int SORTING = 0;
		private static SortingProperties instance;
		private XStream x;
		
		/**
		 * @return the instance
		 */
		public static SortingProperties getInstance() {
			if(instance==null){
				instance=new SortingProperties();
			}
			return instance;
		}
		
		public SortingProperties(){
			x=new XStream(new DomDriver());
		}
		
		
		/**
		 * @return the configurations
		 * @throws LoaderException 
		 */
		@SuppressWarnings("unchecked") //$NON-NLS-1$
		public LinkedList<String> LoadSortingConfig() throws LoaderException {
			String filename=SORTINGFILE;
			LinkedList<String> retorno=null;
			try {
				new Properties();
				URL url=SortingProperties.class.getResource(filename);
				String ruta=filename;
				if(url!=null){			
					ruta=url.getPath().replaceAll("%20"," "); //$NON-NLS-1$ //$NON-NLS-2$
					if(ruta.contains("!")) //$NON-NLS-1$
						ruta = SORTINGFILE;
				}
				File archivo=new File(ruta);
			    FileInputStream propsfile = new FileInputStream(archivo);
			    retorno=(LinkedList<String>) x.fromXML(propsfile);
			} catch (FileNotFoundException e) {
				throw new LoaderException(SORT_ERROR+filename,e);
			} catch (Exception e){
				e.printStackTrace();
			}
			return retorno;
			
		}
}
