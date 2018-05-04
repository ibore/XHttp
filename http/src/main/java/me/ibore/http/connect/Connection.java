package me.ibore.http.connect;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface Connection extends Closeable {

    /**
     * Gets output stream for socket.
     */
    OutputStream getOutputStream() throws IOException;

    /**
     * Gets response code for server.
     */
    int getCode() throws IOException;

    /**
     * Gets response headers for server.
     */
    Map<String, List<String>> getHeaders() throws IOException;

    /**
     * Gets input stream for socket.
     */
    InputStream getInputStream() throws IOException;

}