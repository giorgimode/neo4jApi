package com.poolingpeople.utils.neo4jApi;


import com.poolingpeople.utils.neo4jApi.parsing.CypherQueryProperties;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Neo4jRestApiAdapter {

    @Inject
    RequestBodyBuilderHelper helper;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Inject
    ResponseStreamingParser responseParser;
    
    @Inject
    Endpoint endpoint;

    public Neo4jRestApiAdapter(){
    }

    public Neo4jRestApiAdapter(Endpoint endpoint){
        this.endpoint = endpoint;
    }

    
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, CypherQueryProperties params) {
        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseSimpleListOrException(response);
    }
    
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, Map<String, Object> params) {
        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseSimpleListOrException(response);
    }

    @Deprecated
    public List<Map<String, Object>> runParametrizedCypherQuery(String query) {
        return runParametrizedCypherQuery(query, new HashMap<String, Object>());
    }
    
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, CypherQueryProperties params) {
        this.logger.log(Level.FINE, "Neo4J request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseOrException(response);
    }
    
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, Map<String, Object> params) {
        return null;
    }
    
    public boolean schemaIsCorrectlyLoaded() {
        return true;
    }

    public List<Map<String, Object>> getConstraints() {
        return new ArrayList<>();
    }

    public List<Map<String, Object>> getIndexes() {
        return new ArrayList<>();
    }

    public void createIndex(String label, String property) {
        String query = "CREATE INDEX ON :" + label + "(" + property + ")";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));
    }

    public void dropIndex(String label, String property) {
        String query = "DROP INDEX ON :" + label + "(" + property + ")";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));
    }

    public void createConstraint(String label, String property) {
        String query = "CREATE CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));    }

    
    public void dropConstraint(String label, String property) {
        String query = "DROP CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));
    }

    private javax.ws.rs.client.Invocation.Builder getCypherBuilder(){
        Client client = ClientBuilder.newClient();
        return client.target(endpoint.getCypherEndpoint())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json");
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
}