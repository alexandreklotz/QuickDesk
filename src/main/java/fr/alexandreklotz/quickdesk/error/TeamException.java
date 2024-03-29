package fr.alexandreklotz.quickdesk.error;

public class TeamException extends Exception {

    public TeamException() {
        super();
    }

    public TeamException(String message) {
        super(message);
    }

    public TeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamException(Throwable cause) {
        super(cause);
    }

    protected TeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
