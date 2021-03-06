package me.ibore.http.simple;

import android.text.TextUtils;

import me.ibore.http.Canceller;
import me.ibore.http.Method;
import me.ibore.http.Url;
import me.ibore.http.UrlRequest;
import me.ibore.http.simple.cache.CacheMode;

import java.lang.reflect.Type;

import static me.ibore.http.simple.cache.CacheMode.HTTP;

public class SimpleUrlRequest extends UrlRequest implements SimpleRequest {

    public static SimpleUrlRequest.Api newApi(Url.Builder builder, Method method) {
        return new SimpleUrlRequest.Api(builder, method);
    }

    private final CacheMode mCacheMode;
    private final String mCacheKey;

    private final Converter mConverter;

    private SimpleUrlRequest(Api api) {
        super(api);
        this.mCacheMode = api.mCacheMode == null ? HTTP : api.mCacheMode;
        this.mCacheKey = TextUtils.isEmpty(api.mCacheKey) ? url().toString() : api.mCacheKey;

        this.mConverter = api.mConverter;
    }

    @Override
    public CacheMode cacheMode() {
        return mCacheMode;
    }

    @Override
    public String cacheKey() {
        return mCacheKey;
    }

    @Override
    public Converter converter() {
        return mConverter;
    }

    public static class Api extends UrlRequest.Api<Api> {

        private CacheMode mCacheMode;
        private String mCacheKey;

        private Converter mConverter;

        private Api(Url.Builder builder, Method method) {
            super(builder, method);
        }

        public Api cacheMode(CacheMode cacheMode) {
            this.mCacheMode = cacheMode;
            return this;
        }

        public Api cacheKey(String cacheKey) {
            this.mCacheKey = cacheKey;
            return this;
        }

        public Api converter(Converter converter) {
            this.mConverter = converter;
            return this;
        }

        public <S, F> SimpleResponse<S, F> perform(Type succeed, Type failed) throws Exception {
            return RequestManager.getInstance().perform(new SimpleUrlRequest(this), succeed, failed);
        }
        
        public <S, F> Canceller perform(Callback<S, F> callback) {
            return RequestManager.getInstance().perform(new SimpleUrlRequest(this), callback);
        }
    }
}