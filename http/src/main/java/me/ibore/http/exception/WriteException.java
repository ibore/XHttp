package me.ibore.http.exception;

import java.io.IOException;

public class WriteException extends IOException {
    public WriteException(String message) {
        super(message);
    }

    public WriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteException(Throwable cause) {
        super(cause);
    }
}