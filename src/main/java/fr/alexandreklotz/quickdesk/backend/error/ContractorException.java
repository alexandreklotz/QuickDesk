package fr.alexandreklotz.quickdesk.backend.error;

public class ContractorException extends Exception {

    public ContractorException() {
        super();
    }

    public ContractorException(String message) {
        super(message);
    }

    public ContractorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContractorException(Throwable cause) {
        super(cause);
    }

    protected ContractorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
