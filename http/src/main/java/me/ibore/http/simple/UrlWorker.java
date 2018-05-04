package me.ibore.http.simple;

import me.ibore.http.Response;
import me.ibore.http.connect.http.Call;

import java.io.IOException;
import java.lang.reflect.Type;

final class UrlWorker<S, F> extends BasicWorker<SimpleUrlRequest, S, F> {

    UrlWorker(SimpleUrlRequest request, Type succeed, Type failed) {
        super(request, succeed, failed);
    }

    @Override
    protected Response requestNetwork(SimpleUrlRequest request) throws IOException {
        return new Call(request).execute();
    }
}