package me.ibore.http;

import android.text.TextUtils;

import me.ibore.http.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import static me.ibore.http.Headers.VALUE_APPLICATION_FORM;

public class FormBody extends BasicOutData<FormBody> implements RequestBody {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final Charset mCharset;
    private final String mContentType;
    private final Params mParams;

    private String mBoundary;

    private FormBody(Builder builder) {
        this.mCharset = builder.mCharset == null ? Kalle.getConfig().getCharset() : builder.mCharset;
        this.mContentType = TextUtils.isEmpty(builder.mContentType) ? VALUE_APPLICATION_FORM : builder.mContentType;
        this.mParams = builder.mParams.build();
        this.mBoundary = createBoundary();
    }

    /**
     * Copy parameters from form body.
     */
    private Params copyParams() {
        return mParams;
    }

    @Override
    public long length() {
        CounterStream stream = new CounterStream();
        try {
            onWrite(stream);
        } catch (IOException ignored) {
        }
        return stream.getLength();
    }

    @Override
    public String contentType() {
        return mContentType + "; boundary=" + mBoundary;
    }

    @Override
    protected void onWrite(OutputStream writer) throws IOException {
        Set<String> keys = mParams.keySet();
        for (String key : keys) {
            List<Object> values = mParams.get(key);
            for (Object value : values) {
                if (value instanceof String) {
                    writeFormString(writer, key, (String) value);
                } else if (value instanceof Binary) {
                    writeFormBinary(writer, key, (Binary) value);
                }
                IOUtils.write(writer, "\r\n", mCharset);
            }
        }
        IOUtils.write(writer, "--" + mBoundary + "--", mCharset);
    }

    private void writeFormString(OutputStream writer, String key, String value) throws IOException {
        IOUtils.write(writer, "--" + mBoundary + "\r\n", mCharset);
        IOUtils.write(writer, "Content-Disposition: form-data; name=\"", mCharset);
        IOUtils.write(writer, key, mCharset);
        IOUtils.write(writer, "\"\r\n\r\n", mCharset);
        IOUtils.write(writer, value, mCharset);
    }

    private void writeFormBinary(OutputStream writer, String key, Binary value) throws IOException {
        IOUtils.write(writer, "--" + mBoundary + "\r\n", mCharset);
        IOUtils.write(writer, "Content-Disposition: form-data; name=\"", mCharset);
        IOUtils.write(writer, key, mCharset);
        IOUtils.write(writer, "\"; filename=\"", mCharset);
        IOUtils.write(writer, value.name(), mCharset);
        IOUtils.write(writer, "\"\r\n", mCharset);
        IOUtils.write(writer, "Content-Type: " + value.contentType() + "\r\n\r\n", mCharset);
        if (writer instanceof CounterStream) {
            ((CounterStream) writer).write(value.length());
        } else {
            value.writeTo(writer);
        }
    }

    private static String createBoundary() {
        StringBuilder sb = new StringBuilder("-------FormBoundary");
        for (int t = 1; t < 12; t++) {
            long time = System.currentTimeMillis() + t;
            if (time % 3L == 0L) {
                sb.append((char) (int) time % '\t');
            } else if (time % 3L == 1L) {
                sb.append((char) (int) (65L + time % 26L));
            } else {
                sb.append((char) (int) (97L + time % 26L));
            }
        }
        return sb.toString();
    }

    public static class Builder {

        private Charset mCharset;
        private String mContentType;
        private Params.Builder mParams;

        private Builder() {
            this.mParams = Params.newBuilder();
        }

        /**
         * Data charset.
         */
        public Builder charset(Charset charset) {
            this.mCharset = charset;
            return this;
        }

        /**
         * Content type.
         */
        public Builder contentType(String contentType) {
            this.mContentType = contentType;
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, int value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, long value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, boolean value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, char value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, double value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, float value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, short value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, CharSequence value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, String value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameters.
         */
        public Builder param(String key, List<String> values) {
            mParams.add(key, values);
            return this;
        }

        /**
         * Add parameters.
         */
        public Builder params(Params params) {
            mParams.add(params);
            return this;
        }

        /**
         * Add several file parameters.
         */
        public Builder file(String key, File file) {
            mParams.file(key, file);
            return this;
        }

        /**
         * Add files parameter.
         */
        public Builder files(String key, List<File> files) {
            mParams.files(key, files);
            return this;
        }

        /**
         * Add binary parameter.
         */
        public Builder binary(String key, Binary binary) {
            mParams.binary(key, binary);
            return this;
        }

        /**
         * Add several binary parameters.
         */
        public Builder binaries(String key, List<Binary> binaries) {
            mParams.binaries(key, binaries);
            return this;
        }

        /**
         * Remove parameters.
         */
        public Builder removeParam(String key) {
            mParams.remove(key);
            return this;
        }

        /**
         * Clear parameters.
         */
        public Builder clearParams() {
            mParams.clear();
            return this;
        }

        public FormBody build() {
            return new FormBody(this);
        }
    }
}