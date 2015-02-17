package com.poolingpeople.utils.neo4jApi;


import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Neo4jRestApiAdapterImpl implements Neo4jRestApiAdapter{

    @Inject
    RequestBodyBuilderHelper helper;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Inject
    ResponseStreamingParser responseParser;
    
    @Inject
    Endpoint endpoint;

    public Neo4jRestApiAdapterImpl(){
    }

    public Neo4jRestApiAdapterImpl(Endpoint endpoint){
        this.endpoint = endpoint;
    }

    @Override
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, Map<String, Object> params) {
        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseSimpleListOrException(response);
    }

    @Override
    public List<Map<String, Object>> runParametrizedCypherQuery(String query) {
        return runParametrizedCypherQuery(query, null);
    }

    @Override
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, Map<String, Object> params) {
        this.logger.log(Level.FINE, "Neo4J request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        return responseParser.parseOrException(response);
    }

    @Override
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
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, null)));
    }

    public void dropIndex(String label, String property) {
        String query = "DROP INDEX ON :" + label + "(" + property + ")";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, null)));
    }

    public void createConstraint(String label, String property) {
        String query = "CREATE CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, null)));    }

    @Override
    public void dropConstraint(String label, String property) {
        String query = "DROP CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, null)));
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
