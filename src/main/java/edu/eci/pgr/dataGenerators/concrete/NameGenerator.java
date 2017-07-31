package edu.eci.pgr.dataGenerators.concrete;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.components.combination.CombinationGenerator;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.DAOException;
import edu.eci.pgr.exceptions.DAOSelectException;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.persistence.DAO;
import edu.eci.pgr.properties.EmbededDataBaseConnectionLoader;

public class NameGenerator extends DataGenerator{

	private static final String NO = MessageBundleManager.getString("NameGenerator.0"); //$NON-NLS-1$
	private static final String NAME_LAST_NAME_MESSAGE = MessageBundleManager.getString("NameGenerator.1"); //$NON-NLS-1$
	private static final String YES_NO_MESSAGE = MessageBundleManager.getString("NameGenerator.2"); //$NON-NLS-1$
	private static final String NAME_FORMAT_MESSAGE = MessageBundleManager.getString("NameGenerator.3"); //$NON-NLS-1$
	private static final String YES = MessageBundleManager.getString("NameGenerator.4"); //$NON-NLS-1$
	private static final String MALE_OR_FEMALE_MESSAGE = MessageBundleManager.getString("NameGenerator.5"); //$NON-NLS-1$
	private static final String INTEGER_MESSAGE = MessageBundleManager.getString("NameGenerator.6"); //$NON-NLS-1$
	private static final String DOUBLE_MESSAGE = MessageBundleManager.getString("NameGenerator.7"); //$NON-NLS-1$
	private static final String DATE_MESSAGE = MessageBundleManager.getString("NameGenerator.8"); //$NON-NLS-1$
	private static final String BOOL_MESSAGE = MessageBundleManager.getString("NameGenerator.9"); //$NON-NLS-1$
	private static final String NAME_GENERATOR_MESSAGE = MessageBundleManager.getString("NameGenerator.10"); //$NON-NLS-1$
	private static final String FEMALE_PERCENTAGE = MessageBundleManager.getString("NameGenerator.11"); //$NON-NLS-1$
	private static final String MALE_PERCENTAGE = MessageBundleManager.getString("NameGenerator.12"); //$NON-NLS-1$
	private static final String NAME = "%FN"; //$NON-NLS-1$
	private static final String LAST_NAME = "%LN"; //$NON-NLS-1$
	private static final String INITIAL = "%I"; //$NON-NLS-1$
	private static final String NAME_SPACE = "% FN"; //$NON-NLS-1$
	private static final String LAST_NAME_SPACE = "% LN"; //$NON-NLS-1$
	private static final String INITIAL_SPACE = "% I"; //$NON-NLS-1$
	private static final String FORMAT_EXPLANATION= MessageBundleManager.getString("NameGenerator.13")+NAME+", "+INITIAL+MessageBundleManager.getString("NameGenerator.14")+LAST_NAME+MessageBundleManager.getString("NameGenerator.15"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	private static final long serialVersionUID = 1L;
	private static final int FEMALE = 0;
	private static final int MALE = 1;
	private static final int LAST = 2;
	private static final int CHAR = 3;
	private static final String WHERE_CLAUSE = " ROWID = "; //$NON-NLS-1$
	private static final String VALUE = "VALUE"; //$NON-NLS-1$
	private static final long FEMALE_LENGTH_DIGITS = 10l;
	private static final long MALE_LENGTH_DIGITS = 10l;
	private static final long LAST_LENGTH_DIGITS = 10l;
	private static final long CAPITAL_LETTERS_DIGITS = 26;
	private static final int FEMALE_LENGTH = 3;
	private static final int MALE_LENGTH = 3;
	private static final int LAST_LENGTH = 3;
	private static final String FEMALE_TABLE = " FEMALE_NAMES "; //$NON-NLS-1$
	private static final String MALE_TABLE = " MALE_NAMES "; //$NON-NLS-1$
	private static final String LAST_TABLE = " LAST_NAMES "; //$NON-NLS-1$
	private static final String CAPITAL = "CAPITAL"; //$NON-NLS-1$
	private static final String DONEL = "DONEL"; //$NON-NLS-1$
	private static final String DONEF = "DONEF"; //$NON-NLS-1$
	private static final String DONEM = "DONEM"; //$NON-NLS-1$
	private static final String DONEC = "DONEC"; //$NON-NLS-1$
	private static final String booleanAttributes[] = new String[]{FORMAT_EXPLANATION,FEMALE_PERCENTAGE,MALE_PERCENTAGE};
	private static final int CHARACTER = 65;
	private DAO dao;
	private boolean femalePercentage;
	private boolean malePercentage;
	private HashMap<String,CombinationGenerator> generators;
	private Random random;
	private List<String> keyset;
	
	public NameGenerator(){
		try {
			dao = new DAO(EmbededDataBaseConnectionLoader.getInstance().getConnectionInformation());
			asignAttributes();
			types = new ArrayList<String>();
			types.add(TypesNames.STRING);
			generators = new HashMap<String,CombinationGenerator>();
			random = new Random();
			attributeValues = new Hashtable<String, Object>();
			uniqueTypes = new LinkedList<String>();
			uniqueTypes.add(TypesNames.STRING);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (InstantException e) {
			e.printStackTrace();
		}
	}
	
	private void asignAttributes() {
		attributes = booleanAttributes;
	}
	
	public String generateNextStringUniqueValue()
		throws UnsupportedDataException, GeneratorException {
		String response = ""; //$NON-NLS-1$
		String format = keyset.get(random.nextInt(keyset.size()));
		CombinationGenerator generator = generators.get(format);
		long values[] = generator.getNextValues();
		response = format;
		while(format.contains(FEMALE_TABLE)){
			response = response.replaceFirst(FEMALE_TABLE,getFemaleValue(format,values,FEMALE_TABLE));
			format = format.replaceFirst(FEMALE_TABLE,DONEF);
		}
		while(format.contains(MALE_TABLE)){
			response = response.replaceFirst(MALE_TABLE,getMaleValue(format,values,MALE_TABLE));
			format = format.replaceFirst(MALE_TABLE,DONEM);
		}
		while(format.contains(LAST_TABLE)){
			response = response.replaceFirst(LAST_TABLE,getLastValue(format,values,LAST_TABLE));
			format = format.replaceFirst(LAST_TABLE,DONEL);
		}
		while(format.contains(CAPITAL)){
			response = response.replaceFirst(CAPITAL,getCapitalValue(format,values));
			format = format.replaceFirst(CAPITAL,DONEC);
		}
		return response.substring(0,response.length()-1);
	}
	private String getCapitalValue(String format, long[] values) {
		int start = getStartIndex(format,CAPITAL);
		return ""+(char)(Integer.parseInt(getIndex(values, start, start+1))+CHARACTER); //$NON-NLS-1$
	}
	private String getLastValue(String format, long[] values, String lastTable) {
		int start = getStartIndex(format,lastTable);
		int finish = start+LAST_LENGTH;
		return getValue(values,start,finish,lastTable);
	}
	private String getMaleValue(String format, long[] values, String maleTable) {
		int start = getStartIndex(format,maleTable);
		int finish = start+MALE_LENGTH;
		return getValue(values,start,finish,maleTable);
	}
	private String getFemaleValue(String format, long[] values, String femaleTable) {
		int start = getStartIndex(format,femaleTable);
		int finish = start+FEMALE_LENGTH;
		return getValue(values,start,finish,femaleTable);
	}
	private String getValue(long[] values, int start, int finish,
			String table) {
		String rowId = getIndex(values,start,finish);
		return getValueFromDB(table, rowId);
	}

	private String getValueFromDB(String table, String rowId) {
		try {
			return (String)dao.selectDataWithWhereClause(new String[]{VALUE}, new String[]{TypesNames.STRING}, table, WHERE_CLAUSE+rowId).get(0)[0];
		} catch (DAOSelectException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private String getIndex(long[] values, int start, int finish) {
		String response = ""; //$NON-NLS-1$
		for(int i=start;i<finish;i++)
			response+=values[i];
		return response;
	}
	private int getStartIndex(String format, String table) {
		int response = 0;
		boolean flag = false;
		String values[]=format.split(" "); //$NON-NLS-1$
		for(int i=0;i<values.length && !flag;i++)
			if(values[i].equals(table))
				flag = true;
			else if(values[i].equals(CAPITAL) || values[i].equals(DONEC))
				response++;
			else if(values[i].equals(FEMALE_TABLE) || values[i].equals(DONEF))
				response+=FEMALE_LENGTH;
			else if(values[i].equals(MALE_TABLE) || values[i].equals(DONEM))
				response+=MALE_LENGTH;
			else if(values[i].equals(LAST_TABLE) || values[i].equals(DONEL))
				response+=LAST_LENGTH;
		return response;
	}
	public String generateNextStringValue()
		throws UnsupportedDataException, GeneratorException {
		String response = ""; //$NON-NLS-1$
		String format = keyset.get(random.nextInt(keyset.size()));
		CombinationGenerator generator = generators.get(format);
		long values[] = generator.getNextValues();
		response = format;
		while(format.contains(FEMALE_TABLE)){
			response = response.replaceFirst(FEMALE_TABLE,getFemaleValue(format,values,FEMALE_TABLE));
			format = format.replaceFirst(FEMALE_TABLE,DONEF);
		}
		while(format.contains(MALE_TABLE)){
			response = response.replaceFirst(MALE_TABLE,getMaleValue(format,values,MALE_TABLE));
			format = format.replaceFirst(MALE_TABLE,DONEM);
		}
		while(format.contains(LAST_TABLE)){
			response = response.replaceFirst(LAST_TABLE,getLastValue(format,values,LAST_TABLE));
			format = format.replaceFirst(LAST_TABLE,DONEL);
		}
		while(format.contains(CAPITAL)){
			response = response.replaceFirst(CAPITAL,getCapitalValue(format,values));
			format = format.replaceFirst(CAPITAL,DONEC);
		}
		return response.substring(0,response.length()-1);
	}
	public String[] getAttributes() {
		return attributes;
	}
	public Hashtable<String, Object> getAttributesValues() {
		return attributeValues;
	}
	public List<String> getTypes() {
		return types;
	}
	
	public void addAttributeValue(String attribute, Object value)
		throws UnsupportedAttributeValueException, UnsupportedAttribute {
		if((attribute.equals(FEMALE_PERCENTAGE) || attribute.equals(MALE_PERCENTAGE)) 
				&& !(((String)value).toUpperCase().equals(YES) ||((String)value).toUpperCase().equals(NO) ))
			throw new UnsupportedAttributeValueException(YES_NO_MESSAGE);
		if(attribute.equals(FORMAT_EXPLANATION) && !isValidFormat((String)value))
			throw new UnsupportedAttributeValueException(NAME_LAST_NAME_MESSAGE);
		attributeValues.put(attribute,value);
	}
	
	private boolean isValidFormat(String value) {
		return value.contains(NAME) || value.contains(LAST_NAME) || value.contains(INITIAL);
	}
	
	@Override
	public void doneConfiguration() throws UnsupportedAttributeValueException{
		Object female = attributeValues.get(FEMALE_PERCENTAGE);
		Object male = attributeValues.get(MALE_PERCENTAGE);
		if(female == null && male == null)
			throw new UnsupportedAttributeValueException(MALE_OR_FEMALE_MESSAGE);
		femalePercentage = ((String)female).toUpperCase().equals(YES);
		malePercentage = ((String)male).toUpperCase().equals(YES);
		if(!femalePercentage  && !malePercentage)
			throw new UnsupportedAttributeValueException(MALE_OR_FEMALE_MESSAGE);
		Object format1= attributeValues.get(FORMAT_EXPLANATION);
		if(format1 == null || ((String)format1).equals("")) //$NON-NLS-1$
			throw new UnsupportedAttributeValueException(NAME_FORMAT_MESSAGE);
		if(format1 != null)
			if(femalePercentage){
				CombinationGenerator generator = new CombinationGenerator();
				String format = getFormat((String)format1,true);
				generator.generateValues(generatorValues(getGeneratorValues(format)));
				generators.put(format,generator);
			}
			if(malePercentage){
				CombinationGenerator generator = new CombinationGenerator();
				String format = getFormat((String)format1,false);
				generator.generateValues(generatorValues(getGeneratorValues(format)));
				generators.put(format,generator);
			}
		generateKeySet();
	}
	private int[] getGeneratorValues(String format1) {
		int total = getArraySize(format1);
		int[] response = new int[total];
		int i=0;
		while(format1.contains(FEMALE_TABLE)){
			format1 = format1.replaceFirst(FEMALE_TABLE, "*"); //$NON-NLS-1$
			response[i] = FEMALE;
			i++;
		}
		while(format1.contains(MALE_TABLE)){
			format1 = format1.replaceFirst(MALE_TABLE, "*"); //$NON-NLS-1$
			response[i] = MALE;
			i++;
		}
		while(format1.contains(LAST_TABLE)){
			format1 = format1.replaceFirst(LAST_TABLE, "*"); //$NON-NLS-1$
			response[i] = LAST;
			i++;
		}
		while(format1.contains(CAPITAL)){
			format1 = format1.replaceFirst(CAPITAL, "*"); //$NON-NLS-1$
			response[i] = CHAR;
			i++;
		}
		return response;
	}
	private int getArraySize(String format1) {
		int response = 0;
		while(format1.contains(FEMALE_TABLE)){
			format1 = format1.replaceFirst(FEMALE_TABLE, "*"); //$NON-NLS-1$
			response++;
		}
		while(format1.contains(MALE_TABLE)){
			format1 = format1.replaceFirst(MALE_TABLE, "*"); //$NON-NLS-1$
			response++;
		}
		while(format1.contains(LAST_TABLE)){
			format1 = format1.replaceFirst(LAST_TABLE, "*"); //$NON-NLS-1$
			response++;
		}
		while(format1.contains(CAPITAL)){
			format1 = format1.replaceFirst(CAPITAL, "*"); //$NON-NLS-1$
			response++;
		}
		return response;
	}
	private Object[] generatorValues(int []types){
		List<Long> values = new LinkedList<Long>();
		for(int i=0;i<types.length;i++)
			if(types[i] == FEMALE)
				for(int j=0;j<FEMALE_LENGTH;j++)
					values.add(new Long(FEMALE_LENGTH_DIGITS));
			else if(types[i] == MALE)
				for(int j=0;j<MALE_LENGTH;j++)
					values.add(new Long(MALE_LENGTH_DIGITS));
			else if(types[i] == LAST)
				for(int j=0;j<LAST_LENGTH;j++)
					values.add(new Long(LAST_LENGTH_DIGITS));
			else if(types[i] == CHAR)
				values.add(new Long(CAPITAL_LETTERS_DIGITS));
		return values.toArray();
	}
	private void generateKeySet() {
		keyset = new LinkedList<String>();
		Iterator<String> iterator = generators.keySet().iterator();
		while(iterator.hasNext())
			keyset.add(iterator.next());
	}
	
	private String getFormat(String format, boolean female) {
		String response = format+generators.size();
		if(female){
			while(response.contains(NAME))
				response=response.replace(NAME,FEMALE_TABLE);
		}else{
			while(response.contains(NAME))
				response=response.replace(NAME,MALE_TABLE);
		}
		while(response.contains(LAST_NAME))
			response = response.replace(LAST_NAME,LAST_TABLE);
		while(response.contains(INITIAL))
			response = response.replace(INITIAL,CAPITAL);
		return response;
	}
	
	public boolean generateNextBooleanValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+BOOL_MESSAGE);
	}
	
	public Date generateNextDateUniqueValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+DATE_MESSAGE);
	}
	
	public Date generateNextDateValue() throws UnsupportedDataException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+DATE_MESSAGE);
	}
	
	public double generateNextDoubleUniqueValue()throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+DOUBLE_MESSAGE);
	}
	
	public double generateNextDoubleValue()throws UnsupportedDataException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+DOUBLE_MESSAGE);
	}
	
	public int generateNextIntUniqueValue()throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+INTEGER_MESSAGE);
	}
	
	public int generateNextIntValue()throws UnsupportedDataException {
		throw new UnsupportedDataException(NAME_GENERATOR_MESSAGE+INTEGER_MESSAGE);
	}

	@Override
	public long getMaximumUniqueValues() {
		long res = 1;
		String format = (String)attributeValues.get(FORMAT_EXPLANATION);
		while(format.contains(NAME)){
			res*=1000;
			format = format.replaceFirst(NAME,"+");
		}
		while(format.contains(INITIAL)){
			res*=26;
			format = format.replaceFirst(INITIAL,"+");
		}
		while(format.contains(LAST_NAME)){
			res*=1000;
			format = format.replaceFirst(LAST_NAME,"+");
		}
		return res;
	}
	
}
