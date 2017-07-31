package edu.eci.pgr.dataGenerators.concrete;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import jsc.distributions.Normal;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class NumberNormalGenerator extends DataGenerator{
	private static final String DIGITS_ERROR = MessageBundleManager.getString("NumberNormalGenerator.0"); //$NON-NLS-1$
	private static final String NUMBER_MESSAGE = MessageBundleManager.getString("NumberNormalGenerator.1"); //$NON-NLS-1$
	private static final String MEAN_DEVIATION_MESSAGE = MessageBundleManager.getString("NumberNormalGenerator.2"); //$NON-NLS-1$
	private static final String AMOUNT_DIGITS_MESSAGE = MessageBundleManager.getString("NumberNormalGenerator.3"); //$NON-NLS-1$
	private static final String DIGITS_MESSAGE_2 = MessageBundleManager.getString("NumberNormalGenerator.4"); //$NON-NLS-1$
	private static final String AND = MessageBundleManager.getString("NumberNormalGenerator.5"); //$NON-NLS-1$
	private static final String DIGITS_MESSAGE_1 = MessageBundleManager.getString("NumberNormalGenerator.6"); //$NON-NLS-1$
	private static final String ASSINGMENT_ERROR = MessageBundleManager.getString("NumberNormalGenerator.7"); //$NON-NLS-1$
	private static final String MEAN_MESSAGE = MessageBundleManager.getString("NumberNormalGenerator.8"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	private static final String UNSUPPORTEDMESSAGE = MessageBundleManager.getString("NumberNormalGenerator.9"); //$NON-NLS-1$
	private static final String UNSUPPORTEDUNIQUEMESSAGE = MessageBundleManager.getString("NumberNormalGenerator.10"); //$NON-NLS-1$
	private static final String DATE = MessageBundleManager.getString("NumberNormalGenerator.11"); //$NON-NLS-1$
	private static final String DOUBLE = MessageBundleManager.getString("NumberNormalGenerator.12"); //$NON-NLS-1$
	private static final String INT = MessageBundleManager.getString("NumberNormalGenerator.13"); //$NON-NLS-1$
	private static final String STRING = MessageBundleManager.getString("NumberNormalGenerator.14"); //$NON-NLS-1$
	private static final String STANDAR_DESV= MessageBundleManager.getString("NumberNormalGenerator.15"); //$NON-NLS-1$
	private static final String MEAN = MessageBundleManager.getString("NumberNormalGenerator.16"); //$NON-NLS-1$
	private static final String DECIMALDIG = MessageBundleManager.getString("NumberNormalGenerator.17"); //$NON-NLS-1$
	private static final String NOTDECIMALDIG = MessageBundleManager.getString("NumberNormalGenerator.18"); //$NON-NLS-1$

	private Normal normal;
	private DecimalFormat nf;


	public NumberNormalGenerator(){
		super(new String[]{STANDAR_DESV,MEAN,DECIMALDIG,NOTDECIMALDIG});
		normal= new Normal();
		types.add(TypesNames.INT);
		types.add(TypesNames.DOUBLE);
		types.add(TypesNames.STRING);
	}


	private Double randomValue(){
		return normal.random();
	}

	@Override
	public Date generateNextDateUniqueValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+DATE);
	}

	@Override
	public Date generateNextDateValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+DATE);
	}

	@Override
	public String generateNextStringUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+STRING);
	}

	@Override
	public String generateNextStringValue()
	throws UnsupportedDataException {
		return nf.format(randomValue()); 
	}

	@Override
	public double generateNextDoubleUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+DOUBLE);
	}

	@Override
	public double generateNextDoubleValue()
	throws UnsupportedDataException {
		return Double.parseDouble(nf.format(randomValue())); 
	}

	@Override
	public int generateNextIntUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+INT);
	}

	@Override
	public int generateNextIntValue()
	throws UnsupportedDataException {
		Double retorno=Double.parseDouble(nf.format(randomValue()));
		return retorno.intValue(); 

	}

	@Override
	public boolean generateNextBooleanValue() throws UnsupportedDataException {
		return randomValue()<randomValue();
	}

	@Override
	public void addAttributeValue(String attribute, Object value)
	throws UnsupportedAttributeValueException, UnsupportedAttribute {
		if(attribute.equals(MEAN) || attribute.equals(STANDAR_DESV)){
			try{
				Double doubleVal = Double.parseDouble((String)value);
				this.attributeValues.put(attribute, doubleVal);
			}catch(NumberFormatException e){
				throw new UnsupportedAttributeValueException(MEAN_MESSAGE);
			}catch(Exception e){
				throw new UnsupportedAttributeValueException(ASSINGMENT_ERROR+e.getMessage());
			}
		}else if(attribute.equals(DECIMALDIG) || attribute.equals(NOTDECIMALDIG)){
			try{
				Integer intVal = Integer.parseInt((String)value);
				int less =  attribute.equals(DECIMALDIG)?0:1;
				if(intVal<less || intVal>type.getRange())
					throw new UnsupportedAttributeValueException(DIGITS_MESSAGE_1+less+AND+type.getRange()+DIGITS_MESSAGE_2);
				this.attributeValues.put(attribute, intVal);
			}catch(NumberFormatException e){
				throw new UnsupportedAttributeValueException(AMOUNT_DIGITS_MESSAGE);
			}catch(Exception e){
				throw new UnsupportedAttributeValueException(ASSINGMENT_ERROR+e.getMessage());
			}
		}
	}

	@Override
	public void doneConfiguration() throws UnsupportedAttributeValueException,
			UnsupportedAttribute {
		try{		
			if(this.attributeValues.get(MEAN)!=null &&
					this.getAttributesValues().get(STANDAR_DESV)!=null){
				normal.setMean((Double) this.attributeValues.get(MEAN));
				normal.setSd((Double) this.getAttributesValues().get(STANDAR_DESV));
			}
		}catch(NumberFormatException e){
			throw new UnsupportedAttributeValueException(MEAN_DEVIATION_MESSAGE);
		}catch(Exception e){
			throw new UnsupportedAttributeValueException(ASSINGMENT_ERROR+e.getMessage());
		}
		try{
			if(this.attributeValues.get(DECIMALDIG)!=null &&
					this.getAttributesValues().get(NOTDECIMALDIG)!=null){
				if((Integer) this.attributeValues.get(DECIMALDIG)+
						(Integer) this.attributeValues.get(NOTDECIMALDIG)>this.type.getRange()||
						(Integer) this.attributeValues.get(DECIMALDIG)+
						(Integer) this.attributeValues.get(NOTDECIMALDIG)<2)
					throw new UnsupportedAttributeValueException(NUMBER_MESSAGE+this.type.getRange()); 
				formatConstructor((Integer) this.attributeValues.get(DECIMALDIG),
						(Integer) this.attributeValues.get(NOTDECIMALDIG));
			}	
		}catch(NumberFormatException e){
			throw new UnsupportedAttributeValueException(DIGITS_ERROR);
		}catch(Exception e){
			throw new UnsupportedAttributeValueException(ASSINGMENT_ERROR+e.getMessage());
		}
		
	}
	
	private void formatConstructor(int decDig, int notDecDigl){
		String format=""; //$NON-NLS-1$
		for (int i = 0; i < notDecDigl; i++) {
			format+="0"; //$NON-NLS-1$
		}
		if(decDig>0)
			format+="."; //$NON-NLS-1$
		for (int i = 0; i < decDig; i++) {
			format+="0"; //$NON-NLS-1$
		}
		//the local parameter is need but the English locale see the double separator as '.' symbol 
		this.nf= new DecimalFormat(format, new DecimalFormatSymbols(Locale.ENGLISH));
	}


	@Override
	public long getMaximumUniqueValues() {
		return 0;
	}


}
