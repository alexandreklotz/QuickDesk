package fr.alexandreklotz.quickdesklite.error;

public class TicketCategoryException extends Exception {

    public TicketCategoryException() {
        super();
    }

    public TicketCategoryException(String message) {
        super(message);
    }

    public TicketCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketCategoryException(Throwable cause) {
        super(cause);
    }

    protected TicketCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
