package me.ibore.http.connect.http;


import java.io.IOException;
import java.util.List;

import me.ibore.http.Request;
import me.ibore.http.Response;
import me.ibore.http.connect.Interceptor;

public class AppChain implements Chain {

    private final List<Interceptor> mInterceptors;
    private final int mTargetIndex;
    private final Request mRequest;

    public AppChain(List<Interceptor> interceptors, int targetIndex, Request request) {
        this.mInterceptors = interceptors;
        this.mTargetIndex = targetIndex;
        this.mRequest = request;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        Interceptor interceptor = mInterceptors.get(mTargetIndex);
        AppChain chain = new AppChain(mInterceptors, mTargetIndex + 1, mRequest);
        return interceptor.intercept(chain);
    }

    @Override
    public Call newCall() {
        return new Call(mRequest);
    }
}