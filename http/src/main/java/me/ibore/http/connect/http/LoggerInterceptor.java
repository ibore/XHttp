package me.ibore.http.connect.http;

import android.text.TextUtils;
import android.util.Log;

import me.ibore.http.Headers;
import me.ibore.http.Request;
import me.ibore.http.Response;
import me.ibore.http.connect.Interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class LoggerInterceptor implements Interceptor {

    private final String mTag;
    private final boolean isEnable;

    public LoggerInterceptor(String tag, boolean isEnable) {
        this.mTag = tag;
        this.isEnable = isEnable;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (isEnable) {
            String url = request.url().toString();

            StringBuilder requestLog = new StringBuilder(String.format(" \nPrint Request: %1$s.", url));
            requestLog.append(String.format("\nMethod: %1$s.", request.method().name()));

            Headers toHeaders = request.headers();
            for (Map.Entry<String, List<String>> entry : toHeaders.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                requestLog.append(String.format("\n%1$s: %2$s.", key, TextUtils.join(";", values)));
            }
            Log.i(mTag, requestLog.toString());

            Response response = chain.proceed(request);
            StringBuilder responseLog = new StringBuilder(String.format(" \nPrint Response: %1$s.", url));
            responseLog.append(String.format(Locale.getDefault(), "\nCode: %1$d", response.code()));

            Headers fromHeaders = response.headers();
            for (Map.Entry<String, List<String>> entry : fromHeaders.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                responseLog.append(String.format("\n%1$s: %2$s.", key, TextUtils.join(";", values)));
            }
            Log.i(mTag, responseLog.toString());
            return response;
        }
        return chain.proceed(request);
    }

}