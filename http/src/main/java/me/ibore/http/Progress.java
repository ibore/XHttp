package me.ibore.http;

public interface Progress<T> {
    /**
     * Progress has changed.
     */
    void progress(T origin, int progress);
}