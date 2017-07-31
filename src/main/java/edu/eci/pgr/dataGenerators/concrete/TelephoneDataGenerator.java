/**
 * 
 */
package edu.eci.pgr.dataGenerators.concrete;

import java.sql.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;

/**
 * @author Felipe
 *
 */
public class TelephoneDataGenerator extends DataGenerator{
	private static final String DOUBLE_MESSAGE = MessageBundleManager.getString("TelephoneDataGenerator.0"); //$NON-NLS-1$
	private static final String DATE_MESSAGE = MessageBundleManager.getString("TelephoneDataGenerator.1"); //$NON-NLS-1$
	private static final String TELEPHONE_GENERATOR = MessageBundleManager.getString("TelephoneDataGenerator.2"); //$NON-NLS-1$
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] currentUniqueValues;
	/**number of telephone digits **/
	private static final int TEL_NUM_DIGITS=7;
	private static final int RAND_FACTOR=(int) (5*Math.pow(10, TEL_NUM_DIGITS-1));
	
	
	public TelephoneDataGenerator(){
		currentUniqueValues=new int[2];
		currentUniqueValues[0]=RAND_FACTOR-this.uniqueAux();
		currentUniqueValues[1]=RAND_FACTOR+this.uniqueAux();
		types = new LinkedList<String>();
		uniqueTypes = new LinkedList<String>();
		types.add(TypesNames.INT);
		types.add(TypesNames.STRING);
		uniqueTypes.add(TypesNames.INT);
		uniqueTypes.add(TypesNames.STRING);
	}
	
	private int uniqueAux (){
		String ret="1"; //$NON-NLS-1$
		for (int i = 0; i < TEL_NUM_DIGITS-1; i++) {
			ret+=(int)(Math.random()*9);
		}
		return Integer.parseInt(ret);
	}
	
	private int uniqueValue() throws GeneratorException{
			int pos= (int) (Math.random()*2);
			if(pos==2) pos=1;
			if (pos==0)currentUniqueValues[pos]-=(Math.random()*9)+1;
			else currentUniqueValues[pos]+=(Math.random()*9)+1;
			String s = String.valueOf(currentUniqueValues[pos]);
			if(this.getType().getRange()<TEL_NUM_DIGITS)
				return Integer.valueOf(s.substring(s.length()-this.getType().getRange(),s.length()));
			else
				return Integer.valueOf(s);				
					
	}
	
	@Override
	public String generateNextStringValue() {
		String ret=""; //$NON-NLS-1$
		int nextdigit;
		for (int i = 0; i < TEL_NUM_DIGITS; i++) {
			nextdigit=(int) (Math.random()*9);
			ret+=nextdigit;
		}
		if(this.getType().getRange()<TEL_NUM_DIGITS)
			return ret.substring(0,this.getType().getRange()-1);
		else
			return ret;
			
	}

	@Override
	public int generateNextIntUniqueValue() throws GeneratorException {
		return this.uniqueValue();
	}
	@Override
	public int generateNextIntValue() {
		String ret=""; //$NON-NLS-1$
		int nextdigit;
		for (int i = 0; i < TEL_NUM_DIGITS; i++) {
			nextdigit=(int) (Math.random()*8+1);
			ret+=nextdigit;
		}
		if(this.getType().getRange()==1)
			return Integer.parseInt(ret.substring(0,1));
		else if(this.getType().getRange()<=TEL_NUM_DIGITS)
			return Integer.parseInt(ret.substring(0,this.getType().getRange()-1));
		else
			return Integer.parseInt(ret);
		}
	
	@Override
	public String generateNextStringUniqueValue() throws GeneratorException {
		int pref=(int) ((Math.random()*89)+10);
		String prefix="(0"+pref+")"; //$NON-NLS-1$ //$NON-NLS-2$
		if(prefix.length()<this.getType().getRange())
			return (prefix+this.uniqueValue());
		else
			return (prefix+this.uniqueValue());			
	}

	@Override
	public List<String> getTypes() {
		return types;
	}

	@Override
	public Date generateNextDateUniqueValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(TELEPHONE_GENERATOR+DATE_MESSAGE);
	}

	@Override
	public Date generateNextDateValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(TELEPHONE_GENERATOR+DATE_MESSAGE);
	}


	@Override
	public String[] getAttributes() {
		return attributes;
	}
	@Override
	public Hashtable<String, Object> getAttributesValues() {
		return attributeValues;
	}

	@Override
	public double generateNextDoubleUniqueValue()
			throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(TELEPHONE_GENERATOR+DOUBLE_MESSAGE);
	}

	@Override
	public double generateNextDoubleValue()
			throws UnsupportedDataException {
		throw new UnsupportedDataException(TELEPHONE_GENERATOR+DOUBLE_MESSAGE);
		}

	@Override
	public boolean generateNextBooleanValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(TELEPHONE_GENERATOR+DATE_MESSAGE);
	}

	@Override
	public void addAttributeValue(String attribute, Object value)
			throws UnsupportedAttributeValueException,UnsupportedAttribute {}

	@Override
	public List<String> getUniqueTypes() {
		return uniqueTypes;
	}

	@Override
	public void doneConfiguration() throws UnsupportedAttributeValueException,
			UnsupportedAttribute {}

	@Override
	public long getMaximumUniqueValues() {
		/*esta cantidad es imposible calcularla exacta basado en el peor caso
		daoo que la cantidad varia cada valor generado, por eso se calcula basada
		en el peor caso*/
		Double cant=9*Math.pow(10, TEL_NUM_DIGITS-2);
		return cant.longValue();
	}


}
