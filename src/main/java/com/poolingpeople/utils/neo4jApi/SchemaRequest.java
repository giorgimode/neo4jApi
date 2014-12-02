package com.poolingpeople.utils.neo4jApi;

/**
 * Created by eduardo on 6/25/14.
 */
public class SchemaRequest extends Neo4jRequest {

    public SchemaRequest(String resource) {
        super();
        path = "/db/data/schema/" + resource;
    }

    @Override
    public Neo4jResponse getResponseInstance() {
        return new SchemaResponse();
    }
}