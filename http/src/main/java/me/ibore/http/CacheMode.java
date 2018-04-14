package me.ibore.http;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static me.ibore.http.CacheMode.*;


@IntDef({HTTP, HTTP_YES_THEN_WRITE_CACHE, NETWORK, NETWORK_YES_THEN_HTTP,
        NETWORK_YES_THEN_WRITE_CACHE, NETWORK_NO_THEN_READ_CACHE, READ_CACHE,
        READ_CACHE_NO_THEN_NETWORK, READ_CACHE_NO_THEN_HTTP})
@Retention(RetentionPolicy.SOURCE)
public @interface CacheMode {

    int HTTP = 0;
    int HTTP_YES_THEN_WRITE_CACHE = 1;
    int NETWORK = 2;
    int NETWORK_YES_THEN_HTTP = 3;
    int NETWORK_YES_THEN_WRITE_CACHE = 4;
    int NETWORK_NO_THEN_READ_CACHE = 5;
    int READ_CACHE = 6;
    int READ_CACHE_NO_THEN_NETWORK = 7;
    int READ_CACHE_NO_THEN_HTTP = 8;

}

