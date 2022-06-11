package fr.alexandreklotz.quickdesklite.error;

public class UtilisateurNotFoundException extends Exception {

    public UtilisateurNotFoundException(){
        super();
    }

    public UtilisateurNotFoundException(String message){
        super(message);
    }

    public UtilisateurNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public UtilisateurNotFoundException(Throwable cause){
        super(cause);
    }

    /*protected UtilisateurNotFoundException(String message, Throwable cause, boolean enableSuppr, boolean writableStackTrace){
        super(message, cause, enableSuppr, writableStackTrace);
    }*/
}
