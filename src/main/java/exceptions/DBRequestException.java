package exceptions;

public final class DBRequestException extends RuntimeException {

    public DBRequestException(String message) {
            super(message);
    }
}

