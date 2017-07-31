package edu.eci.pgr.dataGenerators.concrete;

import java.io.File;
import java.io.FileFilter;
import java.sql.Date;

import edu.eci.pgr.business.types.TypesNames;
import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;
import edu.eci.pgr.i18n.MessageBundleManager;

public class FileGenerator extends DataGenerator {

	private static final String FILE_SIZE_MESSAGE = MessageBundleManager.getString("FileGenerator.0"); //$NON-NLS-1$
	private static final String FILE_SIZE = MessageBundleManager.getString("FileGenerator.1"); //$NON-NLS-1$
	private static final String FILE_EXTENSION_MESSAGE = MessageBundleManager.getString("FileGenerator.2"); //$NON-NLS-1$
	private static final String DIRECTORY_MESSAGE = MessageBundleManager.getString("FileGenerator.3"); //$NON-NLS-1$
	private static final String ADD_ATTRIBUTE_MESSAGE = MessageBundleManager.getString("FileGenerator.4"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	private static final String UNSUPPORTEDMESSAGE = MessageBundleManager.getString("FileGenerator.5") + //$NON-NLS-1$
	MessageBundleManager.getString("FileGenerator.6"); //$NON-NLS-1$
	private static final String UNSUPPORTEDUNIQUEMESSAGE = MessageBundleManager.getString("FileGenerator.7") + //$NON-NLS-1$
	MessageBundleManager.getString("FileGenerator.8"); //$NON-NLS-1$
	private static final String DATE = MessageBundleManager.getString("FileGenerator.9"); //$NON-NLS-1$
	private static final String DOUBLE = MessageBundleManager.getString("FileGenerator.10"); //$NON-NLS-1$
	private static final String INT = MessageBundleManager.getString("FileGenerator.11"); //$NON-NLS-1$
	private static final String STRING = MessageBundleManager.getString("FileGenerator.12"); //$NON-NLS-1$
	private static final String BOOLEAN = MessageBundleManager.getString("FileGenerator.13"); //$NON-NLS-1$
	private static String FOLDERPATH=MessageBundleManager.getString("FileGenerator.14"); //$NON-NLS-1$
	private static String EXTENSION=MessageBundleManager.getString("FileGenerator.15"); //$NON-NLS-1$

	private File folder;
	private File[] files;

	public FileGenerator() {
		super(new String[]{FOLDERPATH,EXTENSION});
		types.add(TypesNames.FILE);
	}

	@Override
	public void addAttributeValue(String attribute, Object value)
	throws UnsupportedAttributeValueException, UnsupportedAttribute {
		if(attribute.equals(EXTENSION) && value.toString().length()>5)
			throw new UnsupportedAttributeValueException(value+ADD_ATTRIBUTE_MESSAGE);
		this.attributeValues.put(attribute,value);
	}

	@Override
	public void doneConfiguration() throws UnsupportedAttributeValueException,
			UnsupportedAttribute {
		try{ 
				this.folder = new File((String) attributeValues.get(FOLDERPATH));
				if(!this.folder.isDirectory())
					throw new UnsupportedAttributeValueException(FOLDERPATH+DIRECTORY_MESSAGE);
				final String ext= (String) attributeValues.get(EXTENSION);
				this.files = this.folder.listFiles(new FileFilter(){
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(ext);
					}

				});
				//validar que en el directorio halla archivos
				if(this.files.length<1)
					throw new UnsupportedAttributeValueException(attributeValues.get(FOLDERPATH)+
							FILE_EXTENSION_MESSAGE+ext);
				boolean correct = true;
				//validar que todos los archivos son de tamaño tal que caben en el tipo de dato de la columna
				for (int i = 0; i < this.files.length && correct; i++) {
					if(this.files[i].length()>type.getRange()){
						correct =false;
						throw new UnsupportedAttributeValueException(attributeValues.get(FOLDERPATH)+FILE_SIZE
								+files[i].getName()+FILE_SIZE_MESSAGE+ type.getName());
					}
				}
		}catch(Exception s){
			throw new UnsupportedAttributeValueException(s.getMessage());
		}
		
	}
	
	@Override
	public boolean generateNextBooleanValue() throws UnsupportedDataException,
	GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+BOOLEAN);
	}

	@Override
	public Date generateNextDateUniqueValue() throws UnsupportedDataException,
	GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+DATE);
	}

	@Override
	public Date generateNextDateValue() throws UnsupportedDataException,
	GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+DATE);
	}

	@Override
	public double generateNextDoubleUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+DOUBLE);
	}

	@Override
	public double generateNextDoubleValue() throws UnsupportedDataException,
	GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+DOUBLE);
	}

	@Override
	public int generateNextIntUniqueValue() throws UnsupportedDataException,
	GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+INT);
	}

	@Override
	public int generateNextIntValue() throws UnsupportedDataException,
	GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDMESSAGE+INT);
	}

	@Override
	public String generateNextStringUniqueValue()
	throws UnsupportedDataException, GeneratorException {
		throw new UnsupportedDataException(UNSUPPORTEDUNIQUEMESSAGE+STRING);
	}

	@Override
	public String generateNextStringValue() throws UnsupportedDataException,
	GeneratorException {
		int pos = (int) (Math.random()*(this.files.length));
		if(pos==this.files.length) pos--;
		return this.files[pos].getPath();
	}

	@Override
	public long getMaximumUniqueValues() {
		return 0;
	}

	
}
