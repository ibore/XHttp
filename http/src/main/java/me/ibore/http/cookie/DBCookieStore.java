package me.ibore.http.cookie;

import android.content.Context;
import android.text.TextUtils;

import me.ibore.http.cookie.db.CookieDao;
import me.ibore.http.cookie.db.Field;
import me.ibore.http.cookie.db.Where;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBCookieStore implements CookieStore, Field {

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    private final static int MAX_COOKIE_SIZE = 888;
    private Lock mLock;
    private CookieDao mCookieDao;

    private DBCookieStore(Builder builder) {
        mLock = new ReentrantLock();
        mCookieDao = new CookieDao(builder.mContext);

        Where where = Where.newBuilder()
                .add(EXPIRY, Where.Options.EQUAL, -1)
                .build();
        mCookieDao.delete(where.toString());
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        mLock.lock();
        try {
            uri = getEffectiveURI(uri);

            Where.Builder whereBuilder = Where.newBuilder();

            String host = uri.getHost();
            if (!TextUtils.isEmpty(host)) {
                Where.Builder subBuilder = Where.newBuilder()
                        .add(DOMAIN, Where.Options.EQUAL, host)
                        .or(DOMAIN, Where.Options.EQUAL, "." + host);

                int firstDot = host.indexOf(".");
                int lastDot = host.lastIndexOf(".");
                if (firstDot > 0 && lastDot > firstDot) {
                    String domain = host.substring(firstDot, host.length());
                    if (!TextUtils.isEmpty(domain)) {
                        subBuilder.or(DOMAIN, Where.Options.EQUAL, domain);
                    }
                }
                whereBuilder.set(subBuilder.build().toString());
            }

            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                Where.Builder subBuilder = Where.newBuilder()
                        .add(PATH, Where.Options.EQUAL, path)
                        .or(PATH, Where.Options.EQUAL, "/")
                        .orNull(PATH);
                int lastSplit = path.lastIndexOf("/");
                while (lastSplit > 0) {
                    path = path.substring(0, lastSplit);
                    subBuilder.or(PATH, Where.Options.EQUAL, path);
                    lastSplit = path.lastIndexOf("/");
                }
                subBuilder.bracket();
                whereBuilder.and(subBuilder.build());
            }

            whereBuilder.or(URL, Where.Options.EQUAL, uri.toString());

            Where where = whereBuilder.build();
            List<Cookie> cookieList = mCookieDao.getList(where.toString(), null, null, null);
            List<HttpCookie> returnedCookies = new ArrayList<>();
            for (Cookie cookie : cookieList) {
                if (!Cookie.isExpired(cookie)) returnedCookies.add(Cookie.toHttpCookie(cookie));
            }
            return returnedCookies;
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public void add(URI uri, HttpCookie httpCookie) {
        mLock.lock();
        try {
            if (uri != null && httpCookie != null) {
                uri = getEffectiveURI(uri);
                mCookieDao.replace(Cookie.toCookie(uri.toString(), httpCookie));
                trimSize();
            }
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public void remove(HttpCookie httpCookie) {
        mLock.lock();
        try {
            Where.Builder whereBuilder = Where.newBuilder().add(NAME, Where.Options.EQUAL, httpCookie.getName());

            String domain = httpCookie.getDomain();
            if (!TextUtils.isEmpty(domain)) whereBuilder.and(DOMAIN, Where.Options.EQUAL, domain);

            String path = httpCookie.getPath();
            if (!TextUtils.isEmpty(path)) {
                if (path.length() > 1 && path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                whereBuilder.and(PATH, Where.Options.EQUAL, path);
            }
            mCookieDao.delete(whereBuilder.build().toString());
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public void clear() {
        mLock.lock();
        try {
            mCookieDao.deleteAll();
        } finally {
            mLock.unlock();
        }
    }

    private void trimSize() {
        int count = mCookieDao.count();
        if (count > MAX_COOKIE_SIZE) {
            List<Cookie> rmList = mCookieDao.getList(null, null, Integer.toString(count - MAX_COOKIE_SIZE), null);
            if (rmList != null)
                mCookieDao.delete(rmList);
        }
    }

    private static URI getEffectiveURI(final URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI("http", uri.getHost(), uri.getPath(), null, null);
        } catch (URISyntaxException e) {
            effectiveURI = uri;
        }
        return effectiveURI;
    }

    public static class Builder {

        private Context mContext;

        private Builder(Context context) {
            this.mContext = context;
        }

        public DBCookieStore build() {
            return new DBCookieStore(this);
        }
    }
}