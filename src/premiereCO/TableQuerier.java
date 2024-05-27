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
		while(data.next()) {
			columns = dbMeta.getColumns(null, "dbo", tableName, null);
			while(columns.next()) {
				sb.append("\t");
				try {
					int dataType = columns.getInt("DATA_TYPE");
					String colName = columns.getString("COLUMN_NAME");
					sb.append(printCellData(dataType, colName, data) + "\t");
				} catch (NullPointerException e) {
					if (columns.getInt("NULLABLE") != 0){
						sb.append("N/A");
					}
				} 
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private String printCellData(int dataType, String colName, ResultSet data) throws NullPointerException, SQLException {
		switch (dataType) {
		case java.sql.Types.ARRAY:
			return data.getArray(colName).toString();
		case java.sql.Types.BIGINT:
			return Long.toString(data.getLong(colName));
		case java.sql.Types.BINARY:
			return Arrays.toString(data.getBytes(colName));
		case java.sql.Types.BIT:
			return Boolean.toString(data.getBoolean(colName));
		case java.sql.Types.BLOB:
			return data.getBlob(colName).toString();
		case java.sql.Types.BOOLEAN:
			return Boolean.toString(data.getBoolean(colName));
		case java.sql.Types.CHAR:
			return data.getString(colName);
		case java.sql.Types.CLOB:
			return data.getClob(colName).toString();
		case java.sql.Types.DATALINK:
			return data.getURL(colName).toString();
		case java.sql.Types.DATE:
			return data.getDate(colName).toString();
		case java.sql.Types.DECIMAL:
			return data.getBigDecimal(colName).toString();
		case java.sql.Types.DISTINCT:
			return data.getObject(colName).toString();
		case java.sql.Types.DOUBLE:
			return Double.toString(data.getDouble(colName));
		case java.sql.Types.FLOAT:
			return Float.toString(data.getFloat(colName));
		case java.sql.Types.INTEGER:
			return Integer.toString(data.getInt(colName));
		case java.sql.Types.JAVA_OBJECT:
			return data.getObject(colName).toString();
		case java.sql.Types.LONGNVARCHAR:
			return data.getNString(colName);
		case java.sql.Types.LONGVARBINARY:
			return Arrays.toString(data.getBytes(colName));
		case java.sql.Types.LONGVARCHAR:
			return data.getString(colName);
		case java.sql.Types.NCHAR:
			return data.getNString(colName);
		case java.sql.Types.NCLOB:
			return data.getNClob(colName).toString();
		case java.sql.Types.NULL:
			return "null";
		case java.sql.Types.NUMERIC:
			return data.getBigDecimal(colName).toString();
		case java.sql.Types.NVARCHAR:
			return data.getNString(colName);
		case java.sql.Types.OTHER:
			return data.getObject(colName).toString();
		case java.sql.Types.REAL:
			return Float.toString(data.getFloat(colName));
		case java.sql.Types.REF:
			return data.getRef(colName).toString();
		case java.sql.Types.REF_CURSOR:
			// REF_CURSOR is usually used to retrieve a ResultSet
			return "Cursor";
		case java.sql.Types.ROWID:
			return data.getRowId(colName).toString();
		case java.sql.Types.SMALLINT:
			return Short.toString(data.getShort(colName));
		case java.sql.Types.SQLXML:
			return data.getSQLXML(colName).toString();
		case java.sql.Types.STRUCT:
			return data.getObject(colName).toString();
		case java.sql.Types.TIME:
			return data.getTime(colName).toString();
		case java.sql.Types.TIME_WITH_TIMEZONE:
			return data.getTime(colName).toString(); // Handle timezone appropriately
		case java.sql.Types.TIMESTAMP:
			return data.getTimestamp(colName).toString();
		case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
			return data.getTimestamp(colName).toString(); // Handle timezone appropriately
		case java.sql.Types.TINYINT:
			return Byte.toString(data.getByte(colName));
		case java.sql.Types.VARBINARY:
			return Arrays.toString(data.getBytes(colName));
		case java.sql.Types.VARCHAR:
			return data.getString(colName);
		default:
			throw new SQLException("Unsupported data type: " + dataType);
		}
	}

}




