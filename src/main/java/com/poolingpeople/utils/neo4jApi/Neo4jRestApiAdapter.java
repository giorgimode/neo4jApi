package com.poolingpeople.utils.neo4jApi;


import com.poolingpeople.utils.neo4jApi.parsing.CypherQueryProperties;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.security.InvalidParameterException;
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

    public Neo4jRestApiAdapter(RequestBodyBuilderHelper helper, ResponseStreamingParser responseParser, Endpoint endpoint) {
        this.helper = helper;
        this.responseParser = responseParser;
        this.endpoint = endpoint;
    }

    public List<Map<String, Object>> runParametrizedCypherQuery(String query, CypherQueryProperties params) {
        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseSimpleListOrException(response);
    }
    
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, Map<String, Object> params) {
        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);

        return runParametrizedCypherQuery(query,
                new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL).add("id", params));
    }

    @Deprecated
    public List<Map<String, Object>> runParametrizedCypherQuery(String query) {
        return runParametrizedCypherQuery(query, new HashMap<>());
    }
    
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, CypherQueryProperties params) {
        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseOrException(response);
    }

    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, Map<String, Object> params) {

        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        return runCypherQuery(query, new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL).add("id", params));
    }

    public Map<String, Object> getParametrizedSingleResult(String query, CypherQueryProperties params){

        List<Map<String, Object>> r = runParametrizedCypherQuery(query, params);

        if(r.size() > 1){
            throw new NotSingleResultException("Found " + r.size() + " results");
        }

        return r.get(0);
    }

    public Map<String, Map<String, Object>> getSingleResult(String query, CypherQueryProperties params){

        Collection<Map<String, Map<String, Object>>> r = runCypherQuery(query, params);

        if(r.size() > 1){
            throw new NotSingleResultException("Found " + r.size() + " results");
        }

        return r.iterator().next();
    }

    @Deprecated
    public boolean schemaIsCorrectlyLoaded() {
        return true;
    }

    @Deprecated
    public List<Map<String, Object>> getConstraints() {
        return new ArrayList<>();
    }

    @Deprecated
    public List<Map<String, Object>> getIndexes() {
        return new ArrayList<>();
    }

    @Deprecated
    public void createIndex(String label, String property) {
        String query = "CREATE INDEX ON :" + label + "(" + property + ")";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));
    }

    @Deprecated
    public void dropIndex(String label, String property) {
        String query = "DROP INDEX ON :" + label + "(" + property + ")";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));
    }

    @Deprecated
    public void createConstraint(String label, String property) {
        String query = "CREATE CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, new HashMap<String, Object>())));    }

    @Deprecated
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

    public Neo4jRestApiAdapter setEndpoint(Endpoint endpoint) {

        if(endpoint == null){
            throw new InvalidParameterException("Endpoint can not be null");
        }

        this.endpoint = endpoint;
        return this;
    }
}