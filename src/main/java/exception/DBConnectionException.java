package exception;

public final class DBConnectionException extends RuntimeException {

    public DBConnectionException(String message) {
        super(message);
    }
}
