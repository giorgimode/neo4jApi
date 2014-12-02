package com.poolingpeople.utils.neo4jApi;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public abstract class Neo4jRequest <R extends Neo4jResponse> {

    String body;
    String path;

    public String getPath() {
        return path;
    }

    public Neo4jRequest() {
    }

    public Neo4jRequest(Object bodyClass) {
        loadBody(bodyClass);
    }

    protected void loadBody(Object bodyClass){
        ObjectMapper mapper = new ObjectMapper();
        try {
            body = mapper.writeValueAsString(bodyClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBody(){
        return this.body;
    }

    public abstract R getResponseInstance();

    @Override
    public String toString() {
        return "body:" + body + "\npath: " + path;
    }
}