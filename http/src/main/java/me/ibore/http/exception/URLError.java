package me.ibore.http.exception;

public class URLError extends ConnectException {
    public URLError(String message) {
        super(message);
    }

    public URLError(String message, Throwable cause) {
        super(message, cause);
    }
}