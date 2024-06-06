package premiereCO;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
			System.out.println(dq.printMetaAndData());


			demonstratePreparedStatements("PART", "PRICE", dbConnection);

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

	private static void demonstratePreparedStatements(String table, String col, Connection dbConnection) throws SQLException {
		System.out.println("Let's demonstrate prepared statements using the " + col + " of the " + table + " table.");
		Scanner keyboard = new Scanner(System.in);

		String sql = "SELECT * FROM " + table + " WHERE " + col + " >= ?"; 
		PreparedStatement stmt = dbConnection.prepareStatement(sql);

		String input = null;
		do {
			try {
				System.out.println("Enter the minimum value that " + col + " can take, or enter Q to quit");
				input = keyboard.nextLine();
				double minValue = Double.parseDouble(input);

				stmt.setDouble(1, minValue);

				ResultSet rs = stmt.executeQuery();
				String result = Querier.printResultSetOfEntireTable(dbConnection.getMetaData(), "dbo", rs, table);
				System.out.println("Printing the records with a minimum value of " + minValue);
				System.out.println(result);
			}
			catch (NumberFormatException e){
				if(input == null || !input.equals("Q")) {
					System.out.println("That was not a valid double value. Enter a number, or enter Q to quit");
				}
			}
		}
		while(!input.equals("Q"));

	}

	
	private static void demonstrateSavepoints(Connection dbConnection) {
		System.out.println("Let's add some parts to our store.");
		
		System.out.println("Let's add the first part.");
		
		/*
		 * Task 3: Working with Savepoints and Rollbacks

Illustrate the use of savepoints and rollbacks in a transaction.

Show how you can set a savepoint during a transaction, make some changes, 
and then rollback to the savepoint, effectively undoing the changes.

		 */
	}
	

	
	

}
