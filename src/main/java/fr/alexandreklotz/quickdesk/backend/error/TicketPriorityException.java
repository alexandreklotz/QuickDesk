package fr.alexandreklotz.quickdesk.backend.error;

public class TicketPriorityException extends Exception {

    public TicketPriorityException() {
        super();
    }

    public TicketPriorityException(String message) {
        super(message);
    }

    public TicketPriorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketPriorityException(Throwable cause) {
        super(cause);
    }

    protected TicketPriorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
