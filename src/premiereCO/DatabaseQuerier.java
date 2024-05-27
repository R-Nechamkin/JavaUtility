package premiereCO;

import java.sql.*;

public class DatabaseQuerier {
	Connection server;
	String dbName;
	Statements stmts;
	
	public DatabaseQuerier(Connection server) throws DatabaseQuerierException {
		this.server = server;
		System.err.println("Connection opened.");
		try {
			server.setAutoCommit(false);
			stmts = new Statements(this.server);
		}
		catch(SQLException e){
			e.printStackTrace();
			throw new DatabaseQuerierException();
		}
	}
	
	public void switchDatabase(String dbName) throws DatabaseQuerierException{
		this.dbName = dbName;
		try {
			stmts.switchDatabase(dbName);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseQuerierException();
		}
	}
	
	public String databaseMetaToString() throws DatabaseQuerierException{
		try {
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseQuerierException();
		}
	}

}
