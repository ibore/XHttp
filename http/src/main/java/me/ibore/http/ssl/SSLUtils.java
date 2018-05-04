package me.ibore.http.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class SSLUtils {

    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static final SSLSocketFactory SSL_SOCKET_FACTORY = new CompatSSLSocketFactory();
}