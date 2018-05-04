package me.ibore.http.exception;

public class HostError extends ConnectException {
    public HostError(String message) {
        super(message);
    }

    public HostError(String message, Throwable cause) {
        super(message, cause);
    }
}