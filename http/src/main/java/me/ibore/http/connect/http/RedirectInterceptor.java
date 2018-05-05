package me.ibore.http.connect.http;

import me.ibore.http.BodyRequest;
import me.ibore.http.Headers;
import me.ibore.http.Request;
import me.ibore.http.Method;
import me.ibore.http.Response;
import me.ibore.http.Url;
import me.ibore.http.UrlRequest;
import me.ibore.http.connect.Interceptor;
import me.ibore.http.util.IOUtils;

import java.io.IOException;

import static me.ibore.http.Headers.KEY_COOKIE;

public class RedirectInterceptor implements Interceptor {

    public RedirectInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.isRedirect()) {
            Url oldUrl = request.url();
            Url url = oldUrl.location(response.headers().getLocation());
            Headers headers = request.headers();
            headers.remove(KEY_COOKIE);

            Method method = request.method();
            Request newRequest;
            if (method.allowBody()) {
                newRequest = BodyRequest.newBuilder(url.builder(), method)
                        .setHeaders(headers)
                        .setParams(request.copyParams())
                        .body(request.body())
                        .build();
            } else {
                newRequest = UrlRequest.newBuilder(url.builder(), method)
                        .setHeaders(headers)
                        .build();
            }
            IOUtils.closeQuietly(response);
            return chain.proceed(newRequest);
        }
        return response;
    }
}