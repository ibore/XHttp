package me.ibore.http;

public interface Canceller {
    /**
     * Cancel operation.
     */
    void cancel();

    /**
     * Operation is canceled.
     */
    boolean isCancelled();
}
