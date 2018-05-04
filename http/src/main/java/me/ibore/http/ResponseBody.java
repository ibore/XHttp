package me.ibore.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface ResponseBody extends Closeable {

    /**
     * Transform the response data into a string.
     */
    String string() throws IOException;

    /**
     * Transform the response data into a byte array.
     */
    byte[] byteArray() throws IOException;

    /**
     * Transform the response data into a stream.
     */
    InputStream stream() throws IOException;
}