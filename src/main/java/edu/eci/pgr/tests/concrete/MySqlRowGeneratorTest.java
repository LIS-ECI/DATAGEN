package edu.eci.pgr.tests.concrete;

import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.concrete.MySqlRowGenerator;
import edu.eci.pgr.tests.generic.RowGeneratorTest;

public class MySqlRowGeneratorTest extends RowGeneratorTest {

	@Override
	public String getDriverConcrete() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public String getEngineConcrete() {
		return "MYSQL";
	}

	@Override
	public RowGenerator getRowGeneratorConcrete() {
		return new MySqlRowGenerator();
	}

	@Override
	public String getUrl1Concrete() {
		return "jdbc:mysql://localhost:3306/testpgr111";
	}

	@Override
	public String getUrl2Concrete() {
		return "jdbc:mysql://localhost:3306/testpgr211";
	}

	@Override
	public String getUrl3Concrete() {
		return "jdbc:mysql://localhost:3306/testpgr311";
	}

	@Override
	public String getUrl4Concrete() {
		return "jdbc:mysql://localhost:3306/testpgr4";
	}
	
}
