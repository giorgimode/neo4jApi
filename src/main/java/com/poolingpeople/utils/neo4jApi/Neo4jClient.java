package com.poolingpeople.utils.neo4jApi;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

/**
 * Created by alacambra on 23.06.14.
 */
public class Neo4jClient {

    // TODO: put host and port into config file
    String host = "127.0.0.1";
    String port = "7474";

//    @Inject
    Logger logger = LoggerFactory.getLogger(Neo4jClient.class);

    ObjectMapper mapper = new ObjectMapper();

    public Neo4jClient() {
    }

    public Neo4jResponse post(Neo4jRequest neo4jRequest){

        String body = neo4jRequest.getBody();
        Entity<?> entity = Entity.json(body);
        logger.debug(body);
        Neo4jResponse response = neo4jRequest.getResponseInstance().load(getBuilder(neo4jRequest).post(entity));
        return response;

    }

    public Neo4jResponse get(Neo4jRequest neo4jRequest) {

        Neo4jResponse response = neo4jRequest.getResponseInstance().load(getBuilder(neo4jRequest).get());
        return response;

    }

    private Builder getBuilder(Neo4jRequest neo4jRequest){
        /*
          TODO: don't use a new client for each new com.poolingpeople.utils.neo4j.neo4j request
          Instead we want to use the same client for each request and have to close the connection
          after a request is done.
         */
        Client client = ClientBuilder.newClient();
        Builder builder = client.target(getURL() + neo4jRequest.getPath())
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.CHARSET_PARAMETER);

        return builder;
    }

    private String getURL(){
        // TODO: put host and port into config file
        return "http://" + host + ":" + port;
    }
}
