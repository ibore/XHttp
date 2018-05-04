package me.ibore.http.simple;

import me.ibore.http.Response;

import java.lang.reflect.Type;

public interface Converter {

    /**
     * Default converter.
     */
    Converter DEFAULT = new Converter() {
        @Override
        public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
            S succeedData = null;

            if (succeed == String.class) succeedData = (S) response.body().string();

            return SimpleResponse.<S, F>newBuilder()
                    .code(response.code())
                    .headers(response.headers())
                    .fromCache(fromCache)
                    .succeed(succeedData)
                    .build();
        }
    };

    /**
     * Convert data to the result of the target type.
     *
     * @param succeed   the data type when the business succeed.
     * @param failed    the data type when the business failed.
     * @param response  response of request.
     * @param fromCache the response is from the cache.
     * @param <S>       the data type.
     * @param <F>       the data type.
     * @return {@link SimpleResponse}
     * @throws Exception to prevent accidents.
     */
    <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception;
}