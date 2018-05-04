package me.ibore.http.simple;

import me.ibore.http.Response;
import me.ibore.http.connect.http.Call;

import java.io.IOException;
import java.lang.reflect.Type;

final class BodyWorker<S, F> extends BasicWorker<SimpleBodyRequest, S, F> {

    BodyWorker(SimpleBodyRequest request, Type succeed, Type failed) {
        super(request, succeed, failed);
    }

    @Override
    protected Response requestNetwork(SimpleBodyRequest request) throws IOException {
        return new Call(request).execute();
    }
}