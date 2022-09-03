package fr.alexandreklotz.quickdesk.error;

public class RolesException extends Exception {

    public RolesException() {
        super();
    }

    public RolesException(String message) {
        super(message);
    }

    public RolesException(String message, Throwable cause) {
        super(message, cause);
    }

    public RolesException(Throwable cause) {
        super(cause);
    }

    protected RolesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
