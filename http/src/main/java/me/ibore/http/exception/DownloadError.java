package me.ibore.http.exception;

import me.ibore.http.Headers;

public class DownloadError extends ReadException {

    private int mCode;
    private Headers mHeaders;

    public DownloadError(int code, Headers headers, String message) {
        super(message);
        this.mCode = code;
        this.mHeaders = headers;
    }

    public DownloadError(int code, Headers headers, Throwable cause) {
        super(cause);
        this.mCode = code;
        this.mHeaders = headers;
    }

    public int getCode() {
        return mCode;
    }

    public Headers getHeaders() {
        return mHeaders;
    }
}