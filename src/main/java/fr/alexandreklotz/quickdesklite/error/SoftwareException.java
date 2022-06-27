package fr.alexandreklotz.quickdesklite.error;

public class SoftwareException extends Exception {

    public SoftwareException() {
        super();
    }

    public SoftwareException(String message) {
        super(message);
    }

    public SoftwareException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoftwareException(Throwable cause) {
        super(cause);
    }

    protected SoftwareException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
