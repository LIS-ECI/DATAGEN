package edu.eci.pgr.dataGenerators.concrete;

import java.sql.Date;
import java.util.LinkedList;

import jsc.distributions.Bernoulli;
import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class LogicBernoulliDistributionGenerator extends DataGenerator{
	private static final String NOT_SUPPORTED_ATTRIBUTE = MessageBundleManager.getString("LogicBernoulliDistributionGenerator.0"); //$NON-NLS-1$
	private static final String DOUBLE_ALERT = MessageBundleManager.getString("LogicBernoulliDistributionGenerator.1"); //$NON-NLS-1$
	private static final String DOUBLE_MESSAGE = MessageBundleManager.getString("LogicBernoulliDistributionGenerator.2"); //$NON-NLS-1$
	private static final String DATE_MESSAGE = MessageBundleManager.getString("LogicBernoulliDistributionGenerator.3"); //$NON-NLS-1$
	private static final String LOGIC_GENERATOR_MESSAGE = MessageBundleManager.getString("LogicBernoulliDistributionGenerator.4"); //$NON-NLS-1$
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS_PROBABILITY = MessageBundleManager.getString("LogicBernoulliDistributionGenerator.5"); //$NON-NLS-1$
	private Bernoulli distribution;
	private double successProbability;
	
	public LogicBernoulliDistributionGenerator(){
		types = new LinkedList<String>();
		types.add(TypesNames.BOOLEAN);
		types.add(TypesNames.INT);
		types.add(TypesNames.STRING);
		uniqueTypes = null;
		attributes=new String[]{SUCCESS_PROBABILITY};
	}

	public Date generateNextDateUniqueValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(LOGIC_GENERATOR_MESSAGE+DATE_MESSAGE);
	}

	public Date generateNextDateValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(LOGIC_GENERATOR_MESSAGE+DATE_MESSAGE);
	}

	@Override
	public double generateNextDoubleUniqueValue()
			throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(LOGIC_GENERATOR_MESSAGE+DOUBLE_MESSAGE);
	}

	@Override
	public double generateNextDoubleValue()
			throws UnsupportedDataException {
		throw new UnsupportedDataException(LOGIC_GENERATOR_MESSAGE+DOUBLE_MESSAGE);
	}

	@Override
	public int generateNextIntUniqueValue()
			throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(LOGIC_GENERATOR_MESSAGE+DOUBLE_MESSAGE);
	}
	
	@Override
	public int generateNextIntValue()
			throws UnsupportedDataException {
		return this.generateNextBooleanValue()?1:0;
	}

	@Override
	public String generateNextStringUniqueValue()
			throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(LOGIC_GENERATOR_MESSAGE+DOUBLE_MESSAGE);
	}

	@Override
	public String generateNextStringValue()
			throws UnsupportedDataException {
		return Boolean.toString(this.generateNextBooleanValue());
	}

	@Override
	public boolean generateNextBooleanValue() throws UnsupportedDataException {
		return distribution.random() == 1;
	}

	@Override
	public void addAttributeValue(String attribute,Object value) throws UnsupportedAttributeValueException, UnsupportedAttribute{
		if(!(value instanceof Double)) 
			throw new UnsupportedAttributeValueException(DOUBLE_ALERT);
		if(!attribute.equals(SUCCESS_PROBABILITY)) 
			throw new UnsupportedAttribute(NOT_SUPPORTED_ATTRIBUTE+attribute);
		attributeValues.put(attribute,value);
		successProbability = ((Double)value).doubleValue();
		
	}

	@Override
	public void doneConfiguration() throws UnsupportedAttributeValueException,
			UnsupportedAttribute {
		if(attributeValues.get(SUCCESS_PROBABILITY)==null)
			throw new UnsupportedAttributeValueException(DOUBLE_ALERT);
		distribution = new Bernoulli(successProbability);
		
	}

	@Override
	public long getMaximumUniqueValues() {
		return 0;
	}
	
}
