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
			 
			 DatabaseQuerier dq = new DatabaseQuerier(dbConnection);
			 dq.switchDatabase("PREMIERECO");
			 System.out.println(dq.databaseMetaToString());
	 		

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
		 } catch (DatabaseQuerierException e) {
			System.out.println("Something went wrong while we were querying the database.");
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
