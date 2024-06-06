package premiereCO;

public abstract class DBTable {

	
	
	/**
	 * Returns a string of SQL which can insert the item into the database.
	 * @return
	 */
	public abstract String createInsertStatement();
}
