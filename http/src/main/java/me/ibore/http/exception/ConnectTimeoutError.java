package me.ibore.http.exception;

public class ConnectTimeoutError extends ConnectException {
    public ConnectTimeoutError(String message) {
        super(message);
    }

    public ConnectTimeoutError(String message, Throwable cause) {
        super(message, cause);
    }
}