package fr.alexandreklotz.quickdesklite.error;

public class TicketTypeException extends Exception {

    public TicketTypeException() {
        super();
    }

    public TicketTypeException(String message) {
        super(message);
    }

    public TicketTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeException(Throwable cause) {
        super(cause);
    }

    protected TicketTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
