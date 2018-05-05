package me.ibore.http.connect.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ibore.http.XHttp;
import me.ibore.http.Request;
import me.ibore.http.Response;
import me.ibore.http.connect.Interceptor;

public class Call {

    private final Request mRequest;

    public Call(Request request) {
        this.mRequest = request;
    }

    /**
     * Execute request.
     */
    public Response execute() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>(XHttp.getConfig().getInterceptor());
        interceptors.add(new ConnectInterceptor());

        AppChain chain = new AppChain(interceptors, 0, mRequest);
        return chain.proceed(mRequest);
    }
}