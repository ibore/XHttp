package me.ibore.http.simple;

import me.ibore.http.Canceller;

import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;

final class Work<T extends SimpleRequest, S, F> extends FutureTask<SimpleResponse<S, F>> implements Canceller {

    private final Callback<S, F> mCallback;

    Work(BasicWorker<T, S, F> work, Callback<S, F> callback) {
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
            mCallback.onResponse(get());
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