package me.ibore.http;

import java.util.List;

public class UrlRequest extends Request {

    public static UrlRequest.Builder newBuilder(Url.Builder builder, RequestMethod method) {
        return new UrlRequest.Builder(builder, method);
    }

    private final Url mUrl;

    protected UrlRequest(Api api) {
        super(api);
        this.mUrl = api.mUrl.build();
    }

    @Override
    public Url url() {
        return mUrl;
    }

    @Override
    public Params copyParams() {
        return mUrl.copyQuery();
    }

    @Override
    public RequestBody body() {
        throw new AssertionError("It should not be called.");
    }

    public static class Api<T extends Api<T>> extends Request.Api<T> {

        private Url.Builder mUrl;

        protected Api(Url.Builder builder, RequestMethod method) {
            super(method);
            this.mUrl = builder;
            this.mUrl.addQuery(Kalle.getConfig().getParams());
        }

        @Override
        public T path(int value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(long value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(boolean value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(char value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(double value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(float value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(CharSequence value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T path(String value) {
            mUrl.addPath(value);
            return (T) this;
        }

        @Override
        public T param(String key, int value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, long value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, boolean value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, char value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, double value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, float value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, short value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, CharSequence value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, String value) {
            mUrl.addQuery(key, value);
            return (T) this;
        }

        @Override
        public T param(String key, List<String> values) {
            mUrl.addQuery(key, values);
            return (T) this;
        }

        /**
         * Add parameters to url.
         */
        public T params(Params params) {
            mUrl.addQuery(params);
            return (T) this;
        }

        /**
         * Set parameters to url.
         */
        public T setParams(Params params) {
            mUrl.setQuery(params);
            return (T) this;
        }

        @Override
        public T removeParam(String key) {
            mUrl.removeQuery(key);
            return (T) this;
        }

        @Override
        public T clearParams() {
            mUrl.clearQuery();
            return (T) this;
        }
    }

    public static class Builder extends UrlRequest.Api<UrlRequest.Builder> {

        private Builder(Url.Builder builder, RequestMethod method) {
            super(builder, method);
        }

        public UrlRequest build() {
            return new UrlRequest(this);
        }
    }
}