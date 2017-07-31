package edu.eci.pgr.tests.concrete;

import edu.eci.pgr.dataGenerators.DataGenerator;
import edu.eci.pgr.dataGenerators.concrete.DateNormalgenerator;
import edu.eci.pgr.dataGenerators.concrete.NumberNormalGenerator;
import edu.eci.pgr.exceptions.GeneratorException;
import edu.eci.pgr.exceptions.UnsupportedAttribute;
import edu.eci.pgr.exceptions.UnsupportedAttributeValueException;
import edu.eci.pgr.exceptions.UnsupportedDataException;

public class DataGeneratorsTest {
	private  DataGenerator dg;
	
	public DataGeneratorsTest(){}
	
	public static void main(String[] args) {
		DataGeneratorsTest dgt = new DataGeneratorsTest ();
		try {
			dgt.numberNormalGeneratorTest();
			for (int i = 0; i < 100; i++) 
				System.out.println(dgt.dg.generateNextDoubleValue());

		} catch (UnsupportedDataException e) {
			e.printStackTrace();
		} catch (UnsupportedAttributeValueException e) {
			e.printStackTrace();
		} catch (UnsupportedAttribute e) {
			e.printStackTrace();
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
	}

	private void dateNormalGeneratorTest() throws UnsupportedAttributeValueException, UnsupportedAttribute{
		dg = new DateNormalgenerator();
		dg.addAttributeValue("media", "1950");
		dg.addAttributeValue("desviación", "50");
		dg.addAttributeValue("formato", "hh E dd-MM-yy");

	}

	private void numberNormalGeneratorTest() throws UnsupportedAttributeValueException, UnsupportedAttribute{
		dg = new NumberNormalGenerator();
		dg.addAttributeValue("media", "1950");
		dg.addAttributeValue("desviación", "50.4");
		dg.addAttributeValue("digitos decimales", "10");
		dg.addAttributeValue("digitos no decimales", "9");
	}
}
