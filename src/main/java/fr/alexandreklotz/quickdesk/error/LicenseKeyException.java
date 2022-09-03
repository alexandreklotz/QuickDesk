package fr.alexandreklotz.quickdesk.error;

public class LicenseKeyException extends Exception {

    public LicenseKeyException() {
        super();
    }

    public LicenseKeyException(String message) {
        super(message);
    }

    public LicenseKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public LicenseKeyException(Throwable cause) {
        super(cause);
    }

    protected LicenseKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
