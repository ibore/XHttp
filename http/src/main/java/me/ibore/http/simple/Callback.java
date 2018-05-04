package me.ibore.http.simple;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class Callback<Succeed, Failed> {

    private final Type[] mTypeArguments;

    public Callback() {
        Type superClass = getClass().getGenericSuperclass();
        mTypeArguments = ((ParameterizedType) superClass).getActualTypeArguments();
    }

    /**
     * Get the data type when the business was successful.
     */
    public Type getSucceed() {
        return mTypeArguments[0];
    }

    /**
     * Get the data type when the business failed.
     */
    public Type getFailed() {
        return mTypeArguments[1];
    }

    /**
     * Time dimensions: The request started.
     */
    public abstract void onStart();

    /**
     * Result dimensions: The request has responded.
     */
    public abstract void onResponse(SimpleResponse<Succeed, Failed> response);

    /**
     * Result dimensions: An abnormality has occurred.
     */
    public abstract void onException(Exception e);

    /**
     * Result dimensions: The request was cancelled.
     */
    public abstract void onCancel();

    /**
     * Time dimensions: The request ended.
     */
    public abstract void onEnd();
}