package com.poolingpeople.utils.neo4jApi.boundary;

/**
 * Created by alacambra on 14/04/15.
 */
public class Neo4jClientException extends RuntimeException{
    public Neo4jClientException() {
    }

    public Neo4jClientException(String message) {
        super(message);
    }

    public Neo4jClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public Neo4jClientException(Throwable cause) {
        super(cause);
    }

    public Neo4jClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
