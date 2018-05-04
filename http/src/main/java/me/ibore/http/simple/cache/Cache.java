package me.ibore.http.simple.cache;

import me.ibore.http.Headers;

import java.io.Serializable;

public class Cache implements Serializable {

    private String mKey;
    private int mCode;
    private Headers mHeaders;
    private byte[] mBody;
    private long mExpires;

    public Cache() {
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public Headers getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Headers headers) {
        mHeaders = headers;
    }

    public byte[] getBody() {
        return mBody;
    }

    public void setBody(byte[] body) {
        this.mBody = body;
    }

    public long getExpires() {
        return mExpires;
    }

    public void setExpires(long expires) {
        this.mExpires = expires;
    }
}
