package me.ibore.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BasicOutData<T extends BasicOutData<T>> implements OutData {

    private ProgressBar<T> mProgressBar;

    public T onProgress(ProgressBar<T> progressBar) {
        this.mProgressBar = new AsyncProgressBar<>(progressBar);
        return (T) this;
    }

    @Override
    public final void writeTo(OutputStream writer) throws IOException {
        if (mProgressBar != null) onWrite(new ProgressStream<>(writer, (T) this, mProgressBar));
        else onWrite(writer);
    }

    /**
     * OutData body data.
     */
    protected abstract void onWrite(OutputStream writer) throws IOException;

    /**
     * Calculate the stream of sending onProgress.
     */
    private static class ProgressStream<T extends BasicOutData<T>> extends OutputStream {

        private OutputStream mWriter;
        private T mOrigin;
        private ProgressBar<T> mProgressBar;

        private long mAllLength;
        private long mWriteCount;
        private int mOldProgress;


        private ProgressStream(OutputStream writer, T origin, ProgressBar<T> progressBar) {
            this.mWriter = writer;
            this.mOrigin = origin;
            this.mProgressBar = progressBar;

            this.mAllLength = mOrigin.length();
        }

        @Override
        public void write(int b) throws IOException {
            mWriter.write(b);
            mWriteCount += 1;
            calcProgress();
        }

        @Override
        public void write(byte[] b) throws IOException {
            mWriter.write(b);
            mWriteCount += b.length;
            calcProgress();
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            mWriter.write(b, off, len);
            mWriteCount += len;
            calcProgress();
        }

        @Override
        public void flush() throws IOException {
            mWriter.flush();
        }

        @Override
        public void close() throws IOException {
            mWriter.close();
        }

        private void calcProgress() {

            if (mAllLength > 0) {
                int progress = (int) (mWriteCount * 100 / mAllLength);
                if (progress > mOldProgress && progress % 2 == 0) {
                    mOldProgress = progress;
                    mProgressBar.progress(mOrigin, mOldProgress);
                }
            }
        }
    }

    protected static class CounterStream extends OutputStream {

        private final AtomicLong length = new AtomicLong(0L);

        protected CounterStream() {
        }

        public long getLength() {
            return length.get();
        }

        public void write(long multiByte) throws IOException {
            length.addAndGet(multiByte);
        }

        @Override
        public void write(int oneByte) throws IOException {
            length.addAndGet(1);
        }

        @Override
        public void write(byte[] buffer) throws IOException {
            length.addAndGet(buffer.length);
        }

        @Override
        public void write(byte[] buffer, int offset, int count) throws IOException {
            length.addAndGet(count);
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }
    }

    private static class AsyncProgressBar<T> implements ProgressBar<T> {
        private final ProgressBar<T> mProgressBar;
        private final Executor mExecutor;

        public AsyncProgressBar(ProgressBar<T> bar) {
            this.mProgressBar = bar;
            this.mExecutor = Kalle.getConfig().getMainExecutor();
        }

        @Override
        public void progress(final T origin, final int progress) {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.progress(origin, progress);
                }
            });
        }
    }

}