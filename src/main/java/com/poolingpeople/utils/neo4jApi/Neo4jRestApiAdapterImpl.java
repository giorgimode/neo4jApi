package com.poolingpeople.utils.neo4jApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.*;

//@ApplicationScoped
public class Neo4jRestApiAdapterImpl implements Neo4jRestApiAdapter{

    Neo4jClient neo4jClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    ResponseParser responseParser = new ResponseParser();

    public Neo4jRestApiAdapterImpl(){
        this("7474");
    }

    public Neo4jRestApiAdapterImpl(String port){
        this("localhost", port);
    }

    public Neo4jRestApiAdapterImpl(String host, String port){
        neo4jClient = new Neo4jClient(host, port);
    }

    @Override
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, Map<String, Object> params) {
        this.logger.debug("Neo4J request with cypher query: " + query);
        CypherResponse response = (CypherResponse) neo4jClient.post(new CypherRequest(query, params));

        return responseParser.parseSimpleListOrException(response);
    }

    @Override
    public List<Map<String, Object>> runParametrizedCypherQuery(String query) {
        return runParametrizedCypherQuery(query, null);
    }

    @Override
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, Map<String, Object> params) {

        this.logger.debug("Neo4J request with cypher query: " + query);
        CypherResponse response = (CypherResponse) neo4jClient.post(new CypherRequest(query, params));

        return responseParser.parseOrException(response);
    }

    @Override
    public boolean schemaIsCorrectlyLoaded() {
        List<Map<String, Object>> constraints = this.getConstraints();

        /*
          TODO: this is only temporarily for convenience and should be removed in production!
          We could define a map with constraints that have to be present. If one is missing we can set it.
          */
        if (constraints.isEmpty()) {
            this.logger.warn("DB schema is invalid! Setting UUID constraint!");
            this.createConstraint("UUID", "uuid");
        }

        return true;
    }

    public List<Map<String, Object>> getConstraints() {
        SchemaResponse response = (SchemaResponse) neo4jClient.get(new SchemaRequest("constraint"));
        return response.getReceivedDataAsMap();
    }

    public List<Map<String, Object>> getIndexes() {
        SchemaResponse response = (SchemaResponse) neo4jClient.get(new SchemaRequest("index"));
        return response.getReceivedDataAsMap();
    }


    public void createIndex(String label, String property) {
        String query = "CREATE INDEX ON :" + label + "(" + property + ")";
        neo4jClient.post(new CypherRequest(query, new HashMap<String, Object>()));
    }

    public void dropIndex(String label, String property) {
        String query = "DROP INDEX ON :" + label + "(" + property + ")";
        neo4jClient.post(new CypherRequest(query, new HashMap<String, Object>()));
    }

    public void createConstraint(String label, String property) {
        String query = "CREATE CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        neo4jClient.post(new CypherRequest(query, null));
    }

    @Override
    public void dropConstraint(String label, String property) {
        String query = "DROP CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        neo4jClient.post(new CypherRequest(query, null));
    }
}
