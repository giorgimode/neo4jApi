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
        List<Map<String, Object>> res = Converter.toOneColumn(r);

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
        List<Map<String, Object>> res = Converter.toParams(r);

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
        Map<String, Object> res = Converter.toSingleEntity(r);
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

        Object res = Converter.toSingleProperty(r);
        return res;
    }

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<List<Map<String, Object>>> cypherOneColumnQuery(MultiStatementBuilder statements){
        this.logger.log(Level.FINE, statements.build().toString());
        Response response = sendRequest(statements.build());
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> Converter.toOneColumn(sts)).collect(Collectors.toList());
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<List<Map<String, Map<String, Object>>>> cypherMultipleEntityColumnsQuery(MultiStatementBuilder statements){
        this.logger.log(Level.FINE, statements.build().toString());
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
        this.logger.log(Level.FINE, statements.build().toString());
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> Converter.toParams(sts)).collect(Collectors.toList());
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public List<Map<String, Object>> cypherSingleEntityQuery(MultiStatementBuilder statements){
        this.logger.log(Level.FINE, statements.build().toString());
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> Converter.toSingleEntity(sts)).collect(Collectors.toList());
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public List<Object> cypherSinglePropertyQuery(MultiStatementBuilder statements){
        this.logger.log(Level.FINE, statements.build().toString());
        Response response = getCypherBuilder().post(Entity.json(statements.build()));
        List<List<Map<String,Map<String,Object>>>> r = responseParser.parseMultiStatementOrException(response);
        return r.stream().map( sts -> Converter.toSingleProperty(sts)).collect(Collectors.toList());
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