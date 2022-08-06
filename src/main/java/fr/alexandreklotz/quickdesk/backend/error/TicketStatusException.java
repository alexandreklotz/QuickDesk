package fr.alexandreklotz.quickdesk.backend.error;

public class TicketStatusException extends Exception {

    public TicketStatusException() {
        super();
    }

    public TicketStatusException(String message) {
        super(message);
    }

    public TicketStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketStatusException(Throwable cause) {
        super(cause);
    }

    protected TicketStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
