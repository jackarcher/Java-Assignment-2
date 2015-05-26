package exceptions;

public class ValidationFail extends RuntimeException
{

    public ValidationFail()
    {
	super();
    }

    public ValidationFail(String message)
    {
	super(message);
    }

    public ValidationFail(String message, Throwable cause)
    {
	super(message, cause);
    }

    public ValidationFail(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public ValidationFail(Throwable cause)
    {
	super(cause);
    }

}
