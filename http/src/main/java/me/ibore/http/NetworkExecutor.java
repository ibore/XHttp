package me.ibore.http;

public class NetworkExecutor {

    /**
     * Perform network connection.
     *
     * @param request {@link BasicRequest}.
     * @return {@link Network}.
     * @throws Exception maybe.
     */
    Network execute(BasicRequest<?> request) throws Exception;

}
