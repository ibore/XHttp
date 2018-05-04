package me.ibore.http;

import android.text.TextUtils;

import me.ibore.http.util.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static me.ibore.http.Headers.VALUE_APPLICATION_STREAM;

/**
 * Created by YanZhenjie on 2018/2/11.
 */
public class StringBody extends BasicOutData<StringBody> implements RequestBody {

    private final String mBody;
    private final Charset mCharset;
    private final String mContentType;

    public StringBody(String body) {
        this(body, Kalle.getConfig().getCharset());
    }

    public StringBody(String body, Charset charset) {
        this(body, charset, VALUE_APPLICATION_STREAM);
    }

    public StringBody(String body, String contentType) {
        this(body, Kalle.getConfig().getCharset(), contentType);
    }

    public StringBody(String body, Charset charset, String contentType) {
        this.mBody = body;
        this.mCharset = charset;
        this.mContentType = contentType;
    }

    @Override
    public long length() {
        if (TextUtils.isEmpty(mBody)) return 0;
        return IOUtils.toByteArray(mBody, mCharset).length;
    }

    @Override
    public String contentType() {
        return mContentType;
    }

    @Override
    protected void onWrite(OutputStream writer) throws IOException {
        IOUtils.write(writer, mBody, mCharset);
    }
}