package com.poolingpeople.utils.neo4jApi.boundary;

import com.poolingpeople.utils.neo4jApi.control.RequestBodyBuilderHelper;
import com.poolingpeople.utils.neo4jApi.control.ResponseStreamingParser;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by alacambra on 15.04.15.
 */
public class Neo4jClient {

    @Inject
    RequestBodyBuilderHelper helper;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Inject
    ResponseStreamingParser responseParser;

    @Inject
    Endpoint endpoint;

    @Inject
    MultiStatementBuilder multiStatementBuilder;

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<Map<String, Object>> cypherOneColumnQuery(String query, HasQueryParams params){

        this.logger.log(Level.FINE, query);

        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, params)));

        List<Map<String,Map<String,Object>>> r = responseParser.parseOrException(response);

        List<Map<String, Object>> res = r.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            return c.values().stream().findFirst().get();
        }).collect(Collectors.toList());

        return res;
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<Map<String, Map<String, Object>>> cypherMultipleEntityColumnsQuery(String query, HasQueryParams params){

        this.logger.log(Level.FINE, query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, params)));
        List<Map<String,Map<String,Object>>> res = responseParser.parseOrException(response);

        return res;
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public List<Map<String, Object>> cypherParamsQuery(String query, HasQueryParams params){

        this.logger.log(Level.FINE, "Neo4j request with cypher query: " + query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query,params)));
        List<Map<String, Object>> res =  responseParser.parseSimpleListOrException(response);

        return res;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public Map<String, Object> cypherSingleEntityQuery(String query, HasQueryParams params){

        this.logger.log(Level.FINE, query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, params)));
        List<Map<String,Map<String,Object>>> r = responseParser.parseOrException(response);

        Map<String, Object> res = r.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            return c.values().stream().findFirst().get();
        }).map(entity -> {
            if (entity.size() > 1) throw new Neo4jException();
            return entity;
        }).findFirst().orElse(new HashMap<>());

        return res;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public Object cypherSinglePropertyQuery(String query, HasQueryParams params){
        this.logger.log(Level.FINE, query);
        Response response = getCypherBuilder().post(Entity.json(helper.getCypherBody(query, params)));
        List<Map<String,Map<String,Object>>> r = responseParser.parseOrException(response);

        Object res = r.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            return c.values().stream().findFirst().get();
        }).map(entity -> {

            if(entity.size() > 1) throw new Neo4jException("More than one result returned");
            if(entity.values().size() > 1) throw new Neo4jException("More than one column found");
            return entity.values().iterator().next();

        }).findFirst().orElse(null);

        return res;
    }

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<List<Map<String, Object>>> cypherOneColumnQuery(MultiStatementBuilder statements){

        Response response = getCypherBuilder().post(Entity.json(multiStatementBuilder.build()));

        List<Map<String,Map<String,Object>>> r = responseParser.parseOrException(response);

        List<Map<String, Object>> res = r.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            return c.values().stream().findFirst().get();
        }).collect(Collectors.toList());

        return null;
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<List<Map<String, Map<String, Object>>>> cypherMultipleEntityColumnsQuery(MultiStatementBuilder statements){
        return null;
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public List<List<Map<String, Object>>> cypherParamsQuery(MultiStatementBuilder statements){

        return null;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public List<Map<String, Object>> cypherSingleEntityQuery(MultiStatementBuilder statements){
        return null;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public List<Object> cypherSinglePropertyQuery(MultiStatementBuilder statements){
        return null;
    }

    private javax.ws.rs.client.Invocation.Builder getCypherBuilder(){
        Client client = ClientBuilder.newClient();
        return client.target(endpoint.getCypherEndpoint())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json");
    }

    public Neo4jClient setEndpoint(Endpoint endpoint) {

        if(endpoint == null){
            throw new InvalidParameterException("Endpoint can not be null");
        }

        this.endpoint = endpoint;
        return this;
    }
}