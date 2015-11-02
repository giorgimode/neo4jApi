package com.poolingpeople.neo4j.api.control.parsing;

/**
 * Created by alacambra on 1/18/15.
 */
public class InvalidJsonValueException extends RuntimeException {

    public InvalidJsonValueException() {
    }

    public InvalidJsonValueException(String message) {
        super(message);
    }

    public InvalidJsonValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJsonValueException(Throwable cause) {
        super(cause);
    }

    public InvalidJsonValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
