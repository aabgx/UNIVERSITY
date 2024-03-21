package validators;

public class ValidationException extends RuntimeException{

    public ValidationException(String mesaj){
        super(mesaj);
    }

    public ValidationException(String mesaj, Throwable cauza) {
        super(mesaj, cauza);
    }

    public ValidationException(Throwable cauza) {
        super(cauza);
    }

    public ValidationException(String mesaj, Throwable cauza, boolean enableSuppression, boolean writableStackTrace) {
        super(mesaj, cauza, enableSuppression, writableStackTrace);
    }

}
