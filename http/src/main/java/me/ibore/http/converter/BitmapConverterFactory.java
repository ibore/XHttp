package me.ibore.http.converter;

import android.graphics.Bitmap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class BitmapConverterFactory extends Converter.Factory {

    private BitmapConverterFactory() {
    }

    public static BitmapConverterFactory create(){
        return new BitmapConverterFactory();
    }

    @Override
    public Converter<ResponseBody, Bitmap> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new BitmapConverter();
    }

}