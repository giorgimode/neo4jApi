package com.poolingpeople.utils.neo4jApi.boundary;

import com.poolingpeople.utils.neo4jApi.control.ResponseStreamingParser;
import com.poolingpeople.utils.neo4jApi.control.StatementBuilder;

import javax.inject.Inject;
import javax.json.JsonObject;
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

//    @Inject
//    RequestBodyBuilderHelper helper;

    @Inject
    StatementBuilder helper;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Inject
    ResponseStreamingParser responseParser;

    @Inject
    Endpoint endpoint;

//    @Inject
//    MultiStatementBuilder multiStatementBuilder;

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<Map<String, Object>> cypherOneColumnQuery(Statement statement){

        this.logger.log(Level.FINE, statement.getQuery());
        Response response = sendRequest(new MultiStatementBuilder().add(statement).build());
        List<Map<String,Map<String,Object>>> r = responseParser.parseSimpleStatementOrException(response);
        List<Map<String, Object>> res = toOneColumn(r);

        return res;
    }

    private Response sendRequest(JsonObject body){
        logger.fine(body.toString());
        Response response = getCypherBuilder().post(Entity.json(body));
        return response;
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<Map<String, Map<String, Object>>> cypherMultipleEntityColumnsQuery(Statement statement){

        this.logger.log(Level.FINE, statement.getQuery());
        Response response = sendRequest(new MultiStatementBuilder().add(statement).build());
        List<Map<String,Map<String,Object>>> res = responseParser.parseSimpleStatementOrException(response);

        return res;
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public List<Map<String, Object>> cypherParamsQuery(Statement statement){

        this.logger.log(Level.FINE, statement.getQuery());
        Response response = sendRequest(new MultiStatementBuilder().add(statement).build());
        List<Map<String,Map<String,Object>>> r = responseParser.parseSimpleStatementOrException(response);
        List<Map<String, Object>> res = toParams(r);

        return res;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public Map<String, Object> cypherSingleEntityQuery(Statement statement){

        this.logger.log(Level.FINE, statement.getQuery());
        Response response = sendRequest(new MultiStatementBuilder().add(statement).build());
        List<Map<String,Map<String,Object>>> r = responseParser.parseSimpleStatementOrException(response);
        Map<String, Object> res = toSingleEntity(r);
        return res;

    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public Object cypherSinglePropertyQuery(Statement statement){
        this.logger.log(Level.FINE, statement.getQuery());
        Response response = sendRequest(new MultiStatementBuilder().add(statement).build());
        List<Map<String,Map<String,Object>>> r = responseParser.parseSimpleStatementOrException(response);

        Object res = toSingleProperty(r);
        return res;
    }

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<List<Map<String, Object>>> cypherOneColumnQuery(MultiStatementBuilder statements){

        Response response = sendRequest(statements.build());
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> toOneColumn(sts)).collect(Collectors.toList());
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<List<Map<String, Map<String, Object>>>> cypherMultipleEntityColumnsQuery(MultiStatementBuilder statements){
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> res = responseParser.parseMultiStatementOrException(response);
        return res;
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public List<List<Map<String, Object>>> cypherParamsQuery(MultiStatementBuilder statements){
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> toParams(sts)).collect(Collectors.toList());
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public List<Map<String, Object>> cypherSingleEntityQuery(MultiStatementBuilder statements){
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> toSingleEntity(sts)).collect(Collectors.toList());
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public List<Object> cypherSinglePropertyQuery(MultiStatementBuilder statements){
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> toSingleProperty(sts)).collect(Collectors.toList());
    }

    List<Map<String, Object>> toOneColumn(List<Map<String, Map<String, Object>>> sts){
        return sts.stream().map(c -> {

            if(c.keySet().size() != 1) throw new InvalidParameterException(c.keySet().size() + " column found");
            return c.values().stream().findFirst().get();

        }).collect(Collectors.toList());
    }

    List<Map<String, Object>> toParams(List<Map<String,Map<String,Object>>> sts){
        return sts.stream().map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.keySet().stream().forEach(k -> map.put(k, c.get(k).get(k)));
                    return map;
                }
        ).collect(Collectors.toList());
    }

    Map<String, Object> toSingleEntity(List<Map<String,Map<String,Object>>> sts){
        return sts.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            if (c.size() > 1) throw new Neo4jException("More than one entity found");

            return c.values().stream().findFirst().get();
        }).findFirst().orElse(new HashMap<>());
    }

    Object toSingleProperty(List<Map<String, Map<String, Object>>> sts){

        Object obj = sts.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            return c.values().stream().findFirst().get();
        }).map(entity -> {

            if(entity.size() > 1) throw new Neo4jException("More than one result returned");
            if(entity.values().size() > 1) throw new Neo4jException("More than one column found");
            return entity.values().iterator().next();

        }).findFirst().orElse(null);

        return obj;
    }

    private javax.ws.rs.client.Invocation.Builder getCypherBuilder(){
        Client client = ClientBuilder.newClient();
        return client.target(endpoint.getCypherEndpoint())
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type", "application/json");
    }

    public Neo4jClient setEndpoint(Endpoint endpoint) {

        if(endpoint == null){
            throw new InvalidParameterException("Endpoint can not be null");
        }

        this.endpoint = endpoint;
        return this;
    }

    public void manipulativeQuery(Statement statement){
        manipulativeQuery(new MultiStatementBuilder().add(statement));
    }

    public void manipulativeQuery(MultiStatementBuilder statements){
        getCypherBuilder().post(Entity.json(statements.build()));
    }
}