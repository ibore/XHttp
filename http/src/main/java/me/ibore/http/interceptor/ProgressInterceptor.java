package me.ibore.http.interceptor;

import me.ibore.http.progress.ProgressListener;
import okhttp3.Interceptor;

public abstract class ProgressInterceptor implements Interceptor {

    private ProgressListener mProgressListener;

    public ProgressInterceptor(ProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    protected ProgressListener getProgressListener() {
        return mProgressListener;
    }

}