package me.ibore.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public interface ConnectFactory {

    HttpURLConnection connect(Request request) throws IOException;

}
