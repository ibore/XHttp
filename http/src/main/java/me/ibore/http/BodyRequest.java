package me.ibore.http;

import java.io.File;
import java.util.List;

public class BodyRequest extends Request {

    public static BodyRequest.Builder newBuilder(Url.Builder builder, Method method) {
        return new BodyRequest.Builder(builder, method);
    }

    private final Url mUrl;
    private final RequestBody mBody;
    private final Params mParams;

    protected BodyRequest(Api api) {
        super(api);
        this.mUrl = api.mUrlBuilder.build();
        this.mParams = api.mParams.build();
        this.mBody = api.mBody == null ? (mParams.hasBinary() ? mParams.toFormBody() : mParams.toUrlBody()) : api.mBody;
    }

    @Override
    public Url url() {
        return mUrl;
    }

    @Override
    public Params copyParams() {
        return mParams;
    }

    /**
     * Get the define body to send.
     */
    @Override
    public RequestBody body() {
        return mBody;
    }

    public static class Api<T extends Api<T>> extends Request.Api<T> {

        private Url.Builder mUrlBuilder;

        private Params.Builder mParams;
        private RequestBody mBody;

        protected Api(Url.Builder builder, Method method) {
            super(method);
            this.mUrlBuilder = builder;
            this.mParams = Params.newBuilder();

            this.mParams.add(XHttp.getConfig().getParams());
        }

        @Override
        public T path(int value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(long value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(boolean value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(char value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(double value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(float value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(CharSequence value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        @Override
        public T path(String value) {
            mUrlBuilder.addPath(value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, int value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, long value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, boolean value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, char value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, double value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, float value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, short value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, CharSequence value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, String value) {
            mUrlBuilder.addQuery(key, value);
            return (T) this;
        }

        /**
         * Add parameter to url.
         */
        public T urlParam(String key, List<String> values) {
            mUrlBuilder.addQuery(key, values);
            return (T) this;
        }

        /**
         * Add parameters to url.
         */
        public T urlParam(Params params) {
            mUrlBuilder.addQuery(params);
            return (T) this;
        }

        /**
         * Set parameter to url.
         */
        public T setUrlParam(Params params) {
            mUrlBuilder.setQuery(params);
            return (T) this;
        }

        @Override
        public T param(String key, int value) {
            return param(key, Integer.toString(value));
        }

        @Override
        public T param(String key, long value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, boolean value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, char value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, double value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, float value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, short value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, CharSequence value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, String value) {
            mParams.add(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, List<String> values) {
            mParams.add(key, values);
            return (T) this;
        }

        /**
         * Add parameters to body.
         */
        public T params(Params params) {
            mParams.add(params);
            return (T) this;
        }

        /**
         * Set parameters to body.
         */
        public T setParams(Params params) {
            mParams.set(params);
            return (T) this;
        }

        @Override
        public T removeParam(String key) {
            mParams.remove(key);
            return (T) this;
        }

        @Override
        public T clearParams() {
            mParams.clear();
            return (T) this;
        }

        /**
         * Add several file parameters.
         */
        public T file(String key, File file) {
            mParams.file(key, file);
            return (T) this;
        }

        /**
         * Add files parameter.
         */
        public T files(String key, List<File> files) {
            mParams.files(key, files);
            return (T) this;
        }

        /**
         * Add binary parameter.
         */
        public T binary(String key, Binary binary) {
            mParams.binary(key, binary);
            return (T) this;
        }

        /**
         * Add several binary parameters.
         */
        public T binaries(String key, List<Binary> binaries) {
            mParams.binaries(key, binaries);
            return (T) this;
        }

        /**
         * Set request body.
         */
        public T body(RequestBody body) {
            this.mBody = body;
            return (T) this;
        }
    }

    public static class Builder extends BodyRequest.Api<BodyRequest.Builder> {

        private Builder(Url.Builder builder, Method method) {
            super(builder, method);
        }

        public BodyRequest build() {
            return new BodyRequest(this);
        }
    }

}