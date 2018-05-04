package me.ibore.http.connect;


import java.io.IOException;

import me.ibore.http.Request;

public interface ConnectFactory {
    /**
     * According to the request attribute,
     * and the server to establish a connection.
     */
    Connection connect(Request request) throws IOException;
}