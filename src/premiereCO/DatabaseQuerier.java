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
	
	public String printMetaAndData() throws DatabaseQuerierException{
		try {
		
			StringBuilder sb = new StringBuilder();
			DatabaseMetaData dbMeta = server.getMetaData();


			ResultSet resultSet = dbMeta.getTables(null, "dbo", null, new String[]{"TABLE"});

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				TableQuerier tq  = new TableQuerier(tableName, stmts, dbMeta);
				sb.append(tq.printMetaAndData(dbMeta) + "\n\n\n");
			}
			
			return sb.toString();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseQuerierException();
		}
	}

}
