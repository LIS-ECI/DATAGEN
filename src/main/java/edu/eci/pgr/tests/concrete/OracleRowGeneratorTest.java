package edu.eci.pgr.tests.concrete;

import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.concrete.OracleRowGenerator;
import edu.eci.pgr.tests.generic.RowGeneratorTest;

public class OracleRowGeneratorTest extends RowGeneratorTest {

	@Override
	public String getDriverConcrete() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	@Override
	public String getEngineConcrete() {
		return "ORACLE";
	}

	@Override
	public RowGenerator getRowGeneratorConcrete(){ 
		return new OracleRowGenerator();
	}

	@Override
	public String getUrl1Concrete() {
		return "jdbc:oracle:thin:@localhost:1521:testpgr1";
	}

	@Override
	public String getUrl2Concrete() {
		return "jdbc:oracle:thin:@localhost:1521:testpgr2";
	}

	@Override
	public String getUrl3Concrete() {
		return "jdbc:oracle:thin:@localhost:1521:testpgr1";
	}

	@Override
	public String getUrl4Concrete() {
		return "jdbc:oracle:thin:@localhost:1521:testpgr1";
	}

}
