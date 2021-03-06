package me.ibore.http;

import me.ibore.http.connect.Connection;
import me.ibore.http.connect.Interceptor;
import me.ibore.http.connect.Network;
import me.ibore.http.cookie.CookieStore;
import me.ibore.http.simple.Converter;
import me.ibore.http.simple.cache.CacheStore;
import me.ibore.http.ssl.SSLUtils;
import me.ibore.http.urlconnect.URLConnectionFactory;
import me.ibore.http.util.MainExecutor;
import me.ibore.http.util.WorkExecutor;

import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import static me.ibore.http.Headers.KEY_ACCEPT;
import static me.ibore.http.Headers.KEY_ACCEPT_ENCODING;
import static me.ibore.http.Headers.KEY_ACCEPT_LANGUAGE;
import static me.ibore.http.Headers.KEY_CONNECTION;
import static me.ibore.http.Headers.KEY_CONTENT_TYPE;
import static me.ibore.http.Headers.KEY_USER_AGENT;
import static me.ibore.http.Headers.VALUE_ACCEPT_ALL;
import static me.ibore.http.Headers.VALUE_ACCEPT_ENCODING;
import static me.ibore.http.Headers.VALUE_ACCEPT_LANGUAGE;
import static me.ibore.http.Headers.VALUE_APPLICATION_URLENCODED;
import static me.ibore.http.Headers.VALUE_KEEP_ALIVE;
import static me.ibore.http.Headers.VALUE_USER_AGENT;

public final class XHttpConfig {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final Executor mWorkExecutor;
    private final Executor mMainExecutor;

    private final Charset mCharset;
    private final Headers mHeaders;
    private final Proxy mProxy;
    private final SSLSocketFactory mSSLSocketFactory;
    private final HostnameVerifier mHostnameVerifier;
    private final int mConnectTimeout;
    private final int mReadTimeout;
    private final Params mParams;

    private final CacheStore mCacheStore;

    private final Network mNetwork;
    private final Connection.Factory mConnectFactory;
    private final CookieStore mCookieStore;
    private final List<Interceptor> mInterceptors;

    private final Converter mConverter;

    private XHttpConfig(Builder builder) {
        this.mWorkExecutor = builder.mWorkExecutor == null ? new WorkExecutor() : builder.mWorkExecutor;
        this.mMainExecutor = builder.mMainExecutor == null ? new MainExecutor() : builder.mMainExecutor;

        this.mCharset = builder.mCharset == null ? Charset.defaultCharset() : builder.mCharset;
        this.mHeaders = builder.mHeaders;
        this.mProxy = builder.mProxy;
        this.mSSLSocketFactory = builder.mSSLSocketFactory == null ? SSLUtils.SSL_SOCKET_FACTORY : builder.mSSLSocketFactory;
        this.mHostnameVerifier = builder.mHostnameVerifier == null ? SSLUtils.HOSTNAME_VERIFIER : builder.mHostnameVerifier;
        this.mConnectTimeout = builder.mConnectTimeout <= 0 ? 10000 : builder.mConnectTimeout;
        this.mReadTimeout = builder.mReadTimeout <= 0 ? 10000 : builder.mReadTimeout;
        this.mParams = builder.mParams.build();

        this.mCacheStore = builder.mCacheStore == null ? CacheStore.DEFAULT : builder.mCacheStore;

        this.mNetwork = builder.mNetwork == null ? Network.DEFAULT : builder.mNetwork;
        this.mConnectFactory = builder.mConnectFactory == null ? URLConnectionFactory.newBuilder().build() : builder.mConnectFactory;
        this.mCookieStore = builder.mCookieStore == null ? CookieStore.DEFAULT : builder.mCookieStore;
        this.mInterceptors = Collections.unmodifiableList(builder.mInterceptors);

        this.mConverter = builder.mConverter == null ? Converter.DEFAULT : builder.mConverter;
    }

    public Executor getWorkExecutor() {
        return mWorkExecutor;
    }

    public Executor getMainExecutor() {
        return mMainExecutor;
    }

    public Charset getCharset() {
        return mCharset;
    }

    public Headers getHeaders() {
        return mHeaders;
    }

