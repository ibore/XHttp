package me.ibore.http.exception;

public class NoCacheError extends ReadException {
    public NoCacheError(String message) {
        super(message);
    }

    public NoCacheError(String message, Throwable cause) {
        super(message, cause);
    }
}
