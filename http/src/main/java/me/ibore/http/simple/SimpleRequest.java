package me.ibore.http.simple;

import me.ibore.http.Headers;
import me.ibore.http.Url;
import me.ibore.http.simple.cache.CacheMode;

public interface SimpleRequest {
    /**
     * Get the file download address.
     */
    Url url();

    /**
     * Get headers.
     */
    Headers headers();

    /**
     * Get cache mode.
     */
    CacheMode cacheMode();

    /**
     * Get cache key.
     */
    String cacheKey();

    /**
     * Get converter.
     */
    Converter converter();
}