package me.ibore.http.exception;

public class NetworkError extends ConnectException {
    public NetworkError(String message) {
        super(message);
    }

    public NetworkError(String message, Throwable cause) {
        super(message, cause);
    }
}