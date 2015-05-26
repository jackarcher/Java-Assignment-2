package exceptions;

public class ValidationFailException extends RuntimeException
{

    public ValidationFailException()
    {
	super();
    }

    public ValidationFailException(String message)
    {
	super(message);
    }

    public ValidationFailException(String message, Throwable cause)
    {
	super(message, cause);
    }

    public ValidationFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public ValidationFailException(Throwable cause)
    {
	super(cause);
    }

}
