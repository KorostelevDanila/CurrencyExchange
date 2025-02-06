package exceptions;

public class NotFoundInDatabaseException extends RuntimeException {
    public NotFoundInDatabaseException() {
        super();
    }

    public NotFoundInDatabaseException(String message) {
        super(message);
    }
}
