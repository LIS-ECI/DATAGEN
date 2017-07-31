package edu.eci.pgr.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.eci.pgr.exceptions.LoaderException;
import edu.eci.pgr.exceptions.WriterException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.ViewConstants;

public class PlanDAO {
	private static final String CREATING_ERROR = MessageBundleManager.getString("PlanDAO.0"); //$NON-NLS-1$
	private static final String LOADING_ERROR = MessageBundleManager.getString("PlanDAO.1"); //$NON-NLS-1$
	private static final String FILE_NOT_FOUND = MessageBundleManager.getString("PlanDAO.2"); //$NON-NLS-1$
	private static final String PASS_ERROR_READING = MessageBundleManager.getString("PlanDAO.3"); //$NON-NLS-1$
	private static final String PASS_ERROR = MessageBundleManager.getString("PlanDAO.4"); //$NON-NLS-1$
	private static PlanDAO instance;
	private XStream x;
	private PlanDAO(){
		x=new XStream(new DomDriver());
	}
	public static PlanDAO getInstance(){
		if(instance == null) instance = new PlanDAO();
		return instance;
	}
	public void savePlan(String path, PlanVO plan) throws WriterException {
		try {
			x=new XStream(new DomDriver());
			path = path.replaceFirst(ViewConstants.FILESEXTENTION, ""); //$NON-NLS-1$
			FileOutputStream archivo=new FileOutputStream(path+ViewConstants.FILESEXTENTION);
			OutputStreamWriter br = new OutputStreamWriter(archivo,ViewConstants.ENCODING);
			x.toXML(plan,br);
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new WriterException(FILE_NOT_FOUND+path,e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WriterException(CREATING_ERROR+path,e);
		}
	}

	public PlanVO loadPlan(String path) throws LoaderException {
		try {
			FileInputStream archivo=new FileInputStream(path);
			InputStreamReader br = new InputStreamReader(archivo,ViewConstants.ENCODING);
			return (PlanVO) x.fromXML(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new LoaderException(FILE_NOT_FOUND+path,e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LoaderException(LOADING_ERROR+path,e);
		}
	}
	
	public String recoverPassword(String url) throws LoaderException {
		String ret=null;
		try {
			File file = new File(ViewConstants.PASSWORDSFILE);
			if(file.exists())
				ret= getKeys(file).get(url);
		} catch (IOException e) {
			throw new LoaderException(PASS_ERROR_READING+url,e);
		} catch (ClassNotFoundException e) {
			throw new LoaderException(PASS_ERROR_READING+url,e);
		}
		return ret;
	}

	public void savePassword(String password, String url) throws WriterException {
		try {
			Hashtable<String, String> claves= new Hashtable<String, String>();
			File file = new File(ViewConstants.PASSWORDSFILE);
			if(file.exists()){
				claves = getKeys(file);
			}
			FileOutputStream fout = new FileOutputStream (file);
			ObjectOutputStream obj2 = new ObjectOutputStream (fout);
			claves.put(url,password);
			obj2.writeObject(claves);
			obj2.close();
			fout.close();
		} catch (IOException e) {
			throw new WriterException(PASS_ERROR+url,e);
		} catch (ClassNotFoundException e) {
			throw new WriterException(PASS_ERROR+url,e);
		}	
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private Hashtable<String, String> getKeys(File file) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream (file);
		ObjectInputStream obj = new ObjectInputStream (fin);
		return (Hashtable<String, String>) obj.readObject();  
	}
}
