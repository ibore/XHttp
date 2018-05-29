package me.ibore.http.progress;

import java.io.IOException;

import me.ibore.http.XHttp;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2018/2/22.
 */

public final class ProgressResponseBody extends ResponseBody {

    private ResponseBody mDelegate;
    private ProgressListener mListener;
    private Progress mProgress;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(String url, ResponseBody delegate, ProgressListener listener) {
        this.mDelegate = delegate;
        this.mListener = listener;
        this.mProgress = new Progress();
        this.mProgress.setMode(Progress.DOWNLOAD);
        this.mProgress.setUrl(url);
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() {
        return mDelegate.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(mDelegate.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new CountingSource(source);
    }

    private final class CountingSource extends ForwardingSource {

        private long bytesWritten = 0L;
        private long lastRefreshUiTime = 0L;
        private long lastWriteBytes = 0L;

        public CountingSource(Source delegate) {
            super(delegate);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            if (mProgress.getTotal() == 0) {
                mProgress.setTotal(contentLength());
            }
            bytesWritten += byteCount;
            if (mListener != null) {
                long curTime = System.currentTimeMillis();
                lastWriteBytes = 0L;
                if (curTime - lastRefreshUiTime >= XHttp.REFRESH_TIME || bytesWritten == mProgress.getTotal()) {
                    long diffTime = (curTime - lastRefreshUiTime) / 1000;
                    if (diffTime == 0) diffTime += 1;
                    long diffBytes = bytesWritten - lastWriteBytes;
                    final long networkSpeed = diffBytes / diffTime;
                    lastRefreshUiTime = curTime;
                    lastWriteBytes = bytesWritten;
                    mProgress.setSpeed(networkSpeed);
                    mProgress.setCurrent(bytesWritten);
                    mProgress.setProgress((int) (bytesWritten * 10000 / mProgress.getTotal()));
                    XHttp.Handler.post(() -> mListener.onProgress(mProgress));
                }
            }

            return super.read(sink, byteCount);
        }
    }
}