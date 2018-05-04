package me.ibore.http.download;

public interface Callback {
    /**
     * Time dimensions: The request started.
     */
    void onStart();

    /**
     * Result dimensions: File download completed.
     */
    void onFinish(String path);

    /**
     * Result dimensions: An abnormality has occurred.
     */
    void onException(Exception e);

    /**
     * Result dimensions: The request was cancelled.
     */
    void onCancel();

    /**
     * Time dimensions: The request ended.
     */
    void onEnd();
}