package me.ibore.http;

public interface ProgressBar<T> {
    /**
     * Progress has changed.
     */
    void progress(T origin, int progress);
}