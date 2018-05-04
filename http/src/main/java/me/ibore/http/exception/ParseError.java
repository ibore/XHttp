package me.ibore.http.exception;

public class ParseError extends ReadException {
    public ParseError(String message) {
        super(message);
    }

    public ParseError(String message, Throwable cause) {
        super(message, cause);
    }
}
