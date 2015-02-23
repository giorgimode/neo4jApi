package com.poolingpeople.utils.neo4jApi;

/**
 * Created by alacambra on 23.02.15.
 */
public class NotSingleResultException extends RuntimeException {
    public NotSingleResultException() {
    }

    public NotSingleResultException(String message) {
        super(message);
    }

    public NotSingleResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSingleResultException(Throwable cause) {
        super(cause);
    }

    public NotSingleResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
