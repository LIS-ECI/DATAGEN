package edu.eci.pgr.dataGenerators;


import java.io.Serializable;
import java.sql.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.TypeVO;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
public abstract class DataGenerator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected TypeVO type;
	protected List<String> types;
	protected List<String> uniqueTypes;
	protected String[] attributes;
	protected Hashtable<String, Object> attributeValues;
	protected String name;
	
	public DataGenerator(){
		this.types = new LinkedList<String>();
		this.uniqueTypes = new LinkedList<String>();
		this.attributes = new String[0];
		this.attributeValues = new Hashtable<String, Object>();
	}
	
	public DataGenerator(String[] attributes){
		this.types = new LinkedList<String>();
		this.uniqueTypes = new LinkedList<String>();
		this.attributes = attributes;
		this.attributeValues = new Hashtable<String, Object>();
	}
	
	public DataGenerator(TypeVO type, String[] attributes){
		this.type = type;
		this.types = new LinkedList<String>();
		this.uniqueTypes = new LinkedList<String>();
		this.attributes = attributes;
		this.attributeValues = new Hashtable<String, Object>();
	}
	/**
	 * generates the next value that can be duplicated
	 * @return the next value
	 */
	public abstract String generateNextStringValue() throws UnsupportedDataException, GeneratorException;
	
	/**
	 * generates the next value that can't be duplicated
	 * @return the next unique value
	 * @throws GeneratorException 
	 */
	public abstract String generateNextStringUniqueValue() throws UnsupportedDataException, GeneratorException;
	/**
	 * generates the next value that can be duplicated
	 * @return the next value
	 */
	public abstract int generateNextIntValue() throws UnsupportedDataException, GeneratorException;
	
	/**
	 * generates the next value that can't be duplicated
	 * @return the next unique value
	 * @throws GeneratorException 
	 */
	public abstract int generateNextIntUniqueValue() throws UnsupportedDataException, GeneratorException;
	
	public abstract Date generateNextDateValue() throws UnsupportedDataException, GeneratorException;
	
	public abstract Date generateNextDateUniqueValue() throws UnsupportedDataException, GeneratorException;
	/**
	 * generates the next value that can be duplicated
	 * @return the next value
	 */
	public abstract double generateNextDoubleValue() throws UnsupportedDataException, GeneratorException;
	
	/**
	 * generates the next value that can't be duplicated
	 * @return the next unique value
	 * @throws GeneratorException 
	 */
	public abstract double generateNextDoubleUniqueValue() throws UnsupportedDataException, GeneratorException;
	
	public abstract boolean generateNextBooleanValue()throws UnsupportedDataException, GeneratorException;
	
	public abstract void addAttributeValue(String attribute,Object value) throws UnsupportedAttributeValueException, UnsupportedAttribute;
	
	/**
	 * @deprecated: The DataGenerator must be change a {@code atributeValues} one by one
	 * not everyone at once because this operation decreases performance and response time.
	 * replaced by: <code> addAttributeValue(String attribute, Object value) <code> this
	 * method has been replace by doneConfiguration.
	 * 
	 * @param values
	 * 			the value of attributes
	 * @throws UnsupportedAttributeValueException
	 * 			if the some value in {@code values} has not the expected format
	 * @throws UnsupportedAttribute
	 * 			if the {@code attribute} not exists.
	 * 
	 */
	@Deprecated
	public  void setAttributeValues(Hashtable<String, Object> values)throws UnsupportedAttributeValueException, UnsupportedAttribute{}
	
	public abstract void doneConfiguration()throws UnsupportedAttributeValueException, UnsupportedAttribute;
	
	/**
	 * This method must be calculates the maximum number of unique values 
	 * that can generate the data generator, if the <code>data generator</code>
	 * not generate unique values the return is 0(Zero).
	 * this method is employed to validate data given for the user in the GUI,
	 * if the number of values to generate is more than the value returned
	 * in this method the insertion process can't be executed, and the user 
	 * must be change the incorrect values.
	 * 
	 * @return
	 * 		the maximum number of values that can generate the generator 
	 */
	public abstract long getMaximumUniqueValues();
	/**
	 * return the associated types to generator 
	 * @return the type
	 */
	public List<String> getTypes(){
		return types;
	}
	
	/**
	 * @return the attributes
	 */
	public String[] getAttributes(){
		return attributes;
	}
	
	public Hashtable<String, Object> getAttributesValues(){
		return attributeValues;
	}

	public List<String> getUniqueTypes(){
		return uniqueTypes;
	}

	/**
	 * @return the type
	 */
	public TypeVO getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeVO type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
