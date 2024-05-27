package testDBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainDBTest {

	public static void main(String[] args) {
	 final String DATABASE_URL =
			 "jdbc:sqlserver://localhost;" +
	        "databaseName=TestDB;integratedSecurity=true;encrypt=true;TrustServerCertificate=true";
	 Connection dbConnection ;
	 try {
		 dbConnection = DriverManager.getConnection(DATABASE_URL);
		 dbConnection.setAutoCommit(false);
		 System.out.println("connected successfully");
		 dbConnection.close(); 
		 System.out.println("connection closed");
		 
	 }
	 catch (SQLException sqlE) {
		 System.out.println("encountered a problem " + sqlE.getMessage());
	 }
	}

}
