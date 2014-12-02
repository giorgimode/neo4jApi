package com.poolingpeople.utils.neo4jApi;

import java.util.Map;

public class CypherRequest extends Neo4jRequest {

    String query = "";

    public CypherRequest(String query, Map<String, Object> params) {
        super(new CypherRequestBody(query, params));
        this.query = query;
        path = "/db/data/cypher";
    }

    @Override
    public Neo4jResponse getResponseInstance() {
        return new CypherResponse();
    }

    protected static class CypherRequestBody {
        String query;
        Map<String, Object> params;

        public CypherRequestBody(String query, Map<String, Object> params){

            this.query = query;
            this.params = params;

        }

        public Map<String, Object> getParams() {
            return params;
        }

        public String getQuery() {
            return query;
        }
    }

    public String getQuery() {
        return query;
    }
}
