package edu.eci.pgr.view;

import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.eci.pgr.i18n.MessageBundleManager;

public class ViewConstants {
	public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = (int)(SCREEN_HEIGHT*0.80);
	public static final int WIDTH = (int)(SCREEN_WIDTH*0.45);
	public static final int TOTAL_SUPPORTED_DATABASES = 2;
	public static final int URL = 0;
	public static final int DRIVER = 1;
	public static final int TOTAL_CONNECTION_INFORMATION = 2;
	public static final int MYSQL_TEMPLATE = 0;
	public static final int ORACLE_TEMPLATE = 1;
	public static final int SQLSERVER_TEMPLATE = 2;
	public static final int SAVE =0;
	public static final int OPEN = 1;
	public static final int SAVE_AS = 2;

	public static final int[] TABLE_LIST_COLUMNS_WIDTHS = new int[]{120,120,100};
	public static final long HOURINTERVAL = (long) (((Math.random())*600000)+10000);
	public static final String FILESEXTENTION = ".dbd";
	public static final int FILE_OPTIONS_HEIGHT = 130;
	public static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
	public static final String ENCODING = "UTF-8";
	public static final String PASSWORDSFILE = "passwords.dbp";

	
	// Messages
	public static final String LOGFILETIITTLE = "MESSAGE LOG";
	public static final String FILELOG = "Log.txt";
	public static String[] getDatabaseTemplates(){
		String [] response = new String[Integer.parseInt(MessageBundleManager.getString("TOTAL_ENGINES"))];
		for(int i=0;i<response.length;i++){
			response[i] = MessageBundleManager.getString("DATABASE_TEMPLATES"+(i+1));
		}
		return response;
	}
	public static String[] getDatabaseEngines(){
		String [] response = new String[Integer.parseInt(MessageBundleManager.getString("TOTAL_ENGINES"))];
		for(int i=0;i<response.length;i++){
			response[i] = MessageBundleManager.getString("DATABASE_ENGINES"+(i+1));
		}
		return response;
	}
	public static String[] getDatabaseUrlConnection(){
		String [] response = new String[Integer.parseInt(MessageBundleManager.getString("TOTAL_ENGINES"))];
		for(int i=0;i<response.length;i++){
			response[i] = MessageBundleManager.getString("DATABASE_URL_CONNECTION"+(i+1));
		}
		return response;
	}
	public static String[] getDatabaseDriverClassName(){
		String [] response = new String[Integer.parseInt(MessageBundleManager.getString("TOTAL_ENGINES"))];
		for(int i=0;i<response.length;i++){
			response[i] = MessageBundleManager.getString("DATABASE_DRIVER_CLASSNAME"+(i+1));
		}
		return response;
	}

}
