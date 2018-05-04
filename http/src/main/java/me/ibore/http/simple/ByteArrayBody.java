package me.ibore.http.simple;

import android.text.TextUtils;

import me.ibore.http.Headers;
import me.ibore.http.ResponseBody;
import me.ibore.http.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayBody implements ResponseBody {

    private String mContentType;
    private byte[] mData;

    public ByteArrayBody(String contentType, byte[] data) {
        this.mContentType = contentType;
        this.mData = data;
    }

    @Override
    public String string() throws IOException {
        String charset = Headers.parseSubValue(mContentType, "charset", null);
        return TextUtils.isEmpty(charset) ? IOUtils.toString(mData) : IOUtils.toString(mData, charset);
    }

    @Override
    public byte[] byteArray() throws IOException {
        return mData;
    }

    @Override
    public InputStream stream() throws IOException {
        return new ByteArrayInputStream(mData);
    }

    @Override
    public void close() throws IOException {
        mData = null;
    }
}