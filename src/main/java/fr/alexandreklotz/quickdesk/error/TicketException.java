package fr.alexandreklotz.quickdesk.error;

public class TicketException extends Exception {

    public TicketException() {
        super();
    }

    public TicketException(String message) {
        super(message);
    }

    public TicketException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketException(Throwable cause) {
        super(cause);
    }

    protected TicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
