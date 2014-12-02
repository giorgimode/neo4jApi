package com.poolingpeople.utils.neo4jApi;

import javax.ws.rs.core.Response;

/**
 * Created by alacambra on 07.11.14.
 */
public class Neo4jClientErrorException extends Neo4jException{

    private Response response;
    private String exceptionFullName;
    private String exceptionName;

    public Neo4jClientErrorException(String message, String exceptionName, String exceptionFullName, Response response) {
        super(message);
        this.exceptionFullName = exceptionFullName;
        this.exceptionName = exceptionName;
        this.response = response;
    }

    public String getExceptionFullName() {
        return exceptionFullName;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public Response getResponse() {
        return response;
    }
}
