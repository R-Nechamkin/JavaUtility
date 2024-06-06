package premiereCO;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;
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
			demonstrateSavepointsWithPartTable(dbConnection);
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

	
	private static void demonstrateSavepointsWithPartTable(Connection dbConnection) throws SQLException {
		Statement  stmt = dbConnection.createStatement();

		System.out.println("Let's add some parts to our store.");

		//give user choice between adding another part, creating savepoint, rolling back to savepoint, or quitting
		Scanner sc = new Scanner(System.in);
		Deque<Savepoint> savepoints = new ArrayDeque<>();
		String input = "";
		while(!input.equals("Q")) {
			System.out.println("Enter 'A' to add a part, 'S' to create a savepoint, 'R' to rollback to the savepoint, or 'Q' to quit and commit your changes.");
			input = sc.nextLine();

			switch(input) {
				case "A":
					System.out.println("Let's add a part.");
					Part p2 = getPart(System.in, System.out);
					stmt.execute(p2.createInsertStatement());
					break;
				case "S":
					System.out.println("What would you like to name the savepoint?");
					System.out.println("Creating a savepoint.");
					Savepoint savepoint = dbConnection.setSavepoint(sc.nextLine());
					savepoints.push(savepoint);
					break;
				case "R":
					if(savepoints.isEmpty()) {
						System.out.println("There are no savepoints to rollback to.");
					}
					else {
						dbConnection.rollback();
						System.out.println("Rolled back to the previous savepoint of " + savepoints.peek().getSavepointName());
						savepoints.pop();
					}
					break;
				case "Q":
					System.out.println("Committing and quitting.");
					break;
				default:
					System.out.println("That was not a valid choice.");
					break;
			}
		}
	}
		/*
		 * Task 3: Working with Savepoints and Rollbacks

Illustrate the use of savepoints and rollbacks in a transaction.

Show how you can set a savepoint during a transaction, make some changes, 
and then rollback to the savepoint, effectively undoing the changes.

		 */




	/**
	 * This method will prompt the user for the information needed to create a Part object.
	 * @param in
	 * @param out
	 * @return
	 */

	public static Part getPart(InputStream in, PrintStream out) {
		Scanner sc = new Scanner(in);
		out.println("Enter the part number:");
		String partNum = sc.nextLine();
		out.println("Enter the part description:");
		String description = sc.nextLine();
		out.println("Enter the number of units on hand:");
		int unitsOnHand = sc.nextInt();
		sc.nextLine();
		out.println("Enter the category:");
		String category = sc.nextLine();
		out.println("Enter the warehouse:");
		int warehouse = sc.nextInt();
		sc.nextLine();
		out.println("Enter the price:");
		BigDecimal price = sc.nextBigDecimal();
		sc.nextLine();
		return new Part(partNum, description, unitsOnHand, category, warehouse, price);
	}
	

	
	

}
