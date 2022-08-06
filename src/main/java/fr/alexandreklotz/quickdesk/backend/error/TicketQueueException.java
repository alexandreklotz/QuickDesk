package fr.alexandreklotz.quickdesk.backend.error;

public class TicketQueueException extends Exception {

    public TicketQueueException() {
        super();
    }

    public TicketQueueException(String message) {
        super(message);
    }

    public TicketQueueException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketQueueException(Throwable cause) {
        super(cause);
    }

    protected TicketQueueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
