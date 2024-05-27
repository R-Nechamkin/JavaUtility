package premiereCO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static void main(String[] args) {
		final String DATABASE_URL =
				 "jdbc:sqlserver://localhost;" 
		        + "integratedSecurity=true;"
		        + "encrypt=true;"
		        + "TrustServerCertificate=true";
		
	 	try (Connection dbConnection = DriverManager.getConnection(DATABASE_URL);){
			 
			 Statement stmt = dbConnection.createStatement();
			 stmt.execute("USE PREMIERECO");

	 		dbConnection.setAutoCommit(false);
	 		
	 		DatabaseMetaData dbMeta = dbConnection.getMetaData();
	 		
	 		
	 		  ResultSet resultSet = dbMeta.getTables(null, "dbo", null, new String[]{"TABLE"});

	            while (resultSet.next()) {
	                String tableName = resultSet.getString("TABLE_NAME");
	                System.out.println("Table name: " + tableName);
	                
	                ResultSet columns = dbMeta.getColumns(null, "dbo", tableName, null);
	                
	                ResultSet data = getDataFromTable(stmt, tableName);
	                
	                while(columns.next()) {
	                	String colName = columns.getString("COLUMN_NAME");
	                	String dataTypeName = columns.getString("TYPE_NAME");
	                	System.out.println("\tColumn: " + colName + "\t\tDataType: " + dataTypeName);
	                	
	                }
	                
	        		columns = dbMeta.getColumns(null, "dbo", tableName, null);
	        		while(columns.next()) {
	        			System.out.print(columns.getString("COLUMN_NAME") + "\t\t");
	        		}
	        		System.out.println();
	        		
                	
	            }
	 		

			 /*
			  * Use either RowSet or ResultSet to read all data from the database.

Utilize ResultSetMetaData to retrieve and print the following information about the database structure:

The name of each table.

The names of all columns in each table.

The data type of each column.

After printing the structure of each table, proceed to print all the data contained within the table.

Be mindful to use RowSetMetaData as a guide when iterating through the table rows to print the data.
			  */
			 
			 
		 }
		 catch(SQLException e) {
			 System.out.println("Something went wrong with the database");
			 e.printStackTrace();
		 }
	 	
		 System.out.println("Connection closed.");

	}
	
	private static String getColData(ResultSet tableData, ResultSet colMeta) throws SQLException {
		int index = colMeta.getInt("ORDINAL_POSITION");
		return index + "";
	}

//	private static void printColNames(ResultSet columns) throws SQLException {
//
//	}

	private static String tableDataToString(String tableName, Statement stmt, DatabaseMetaData dbMeta) throws SQLException{
		StringBuilder sb = new StringBuilder();
		
		ResultSet data = getDataFromTable(stmt, tableName);
		
        while(data.next()) {
        	ResultSet columns = dbMeta.getColumns(null, "dbo", tableName, null);
        	while(columns.next()) {
        		sb.append(getColData(data, columns) + "\t");
        	}
        	sb.append("\n");
        }
        return sb.toString();
		
	}
	
	public static ResultSet getDataFromTable(Statement stmt, String tableName) throws SQLException {
		String sql = "SELECT * FROM " + tableName;
		return stmt.executeQuery(sql);
	}

}
