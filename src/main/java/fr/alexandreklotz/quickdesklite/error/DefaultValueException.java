package fr.alexandreklotz.quickdesklite.error;

public class DefaultValueException extends Exception {

    public DefaultValueException() {
        super();
    }

    public DefaultValueException(String message) {
        super(message);
    }

    public DefaultValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultValueException(Throwable cause) {
        super(cause);
    }

    protected DefaultValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
