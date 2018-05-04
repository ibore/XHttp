package me.ibore.http.simple;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class SimpleCallback<V> extends Callback<V, String> {

    public SimpleCallback() {
    }

    @Override
    public Type getSucceed() {
        Type superClass = getClass().getGenericSuperclass();
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    @Override
    public Type getFailed() {
        return String.class;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onException(Exception e) {
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onEnd() {
    }
}
