package me.ibore.http;

import java.nio.charset.Charset;

import static me.ibore.http.Headers.VALUE_APPLICATION_XML;

public class XmlBody extends StringBody {

    public XmlBody(String body) {
        this(body, XHttp.getConfig().getCharset());
    }

    public XmlBody(String body, Charset charset) {
        super(body, charset, VALUE_APPLICATION_XML);
    }
}