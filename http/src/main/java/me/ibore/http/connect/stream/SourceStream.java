package me.ibore.http.connect.stream;

import me.ibore.http.connect.Connection;
import me.ibore.http.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SourceStream extends InputStream {

    private final Connection mConnection;
    private final InputStream mStream;

    public SourceStream(Connection connection, InputStream stream) {
        this.mConnection = connection;
        this.mStream = stream;
    }

    @Override
    public int read() throws IOException {
        return mStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return mStream.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return mStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return mStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return mStream.available();
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(mStream);
        IOUtils.closeQuietly(mConnection);
    }

    @Override
    public void reset() throws IOException {
        mStream.reset();
    }

    @Override
    public synchronized void mark(int limit) {
        mStream.mark(limit);
    }

    @Override
    public boolean markSupported() {
        return mStream.markSupported();
    }
}