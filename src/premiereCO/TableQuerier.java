package premiereCO;

import java.sql.*;

public class TableQuerier {
	private String tableName;
	private Statements stmts;

	
	
	public TableQuerier(String tableName, Statements stmts, DatabaseMetaData dbMeta) {
		this.tableName = tableName;
		this.stmts = stmts;
	}
	
	public String printMetaAndData(DatabaseMetaData dbMeta) throws SQLException {
		StringBuilder sb = new StringBuilder();
		
        sb.append("Table name: " + tableName);
        
        ResultSet columns = dbMeta.getColumns(null, "dbo", tableName, null);
                
        while(columns.next()) {
        	String colName = columns.getString("COLUMN_NAME");
        	String dataTypeName = columns.getString("TYPE_NAME");
        	sb.append("\tColumn: " + colName + "\t\tDataType: " + dataTypeName);
        	
        }
        
		columns = dbMeta.getColumns(null, "dbo", tableName, null);
		while(columns.next()) {
			sb.append(columns.getString("COLUMN_NAME") + "\t\t");
		}
		
		
        ResultSet data = stmts.selectAllFromTable(tableName);
    	while(data.next()) {
    		while(columns.next()) {
    			int dataType = columns.getInt("DATA_TYPE");
    			String colName = columns.getString("COLUMN_NAME");
    			sb.append(printCellData(dataType, colName) + "\t");
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
	}

	private String printCellData(int dataType, String colName) {
		//TODO: Create switch statement
	}
	
	
	
}
