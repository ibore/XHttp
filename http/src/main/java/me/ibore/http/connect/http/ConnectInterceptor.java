package me.ibore.http.connect.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import me.ibore.http.Headers;
import me.ibore.http.Kalle;
import me.ibore.http.Request;
import me.ibore.http.RequestBody;
import me.ibore.http.RequestMethod;
import me.ibore.http.Response;
import me.ibore.http.ResponseBody;
import me.ibore.http.connect.ConnectFactory;
import me.ibore.http.connect.Connection;
import me.ibore.http.connect.Interceptor;
import me.ibore.http.connect.Network;
import me.ibore.http.connect.StreamBody;
import me.ibore.http.cookie.CookieManager;
import me.ibore.http.exception.ConnectException;
import me.ibore.http.exception.ConnectTimeoutError;
import me.ibore.http.exception.HostError;
import me.ibore.http.exception.NetworkError;
import me.ibore.http.exception.ReadException;
import me.ibore.http.exception.ReadTimeoutError;
import me.ibore.http.exception.URLError;
import me.ibore.http.exception.WriteException;
import me.ibore.http.util.IOUtils;

import static me.ibore.http.Headers.KEY_CONTENT_LENGTH;
import static me.ibore.http.Headers.KEY_CONTENT_TYPE;
import static me.ibore.http.Headers.KEY_COOKIE;
import static me.ibore.http.Headers.KEY_HOST;
import static me.ibore.http.Headers.KEY_SET_COOKIE;

public class ConnectInterceptor implements Interceptor {

    private final CookieManager mCookieManager;
    private final ConnectFactory mFactory;
    private final Network mNetwork;

    public ConnectInterceptor() {
        this.mCookieManager = new CookieManager(Kalle.getConfig().getCookieStore());
        this.mFactory = Kalle.getConfig().getConnectFactory();
        this.mNetwork = Kalle.getConfig().getNetwork();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestMethod method = request.method();

        Connection connection;
        if (method.allowBody()) {
            Headers headers = request.headers();
            RequestBody body = request.body();
            headers.set(KEY_CONTENT_LENGTH, Long.toString(body.length()));
            headers.set(KEY_CONTENT_TYPE, body.contentType());
            connection = connect(request);
            writeBody(connection, body);
        } else {
            connection = connect(request);
        }
        return readResponse(connection, request);
    }

    /**
     * Connect to the server to change the connection anomalies occurred.
     *
     * @param request target request.
     * @return connection between client and server.
     * @throws ConnectException anomalies that occurred during the connection.
     */
    private Connection connect(Request request) throws ConnectException {
        if (!mNetwork.isAvailable())
            throw new NetworkError(String.format("Network Unavailable: %1$s.", request.url()));

        try {
            Headers headers = request.headers();
            URI uri = new URI(request.url().toString());
            List<String> cookieHeader = mCookieManager.get(uri);
            if (cookieHeader != null && !cookieHeader.isEmpty())
                headers.add(KEY_COOKIE, cookieHeader);
            headers.set(KEY_HOST, uri.getHost());
            return mFactory.connect(request);
        } catch (URISyntaxException e) {
            throw new URLError(String.format("The url syntax error: %1$s.", request.url()), e);
        } catch (MalformedURLException e) {
            throw new URLError(String.format("The url is malformed: %1$s.", request.url()), e);
        } catch (UnknownHostException e) {
            throw new HostError(String.format("Hostname can not be resolved: %1$s.", request.url()), e);
        } catch (SocketTimeoutException e) {
            throw new ConnectTimeoutError(String.format("Connect time out: %1$s.", request.url()), e);
        } catch (Exception e) {
            throw new ConnectException(String.format("An unknown exception: %1$s.", request.url()), e);
        }
    }

    private void writeBody(Connection connection, RequestBody body) throws WriteException {
        try {
            OutputStream stream = connection.getOutputStream();
            body.writeTo(IOUtils.toBufferedOutputStream(stream));
            IOUtils.closeQuietly(stream);
        } catch (Exception e) {
            throw new WriteException(e);
        }
    }

    private Response readResponse(Connection connection, Request request) throws ReadException {
        try {
            int code = connection.getCode();
            Headers headers = parseResponseHeaders(connection.getHeaders());

            List<String> cookieList = headers.get(KEY_SET_COOKIE);
            if (cookieList != null && !cookieList.isEmpty())
                mCookieManager.add(URI.create(request.url().toString()), cookieList);

            String contentType = headers.getContentType();
            ResponseBody body = new StreamBody(contentType, connection.getInputStream());
            return Response.newBuilder().code(code).headers(headers).body(body).build();
        } catch (SocketTimeoutException e) {
            throw new ReadTimeoutError(String.format("Read data time out: %1$s.", request.url()), e);
        } catch (Exception e) {
            throw new ReadException(e);
        }
    }

    private Headers parseResponseHeaders(Map<String, List<String>> headersMap) {
        Headers headers = new Headers();
        for (Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }
        return headers;
    }
}