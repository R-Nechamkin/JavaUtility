package premiereCO;

import java.sql.*;
import java.util.Arrays;

public class TableQuerier {
	
	
	
	
	private String tableName;
	private Statements stmts;



	public TableQuerier(String tableName, Statements stmts, DatabaseMetaData dbMeta) {
		this.tableName = tableName;
		this.stmts = stmts;
	}
	
	public String printMetaAndData(DatabaseMetaData dbMeta) throws SQLException {
		StringBuilder sb = new StringBuilder();

		sb.append("Table name: " + tableName + "\n");

		ResultSet columns = dbMeta.getColumns(null, "dbo", tableName, null);

		while(columns.next()) {
			String colName = columns.getString("COLUMN_NAME");
			String dataTypeName = columns.getString("TYPE_NAME");
			sb.append("Column: " + colName + "\t\tDataType: " + dataTypeName + "\n");

		}
		sb.append("\n\n");
		sb.append("Printing data:\n");

		columns = dbMeta.getColumns(null, "dbo", tableName, null);
		while(columns.next()) {
			sb.append(columns.getString("COLUMN_NAME") + "\t\t");
		}


		ResultSet data = stmts.selectAllFromTable(tableName);
		sb.append("\n");
		sb.append(Querier.printResultSetOfEntireTable(dbMeta, "dbo", data, tableName));
		return sb.toString();
	}
	
}




