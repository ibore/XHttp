package me.ibore.http.exception;

public class ReadTimeoutError extends ReadException {
    public ReadTimeoutError(String message) {
        super(message);
    }

    public ReadTimeoutError(String message, Throwable cause) {
        super(message, cause);
    }
}