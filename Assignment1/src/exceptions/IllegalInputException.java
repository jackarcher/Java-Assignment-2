package exceptions;

public class IllegalInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7285392553071122611L;

	public IllegalInputException() {
		super();
	}

	public IllegalInputException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalInputException(String message) {
		super(message);
	}

	public IllegalInputException(Throwable cause) {
		super(cause);
	}

}
