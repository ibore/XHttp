package me.ibore.http.connect;

import me.ibore.http.Response;
import me.ibore.http.connect.http.Chain;

import java.io.IOException;

public interface Interceptor {

    /**
     * When intercepting the {@link Chain},
     * here can do some signature and login operation,
     * it should send a response and return.
     *
     * @param chain request chain.
     * @return the server connection.
     * @throws IOException io exception may occur during the implementation, you can handle or throw.
     */
    Response intercept(Chain chain) throws IOException;
}