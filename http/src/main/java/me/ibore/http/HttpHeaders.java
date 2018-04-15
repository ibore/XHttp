package me.ibore.http;

import android.os.Build;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class HttpHeaders {

    public static final String TIME_FORMAT_HTTP = "EEE, dd MMM y HH:mm:ss 'GMT'";
    public static final TimeZone TIME_ZONE_GMT = TimeZone.getTimeZone("GMT");

    public static final String KEY_ACCEPT = "Accept";
    public static final String VALUE_ACCEPT_ALL = "*/*";
    public static final String KEY_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String VALUE_ACCEPT_ENCODING = "gzip, deflate";
    public static final String KEY_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String VALUE_ACCEPT_LANGUAGE = getLanguage();
    public static final String KEY_ACCEPT_RANGE = "Accept-Range";
    public static final String KEY_COOKIE = "Cookie";
    public static final String KEY_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String KEY_CONTENT_ENCODING = "Content-Encoding";
    public static final String KEY_CONTENT_LENGTH = "Content-Length";
    public static final String KEY_CONTENT_RANGE = "Content-Range";
    public static final String KEY_CONTENT_TYPE = "Content-Type";
    public static final String VALUE_APPLICATION_URLENCODED = "application/x-www-form-urlencoded";
    public static final String VALUE_APPLICATION_FORM = "multipart/form-data";
    public static final String VALUE_APPLICATION_STREAM = "application/octet-stream";
    public static final String VALUE_APPLICATION_JSON = "application/json";
    public static final String VALUE_APPLICATION_XML = "application/xml";
    public static final String KEY_CACHE_CONTROL = "Cache-Control";
    public static final String KEY_CONNECTION = "Connection";
    public static final String VALUE_KEEP_ALIVE = "keep-alive";
    public static final String VALUE_CLOSE = "close";
    public static final String KEY_DATE = "Date";
    public static final String KEY_EXPIRES = "Expires";
    public static final String KEY_E_TAG = "ETag";
    public static final String KEY_HOST = "Host";
    public static final String KEY_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String KEY_IF_NONE_MATCH = "If-None-Match";
    public static final String KEY_LAST_MODIFIED = "Last-Modified";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_RANGE = "Range";
    public static final String KEY_SET_COOKIE = "Set-Cookie";
    public static final String KEY_USER_AGENT = "User-Agent";
    public static final String VALUE_USER_AGENT = getUserAgent();

    private Map<String, List<String>> headers;






    /**
     * Get language.
     */
    private static String getLanguage() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        StringBuilder builder = new StringBuilder(language);
        if (!TextUtils.isEmpty(country))
            builder.append('-').append(country).append(',').append(language);
        return builder.toString();
    }

    /**
     * Get User-Agent.
     */
    private static String getUserAgent() {
        String webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/534.30 (KHTML, like Gecko) Version/5.0 %sSafari/534.30";

        StringBuffer buffer = new StringBuffer();
        buffer.append(Build.VERSION.RELEASE).append("; ");

        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        if (TextUtils.isEmpty(language)) {
            buffer.append(language.toLowerCase(locale));
            final String country = locale.getCountry();
            if (!TextUtils.isEmpty(country)) {
                buffer.append("-");
                buffer.append(country.toLowerCase(locale));
            }
        } else {
            buffer.append("en");
        }
        if ("REL".equals(Build.VERSION.CODENAME)) {
            if (Build.MODEL.length() > 0) {
                buffer.append("; ");
                buffer.append(Build.MODEL);
            }
        }
        if (Build.ID.length() > 0) {
            buffer.append(" Api/");
            buffer.append(Build.ID);
        }
        return String.format(webUserAgent, buffer, "Mobile ");
    }

}
