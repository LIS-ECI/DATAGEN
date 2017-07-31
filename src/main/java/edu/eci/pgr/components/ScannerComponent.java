/**
 * 
 */
package edu.eci.pgr.components;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.eci.pgr.business.ColumnVO;
import edu.eci.pgr.business.DatabaseVO;
import edu.eci.pgr.business.ForeignKeyVO;
import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.i18n.MessageBundleManager;



/*
 * this file has been created by Beatriz Rojas and Felipe Villamil.
 * this file is a component of MetaDatBrowsing  
 */
/** 
 * @author Beatriz Rojas
 * @author Felipe Villamil
 * @date 30/03/2009 
 * @version 1.0
 * @class ScannerComponent
 * @project MetaDataBrowsing
 * @company Escuela Colombiana de Ingenieria  
 */
public class ScannerComponent {
	
	public class InvalidAmountError {
		private ArrayList<String> referencedTables;
		private String tablename;
		private long amount;
		private String message;
		
		/**
		 * @param referencedTables
		 * @param tablename
		 * @param amount
		 */
		private InvalidAmountError(ArrayList<String> referencedTables, String tablename,
				long amount) {
			this.referencedTables = referencedTables;
			this.tablename = tablename;
			this.amount = amount;
			this.message=MessageBundleManager.getString("ScannerComponent.0")+ amount +MessageBundleManager.getString("ScannerComponent.1")+tablename+  //$NON-NLS-1$ //$NON-NLS-2$
			MessageBundleManager.getString("ScannerComponent.2")+referencedTables+ MessageBundleManager.getString("ScannerComponent.3");  //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		/**
		 * @return the referencedTables
		 */
		public ArrayList<String> getReferencedTables() {
			return referencedTables;
		}
		/**
		 * @return the tablename
		 */
		public String getTablename() {
			return tablename;
		}
		/**
		 * @return the amount
		 */
		public long getAmount() {
			return amount;
		}
		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

	}
	

	private static ScannerComponent instance;
	
	private ScannerComponent(){
	
	}
	
	public static ScannerComponent getInstance(){
		if(instance==null) instance=new ScannerComponent();
		return instance;
	}
	
	public List<InvalidAmountError> verrifyInsertAmount(DatabaseVO database){
		List<InvalidAmountError> errors= new ArrayList<InvalidAmountError>();
		Hashtable<String, ForeignKeyVO > pkfkcolumns;
		Hashtable<String, ColumnVO> pkcolumns;
		Hashtable <String,Long> amounts;
		ArrayList<String> referencedtables;
		for(TableVO table: database.getTables().values()){
			// only for pkfk columns need count
			//its possible generate M data for one column thats is referenced by other column with N rows (M>>N)
			//because the rows can be repeated 
			pkfkcolumns=(Hashtable<String, ForeignKeyVO>) table.getPkFkColumns();
			pkcolumns=(Hashtable<String, ColumnVO>) table.getPrimaryColumns();
			//if table have the same number of pk and pkfk columns
			if(pkfkcolumns.size()>0 && pkfkcolumns.size()==pkcolumns.size()) {
				// hash table to count 
				amounts= new Hashtable<String, Long>();
				// referenced tables
				referencedtables= new ArrayList<String>();
				long count=1;
				for(ForeignKeyVO col: pkfkcolumns.values()){
					amounts.put(col.getOriginalTable().getName(), col.getOriginalTable().getInsertTimes());
					referencedtables.add(col.getOriginalTable().getName());
				}
				// make the count to max amount of referenced tables
				for(Long val: amounts.values()) count*=val;
				if(table.getInsertTimes()>=count) errors.add(
						new InvalidAmountError(referencedtables,table.getName(), table.getInsertTimes()));
			}
		}
		return errors;
	}
	
	
}
