package premiereCO;

import java.sql.*;

public class Statements {
	private PreparedStatement switchDatabase;
	private PreparedStatement selectAll;
	
	public Statements(Connection server) throws SQLException {
		switchDatabase = server.prepareStatement("USE ?");
		selectAll = server.prepareStatement("SELECT * FROM ?");
	}
	
	public void switchDatabase(String newDatabaseName) throws SQLException {
		switchDatabase.setString(1, newDatabaseName);
		switchDatabase.execute();
	}
	
	public ResultSet selectAllFromTable(String tableName) throws SQLException{
		selectAll.setString(1, tableName);
		return selectAll.executeQuery();
	}
}
