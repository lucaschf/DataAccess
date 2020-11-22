package tsi.too.dataaccess.model;

@SuppressWarnings("serial")
public class DataAccessObjectException extends Exception{

	public DataAccessObjectException() {
		super();
	}

	public DataAccessObjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataAccessObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessObjectException(String message) {
		super(message);
	}

	public DataAccessObjectException(Throwable cause) {
		super(cause);
	}
}
