package me.ibore.http.connect;

import android.text.TextUtils;

import me.ibore.http.Headers;
import me.ibore.http.ResponseBody;
import me.ibore.http.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class StreamBody implements ResponseBody {

    private String mContentType;
    private InputStream mStream;

    public StreamBody(String contentType, InputStream stream) {
        this.mContentType = contentType;
        this.mStream = stream;
    }

    @Override
    public String string() throws IOException {
        String charset = Headers.parseSubValue(mContentType, "charset", null);
        return TextUtils.isEmpty(charset) ? IOUtils.toString(mStream) : IOUtils.toString(mStream, charset);
    }

    @Override
    public byte[] byteArray() throws IOException {
        return IOUtils.toByteArray(mStream);
    }

    @Override
    public InputStream stream() throws IOException {
        return mStream;
    }

    @Override
    public void close() throws IOException {
        mStream.close();
    }
}