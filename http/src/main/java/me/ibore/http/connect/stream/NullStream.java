package me.ibore.http.connect.stream;

import me.ibore.http.connect.Connection;
import me.ibore.http.util.IOUtils;

import java.io.InputStream;

public class NullStream extends InputStream {

    private final Connection mConnection;

    public NullStream(Connection connection) {
        this.mConnection = connection;
    }

    @Override
    public int read() {
        return 0;
    }

    @Override
    public int read(byte[] b) {
        return 0;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        return 0;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(mConnection);
    }

    @Override
    public long skip(long n) {
        return 0;
    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public void reset() {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void mark(int limit) {
    }
}