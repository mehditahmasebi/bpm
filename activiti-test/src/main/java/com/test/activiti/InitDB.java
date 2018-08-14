package com.test.activiti;
import org.activiti.engine.impl.db.DbSchemaCreate;
import org.activiti.engine.impl.db.DbSchemaDrop;
import org.apache.log4j.Logger;

//@Component("initDB")
public class InitDB {
	Logger logger = Logger.getLogger(InitDB.class);
	
	public InitDB()
	{
		logger.info("Databse Initialization");
	}
	
	public void initDatabase()
	{
		//need activiti.cfg.xml
//		DbSchemaDrop.main(null);
		DbSchemaCreate.main(null);
	}
	
	public static void main(String[] args) {
		new InitDB().initDatabase();
	}
	
	

}
