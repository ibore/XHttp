package me.ibore.http.download;

import me.ibore.http.Canceller;

import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;

public class Work<T extends Download> extends FutureTask<String> implements Canceller {

    private final Callback mCallback;

    Work(BasicWorker<T> work, Callback callback) {
        super(work);
        this.mCallback = callback;
    }

    @Override
    public void run() {
        mCallback.onStart();
        super.run();
    }

    @Override
    protected void done() {
        try {
            mCallback.onFinish(get());
        } catch (CancellationException e) {
            mCallback.onCancel();
        } catch (Exception e) {
            mCallback.onException(e);
        }
        mCallback.onEnd();
    }

    @Override
    public void cancel() {
        cancel(true);
    }
}