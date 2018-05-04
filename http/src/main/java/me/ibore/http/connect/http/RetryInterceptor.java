package me.ibore.http.connect.http;

import me.ibore.http.Response;
import me.ibore.http.connect.Interceptor;

import java.io.IOException;

public class RetryInterceptor implements Interceptor {

    private int mCount;

    public RetryInterceptor(int count) {
        this.mCount = count;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            return chain.proceed(chain.request());
        } catch (IOException e) {
            if (mCount > 0) {
                mCount--;
                return intercept(chain);
            }
            throw e;
        }
    }
}