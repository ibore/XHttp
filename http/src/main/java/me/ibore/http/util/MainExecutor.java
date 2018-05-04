package me.ibore.http.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class MainExecutor implements Executor {

    private Handler mHandler;

    public MainExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}