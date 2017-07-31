package edu.eci.pgr.tests.concrete;

import edu.eci.pgr.rowGenerators.RowGenerator;
import edu.eci.pgr.rowGenerators.concrete.MySqlRowGenerator;
import edu.eci.pgr.tests.generic.RowGeneratorTest;

public class SqlServerRowGeneratorTest extends RowGeneratorTest {

	@Override
	public String getDriverConcrete() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	public String getEngineConcrete() {
		return "SQLSERVER";
	}

	@Override
	public RowGenerator getRowGeneratorConcrete() {
		return new MySqlRowGenerator();
	}

	@Override
	public String getUrl1Concrete() {
		return "jdbc:sqlserver://localhost:1433;databaseName=testpgr1";
	}

	@Override
	public String getUrl2Concrete() {
		return "jdbc:sqlserver://localhost:1433;databaseName=testpgr2";
	}

	@Override
	public String getUrl3Concrete() {
		return "jdbc:sqlserver://localhost:1433;databaseName=testpgr3";
	}

	@Override
	public String getUrl4Concrete() {
		return "jdbc:sqlserver://localhost:1433;databaseName=testpgr4";
	}
	
}
