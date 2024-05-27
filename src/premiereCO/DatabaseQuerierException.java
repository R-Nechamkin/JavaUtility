package premiereCO;

public class DatabaseQuerierException extends Exception {
	
	private final static String EXCEPTION_MESSAGE = "An error occurred while checking the database."; 
	
	public DatabaseQuerierException() {
		super(EXCEPTION_MESSAGE);
	}

	public DatabaseQuerierException(String message) {
		super(message);
	}

	public DatabaseQuerierException(Throwable cause) {
		super(cause);
	}

	public DatabaseQuerierException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseQuerierException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
