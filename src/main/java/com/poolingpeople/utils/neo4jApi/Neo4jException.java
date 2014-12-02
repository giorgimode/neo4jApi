package com.poolingpeople.utils.neo4jApi;

import javax.ws.rs.core.Response;

/**
 * Created by alacambra on 07.11.14.
 */
public class Neo4jException extends RuntimeException{

    Response response;

    public Neo4jException(Response response, String message) {
        super(message);
        this.response = response;
    }

    public Neo4jException() {
    }

    public Neo4jException(String message) {
        super(message);
    }

    public Response getResponse() {
        return response;
    }
}