    public Proxy getProxy() {
        return mProxy;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return mSSLSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public Params getParams() {
        return mParams;
    }

    public CacheStore getCacheStore() {
        return mCacheStore;
    }

    public Network getNetwork() {
        return mNetwork;
    }

    public Connection.Factory getConnectFactory() {
        return mConnectFactory;
    }

    public CookieStore getCookieStore() {
        return mCookieStore;
    }

    public List<Interceptor> getInterceptor() {
        return mInterceptors;
    }

    public Converter getConverter() {
        return mConverter;
    }

    public final static class Builder {

        private Executor mWorkExecutor;
        private Executor mMainExecutor;

        private Charset mCharset;
        private Headers mHeaders;
        private Proxy mProxy;
        private SSLSocketFactory mSSLSocketFactory;
        private HostnameVerifier mHostnameVerifier;
        private int mConnectTimeout;
        private int mReadTimeout;
        private Params.Builder mParams;

        private CacheStore mCacheStore;

        private Network mNetwork;
        private Connection.Factory mConnectFactory;
        private CookieStore mCookieStore;
        private List<Interceptor> mInterceptors;

        private Converter mConverter;

        private Builder() {
            this.mHeaders = new Headers();
            this.mParams = Params.newBuilder();
            this.mInterceptors = new ArrayList<>();

            mHeaders.set(KEY_ACCEPT, VALUE_ACCEPT_ALL);
            mHeaders.set(KEY_ACCEPT_ENCODING, VALUE_ACCEPT_ENCODING);
            mHeaders.set(KEY_CONTENT_TYPE, VALUE_APPLICATION_URLENCODED);
            mHeaders.set(KEY_CONNECTION, VALUE_KEEP_ALIVE);
            mHeaders.set(KEY_USER_AGENT, VALUE_USER_AGENT);
            mHeaders.set(KEY_ACCEPT_LANGUAGE, VALUE_ACCEPT_LANGUAGE);
        }

        /**
         * Set global work thread executor.
         */
        public Builder workThreadExecutor(Executor executor) {
            this.mWorkExecutor = executor;
            return this;
        }

        /**
         * Set global main thread executor.
         */
        public Builder mainThreadExecutor(Executor executor) {
            this.mMainExecutor = executor;
            return this;
        }

        /**
         * Global charset.
         */
        public Builder charset(Charset charset) {
            this.mCharset = charset;
            return this;
        }

        /**
         * Add the global header.
         */
        public Builder addHeader(String key, String value) {
            mHeaders.add(key, value);
            return this;
        }

        /**
         * Set the global header.
         */
        public Builder setHeader(String key, String value) {
            mHeaders.set(key, value);
            return this;
        }

        /**
         * Global proxy.
         */
        public Builder proxy(Proxy proxy) {
            this.mProxy = proxy;
            return this;
        }

        /**
         * Global ssl socket factory.
         */
        public Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.mSSLSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * Global hostname verifier.
         */
        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * Global connection timeout.
         */
        public Builder connectionTimeout(int timeout, TimeUnit timeUnit) {
            long time = timeUnit.toMillis(timeout);
            this.mConnectTimeout = (int) Math.min(time, Integer.MAX_VALUE);
            return this;
        }

        /**
         * Global readResponse timeout.
         */
        public Builder readTimeout(int timeout, TimeUnit timeUnit) {
            long time = timeUnit.toMillis(timeout);
            this.mReadTimeout = (int) Math.min(time, Integer.MAX_VALUE);
            return this;
        }

        /**
         * Add the global param.
         */
        public Builder addParam(String key, String value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Global cache store.
         */
        public Builder cacheStore(CacheStore cacheStore) {
            this.mCacheStore = cacheStore;
            return this;
        }

        /**
         * Global network.
         */
        public Builder network(Network network) {
            this.mNetwork = network;
            return this;
        }

        /**
         * Global cookie store.
         */
        // TODO
        public Builder connectFactory(Connection.Factory factory) {
            this.mConnectFactory = factory;
            return this;
        }

        /**
         * Global cookie store.
         */
        public Builder cookieStore(CookieStore cookieStore) {
            this.mCookieStore = cookieStore;
            return this;
        }

        /**
         * Add the global interceptor.
         */
        public Builder addInterceptor(Interceptor interceptor) {
            this.mInterceptors.add(interceptor);
            return this;
        }

        /**
         * Add the global interceptor.
         */
        public Builder addInterceptors(List<Interceptor> interceptors) {
            this.mInterceptors.addAll(interceptors);
            return this;
        }

        /**
         * The converter.
         */
        public Builder converter(Converter converter) {
            this.mConverter = converter;
            return this;
        }

        public XHttpConfig build() {
            return new XHttpConfig(this);
        }
    }

}