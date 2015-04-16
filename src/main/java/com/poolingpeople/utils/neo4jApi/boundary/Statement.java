package com.poolingpeople.utils.neo4jApi.boundary;

/**
 * Created by alacambra on 16.04.15.
 */
public class Statement {

    String query;
    QueryParams params = new QueryParams();

    public Statement setQuery(String query) {
        this.query = query;
        return this;
    }

    public Statement addParam(String param, Object value) {
        params.add(param, value);
        return this;
    }

    public void setParams(QueryParams params) {
        this.params = params;
    }
}
