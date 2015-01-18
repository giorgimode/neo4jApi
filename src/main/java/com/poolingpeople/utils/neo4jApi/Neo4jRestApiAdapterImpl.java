package com.poolingpeople.utils.neo4jApi;


//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//@ApplicationScoped
//@Stateless
public class Neo4jRestApiAdapterImpl implements Neo4jRestApiAdapter{

    @Inject
    RequestBodyBuilderHelper helper;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Inject
    ResponseStreamingParser responseParser;
    private static final String cypherEndpoint = "/db/data/transaction";
    private String host = "localhost";
    private int port = 7474;

    public Neo4jRestApiAdapterImpl(){
        this("7474");
    }

    public Neo4jRestApiAdapterImpl(String port){
        this("localhost", port);
    }

    public Neo4jRestApiAdapterImpl(String host, String port){

        if("localhost".equals(host) && System.getenv("neo4j") != null) {
            host = System.getenv("neo4j");
            logger.log(Level.FINER, "envelope found. Using " + host + ":" + port);
        }else{
            logger.log(Level.FINER, "envelope NOT found. Using " + host + ":" + port);
        }
    }

    private URI getCypherEndPointUri(){
        try {
            return new URI("http://" + host + ":" + port + "/" + cypherEndpoint);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, Map<String, Object> params) {
        this.logger.log(Level.FINE, "Neo4J request with cypher query: " + query);

        Client client = ClientBuilder.newClient();
        Response response = client.target(getCypherEndPointUri())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(helper.getCypherBody(query,params)));

        return responseParser.parseSimpleListOrException(response);
    }

    @Override
    public List<Map<String, Object>> runParametrizedCypherQuery(String query) {
        return runParametrizedCypherQuery(query, null);
    }

    @Override
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, Map<String, Object> params) {

        this.logger.log(Level.FINE, "Neo4J request with cypher query: " + query);

        Client client = ClientBuilder.newClient();
        Response response = client.target(getCypherEndPointUri())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(helper.getCypherBody(query,params)));

        return responseParser.parseOrException(response);
    }

    @Override
    public boolean schemaIsCorrectlyLoaded() {
//        List<Map<String, Object>> constraints = this.getConstraints();

        /*
          TODO: this is only temporarily for convenience and should be removed in production!
          We could define a map with constraints that have to be present. If one is missing we can set it.
          */
//        if (constraints.isEmpty()) {
//            this.logger.warn("DB schema is invalid! Setting UUID constraint!");
//            this.createConstraint("UUID", "uuid");
//        }

        return true;
    }

    public List<Map<String, Object>> getConstraints() {
//        SchemaResponse response = (SchemaResponse) neo4jClient.get(new SchemaRequest("constraint"));
//        return response.getReceivedDataAsMap();
        return new ArrayList<>();
    }

    public List<Map<String, Object>> getIndexes() {
        return new ArrayList<>();
    }


    public void createIndex(String label, String property) {
        String query = "CREATE INDEX ON :" + label + "(" + property + ")";
        Client client = ClientBuilder.newClient();
        Response response = client.target(getCypherEndPointUri())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(helper.getCypherBody(query, null)));
    }

    public void dropIndex(String label, String property) {
        String query = "DROP INDEX ON :" + label + "(" + property + ")";
        Client client = ClientBuilder.newClient();
        Response response = client.target(getCypherEndPointUri())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(helper.getCypherBody(query, null)));
    }

    public void createConstraint(String label, String property) {
        String query = "CREATE CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Client client = ClientBuilder.newClient();
        Response response = client.target(getCypherEndPointUri())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(helper.getCypherBody(query, null)));    }

    @Override
    public void dropConstraint(String label, String property) {
        String query = "DROP CONSTRAINT ON (a:" + label + ") ASSERT a." + property + " IS UNIQUE";
        Client client = ClientBuilder.newClient();
        Response response = client.target(getCypherEndPointUri())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(helper.getCypherBody(query, null)));        }
}
