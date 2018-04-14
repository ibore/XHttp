package me.ibore.http;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static me.ibore.http.Method.*;

@StringDef({GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE})
@Retention(RetentionPolicy.SOURCE)
public @interface Method {

    String GET = "GET";
    String POST = "POST";
    String PUT = "PUT";
    String DELETE = "DELETE";
    String HEAD = "HEAD";
    String PATCH = "PATCH";
    String OPTIONS = "OPTIONS";
    String TRACE = "TRACE";

}