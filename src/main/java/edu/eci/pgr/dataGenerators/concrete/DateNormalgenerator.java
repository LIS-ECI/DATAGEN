package edu.eci.pgr.dataGenerators.concrete;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import jsc.distributions.Normal;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class DateNormalgenerator extends DataGenerator{

	private String NULL_MESSAGE = MessageBundleManager.getString("DateNormalgenerator.0"); //$NON-NLS-1$
	private String AND = MessageBundleManager.getString("DateNormalgenerator.1"); //$NON-NLS-1$
	private String ASSINGMENT_ERROR = MessageBundleManager.getString("DateNormalgenerator.2"); //$NON-NLS-1$
	private String DOUBLE_MESSAGE = MessageBundleManager.getString("DateNormalgenerator.3"); //$NON-NLS-1$
	private String NOT_VALID_MESSAGE = MessageBundleManager.getString("DateNormalgenerator.4"); //$NON-NLS-1$
	private String THE = MessageBundleManager.getString("DateNormalgenerator.5"); //$NON-NLS-1$
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String UNSUPPORTEDMESSAGE = MessageBundleManager.getString("DateNormalgenerator.6"); //$NON-NLS-1$
	private String UNSUPPORTEDUNIQUEMESSAGE = MessageBundleManager.getString("DateNormalgenerator.7"); //$NON-NLS-1$
	private String STANDAR_DESV= MessageBundleManager.getString("DateNormalgenerator.8"); //$NON-NLS-1$
	private String MEAN = MessageBundleManager.getString("DateNormalgenerator.9"); //$NON-NLS-1$
	private String FORMAT = MessageBundleManager.getString("DateNormalgenerator.10") + //$NON-NLS-1$
			MessageBundleManager.getString("DateNormalgenerator.11") + //$NON-NLS-1$
			MessageBundleManager.getString("DateNormalgenerator.12");  //$NON-NLS-1$
	private String DATE = MessageBundleManager.getString("DateNormalgenerator.13"); //$NON-NLS-1$
	private String STRING = MessageBundleManager.getString("DateNormalgenerator.14");  //$NON-NLS-1$
	private String INT = MessageBundleManager.getString("DateNormalgenerator.15"); //$NON-NLS-1$
	private String DOUBLE = MessageBundleManager.getString("DateNormalgenerator.16"); //$NON-NLS-1$
	private String BOOLEAN = MessageBundleManager.getString("DateNormalgenerator.17"); //$NON-NLS-1$
	private Normal normal;
	private DateFormat df; 

	public DateNormalgenerator() {
		super(new String[]{MessageBundleManager.getString("DateNormalgenerator.8"),MessageBundleManager.getString("DateNormalgenerator.9"),MessageBundleManager.getString("DateNormalgenerator.10") + //$NON-NLS-1$
				MessageBundleManager.getString("DateNormalgenerator.11") + //$NON-NLS-1$
				MessageBundleManager.getString("DateNormalgenerator.12")});
		normal= new Normal();
		types.add(TypesNames.STRING);
		types.add(TypesNames.DATE);	
		uniqueTypes = null;
	}
	@Override
	public Date generateNextDateUniqueValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+DATE);
	}

	@Override
	public Date generateNextDateValue() throws UnsupportedDataException {
		return generateDate(normal.random());
	}

	@Override
	public double generateNextDoubleUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+DOUBLE);
	}

	@Override
	public double generateNextDoubleValue()
	throws UnsupportedDataException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+DOUBLE);
	}

	@Override
	public int generateNextIntUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+INT);
	}

	@Override
	public int generateNextIntValue()
	throws UnsupportedDataException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+INT);
	}

	@Override
	public String generateNextStringUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+STRING);
	}

	@Override
	public String generateNextStringValue()
	throws UnsupportedDataException {
		return df.format(generateDate(normal.random()));
	}
	
	@Override
	public boolean generateNextBooleanValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+BOOLEAN);
	}

	@Override
	public List<String> getTypes() {
		return this.types;
	}

	private Date generateDate(double random) {
		Integer year=(int) random;
		Calendar cal= Calendar.getInstance();
		Integer month=(int) (Math.random()*12);
		if(month==12) month=0;
		Integer day;
		if(month==0 || month==2||month==4 || month==6|| month==7||month==9 || month==11){
			day=(int) (Math.random()*31);
			if(day==0) day=31;
		}else if(month==1){
			if(year%4==0 &&(year%100!=0 || year%400==0)){
				day=(int) (Math.random()*29);
				if(day==0) day=29;
			}else{
				day=(int) (Math.random()*28);
				if(day==0) day=28;
			}

		}else{
			day=(int) (Math.random()*30);
			if(day==0) day=30;
		}
		int hour=(int) (Math.random()*24);
		if(hour==24) hour=0;
		int minute=(int) (Math.random()*60);
		if(hour==60) hour=0;
		int second=(int) (Math.random()*60);
		if(hour==60) hour=59;
		cal.set(year, month, day, hour, minute, second);
		return new Date(cal.getTimeInMillis());
	}

	@Override
	public void addAttributeValue(String attribute, Object value)
	throws UnsupportedAttributeValueException, UnsupportedAttribute {
		if (attribute.equals(FORMAT)){
			try{
				df = new SimpleDateFormat((String)value);
				this.attributeValues.put(attribute, value);
			}catch(Exception e){
				throw new UnsupportedAttributeValueException(THE+attribute+NOT_VALID_MESSAGE);
			}
		}
		else{
			try{
				Double doubleVal = Double.parseDouble((String)value);
				this.attributeValues.put(attribute, doubleVal);
			}catch(NumberFormatException e){
				throw new UnsupportedAttributeValueException(THE+attribute+DOUBLE_MESSAGE);
			}catch(Exception e){
				throw new UnsupportedAttributeValueException(ASSINGMENT_ERROR+e.getMessage());
			}
		}
	}

	@Override
	public void doneConfiguration() throws UnsupportedAttributeValueException,
	UnsupportedAttribute {
		String format;
		try{
			format = (String) attributeValues.get(FORMAT);
			df = new SimpleDateFormat(format);
		}catch(Exception e){
			throw new UnsupportedAttributeValueException(THE+FORMAT+NOT_VALID_MESSAGE);
		}
		if (format==null){
			throw new UnsupportedAttributeValueException(THE+FORMAT+ASSINGMENT_ERROR);
		}
		try{
			if(this.attributeValues.get(MEAN)!=null &&
					this.getAttributesValues().get(STANDAR_DESV)!=null){	
				normal.setMean((Double) this.attributeValues.get(MEAN));
				normal.setSd((Double) this.getAttributesValues().get(STANDAR_DESV));
			}
			else 
				throw new UnsupportedAttributeValueException(THE+MEAN +AND+ STANDAR_DESV+NULL_MESSAGE);
		}catch(NumberFormatException e){
			throw new UnsupportedAttributeValueException(THE+MEAN +AND+ STANDAR_DESV+DOUBLE_MESSAGE);
		}catch(Exception e){
			throw new UnsupportedAttributeValueException(ASSINGMENT_ERROR+e.getMessage());
		}
	}
	
	@Override
	public long getMaximumUniqueValues() {
		return 0;
	}
}
