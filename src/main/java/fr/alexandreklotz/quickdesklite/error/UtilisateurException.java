package fr.alexandreklotz.quickdesklite.error;

public class UtilisateurException extends Exception {

    public UtilisateurException() {
        super();
    }

    public UtilisateurException(String message) {
        super(message);
    }

    public UtilisateurException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilisateurException(Throwable cause) {
        super(cause);
    }

    protected UtilisateurException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
