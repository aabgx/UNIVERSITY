package domain.exceptions;

public class EntityAlreadyFound extends RuntimeException{
    public EntityAlreadyFound() {
        super();
    }

    public EntityAlreadyFound(String message) {
        super(message);
    }

    public EntityAlreadyFound(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyFound(Throwable cause) {
        super(cause);
    }

    protected EntityAlreadyFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
