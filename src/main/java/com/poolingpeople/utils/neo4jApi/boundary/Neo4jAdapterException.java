package com.poolingpeople.utils.neo4jApi.boundary;

/**
 * Created by alacambra on 14/04/15.
 */
public class Neo4jAdapterException extends RuntimeException{
    public Neo4jAdapterException() {
    }

    public Neo4jAdapterException(String message) {
        super(message);
    }

    public Neo4jAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public Neo4jAdapterException(Throwable cause) {
        super(cause);
    }

    public Neo4jAdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
