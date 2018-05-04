package me.ibore.http;

import java.nio.charset.Charset;

import static me.ibore.http.Headers.VALUE_APPLICATION_JSON;

public class JsonBody extends StringBody {
    
    public JsonBody(String body) {
        this(body, Kalle.getConfig().getCharset());
    }

    public JsonBody(String body, Charset charset) {
        super(body, charset, VALUE_APPLICATION_JSON);
    }
}