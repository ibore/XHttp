package me.ibore.http;

import java.io.IOException;
import java.io.OutputStream;

public interface OutData {

    /**
     * Returns the size of the data.
     */
    long length();

    /**
     * Get the content type of data.
     */
    String contentType();

    /**
     * OutData data.
     */
    void writeTo(OutputStream writer) throws IOException;
}