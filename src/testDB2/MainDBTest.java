package testDB2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainDBTest {

	public static void main(String[] args) {
	 final String DATABASE_URL =
			 "jdbc:sqlserver://localhost;" 
	        + "integratedSecurity=true;"
	        + "encrypt=true;"
	        + "TrustServerCertificate=true";
	 Connection dbConnection ;
	 try {
		 dbConnection = DriverManager.getConnection(DATABASE_URL);
		 dbConnection.setAutoCommit(false);
		 
		 Statement stmnt = dbConnection.createStatement();

		 
		 System.out.println("connected successfully");
		 stmnt.execute("DROP DATABASE Touro");

		 stmnt.execute("CREATE DATABASE Touro");
		 System.out.println("Created database");
		 stmnt.execute("Use TOURO");
		 
		 String createTable1 = "CREATE TABLE Teachers("
				+ "teacher_id int PRIMARY KEY IDENTITY(1,1),"
				+ "f_name varchar(32) NOT NULL,"
				+ "l_name varchar(32) NOT NULL,"
				+ "age int NOT NULL,"
				+ "number varchar(10) NOT NULL,"
				+ "email varchar(32) NOT NULL,"
				+ "hair_color varchar(32) NOT NULL"
				+ ")";
		
		 String createTable2 = "CREATE Table Courses(\r\n"
		 		+ "	course_id int PRIMARY KEY IDENTITY(1,1),\r\n"
		 		+ "	teacher_id int references Teachers,\r\n"
		 		+ "	name varchar(32) NOT NULL,\r\n"
		 		+ "	dept varchar(32) NOT NULL,\r\n"
		 		+ "	semester varchar(32) NOT NULL,\r\n"
		 		+ "	campus varchar(32) NOT NULL,\r\n"
		 		+ "	room_number varchar(8) NOT NULL\r\n"
		 		+ ")\r\n";
		 
		 
		 stmnt.execute(createTable1);
		 stmnt.execute(createTable2);
		 System.out.println("Created tables");
		 
		 String insertDummyTeachers = "INSERT INTO Teachers (f_name, l_name, age, number, email, hair_color) VALUES\r\n"
		 		+ "('John', 'Doe', 35, '1234567890', 'johndoe@email.com', 'Brown'),\r\n"
		 		+ "('Jane', 'Smith', 28, '0987654321', 'janesmith@email.com', 'Blonde'),\r\n"
		 		+ "('Alice', 'Johnson', 40, '1122334455', 'alicejohnson@email.com', 'Black'),\r\n"
		 		+ "('Bob', 'Williams', 45, '5544332211', 'bobwilliams@email.com', 'Red'),\r\n"
		 		+ "('Carol', 'Brown', 32, '6677889900', 'carolbrown@email.com', 'Brown'),\r\n"
		 		+ "('David', 'Jones', 50, '2233445566', 'davidjones@email.com', 'Black'),\r\n"
		 		+ "('Emily', 'Taylor', 38, '7788990011', 'emilytaylor@email.com', 'Blonde'),\r\n"
		 		+ "('Frank', 'Clark', 33, '8899001122', 'frankclark@email.com', 'Red'),\r\n"
		 		+ "('Grace', 'Lee', 42, '9900112233', 'gracelee@email.com', 'Black'),\r\n"
		 		+ "('Henry', 'Scott', 29, '0011223344', 'henryscott@email.com', 'Brown');";
		 
		 String insertDummyCourses = "INSERT INTO Courses (teacher_id, name, dept, semester, campus, room_number) VALUES\r\n"
		 		+ "(1, 'Mathematics 101', 'Math', 'Fall', 'Main', '101'),\r\n"
		 		+ "(1, 'Mathematics 201', 'Math', 'Spring', 'Main', '201'),\r\n"
		 		+ "(1, 'Mathematics 301', 'Math', 'Fall', 'Main', '301'),\r\n"
		 		+ "(2, 'History 101', 'History', 'Spring', 'North', '102'),\r\n"
		 		+ "(2, 'History 201', 'History', 'Fall', 'North', '202'),\r\n"
		 		+ "(3, 'Biology 101', 'Biology', 'Fall', 'South', '103'),\r\n"
		 		+ "(3, 'Biology 201', 'Biology', 'Spring', 'South', '203'),\r\n"
		 		+ "(4, 'Chemistry 101', 'Chemistry', 'Spring', 'West', '104'),\r\n"
		 		+ "(5, 'Physics 101', 'Physics', 'Fall', 'East', '105'),\r\n"
		 		+ "(5, 'Physics 201', 'Physics', 'Spring', 'East', '205'),\r\n"
		 		+ "(6, 'Economics 101', 'Economics', 'Fall', 'Main', '106'),\r\n"
		 		+ "(7, 'Literature 101', 'Literature', 'Spring', 'North', '107'),\r\n"
		 		+ "(8, 'Computer Science 101', 'Computer Science', 'Fall', 'South', '108'),\r\n"
		 		+ "(9, 'Psychology 101', 'Psychology', 'Spring', 'West', '109'),\r\n"
		 		+ "(10, 'Sociology 101', 'Sociology', 'Fall', 'East', '110');";
		 
		 
		 stmnt.execute(insertDummyTeachers);
		 stmnt.execute(insertDummyCourses);
		 
		 System.out.println("Inserted data");
		 
		 
		 ResultSet result = stmnt.executeQuery("SELECT * FROM TEACHERS");
		 printResult(result);
		 
		 
		 dbConnection.close(); 
		 System.out.println("connection closed");
		 
	 }
	 catch (SQLException sqlE) {
		 System.out.println("encountered a problem " + sqlE.getMessage());
	 }
	}
	
	public static void printResult(ResultSet result) throws SQLException {
		while(result.next()) {
			System.out.println(result.toString());
		}
	}

}
