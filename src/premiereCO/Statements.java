package premiereCO;

import java.sql.*;

public class Statements {
	private Statement stmt;
	
	public Statements(Connection server) throws SQLException {
		stmt = server.createStatement();
	}
	
	public void switchDatabase(String newDatabaseName) throws SQLException {
		stmt.execute("USE " + newDatabaseName);
	}
	
	public ResultSet selectAllFromTable(String tableName) throws SQLException{
		return stmt.executeQuery("SELECT * FROM " + tableName);
	}
}
