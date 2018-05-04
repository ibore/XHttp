package me.ibore.http.cookie;

import java.net.HttpCookie;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public interface CookieStore {

    CookieStore DEFAULT = new CookieStore() {
        @Override
        public List<HttpCookie> get(URI uri) {
            return Collections.emptyList();
        }

        @Override
        public void add(URI uri, HttpCookie httpCookie) {
        }

        @Override
        public void remove(HttpCookie httpCookie) {
        }

        @Override
        public void clear() {
        }
    };

    /**
     * According to url loading cookies.
     *
     * @param uri uri.
     * @return all cookies that match the rules.
     */
    List<HttpCookie> get(URI uri);

    /**
     * Save cookie for the specified url.
     *
     * @param uri    uri.
     * @param cookie cookie.
     */
    void add(URI uri, HttpCookie cookie);

    /**
     * Remove the specified cookie.
     *
     * @param cookie cookie.
     */
    void remove(HttpCookie cookie);

    /**
     * Clear the cookie.
     */
    void clear();
}