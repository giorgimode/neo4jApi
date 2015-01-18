package com.poolingpeople.utils.neo4jApi;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.StatesManager;

import javax.inject.Inject;
import javax.json.*;
import javax.json.stream.JsonParser;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by alacambra on 05.11.14.
 */
public class ResponseStreamingParser {

    @Inject
    StatesManager statesManager;

    public List<Map<String,Object>> parseList(String json) {
        return parseList(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    public List<Map<String,Object>> parseList(InputStream inputStream) {

        Collection<Map<String, Map<String, Object>>> r = statesManager.parse(inputStream).getResultMixed();
        List<Map<String,Object>> result = new ArrayList<>();

        for(Map<String, Map<String, Object>> row : r){
            Map<String, Object> newRow = new HashMap<>();
            for( Map<String, Object> values : row.values()){
                for(Map.Entry<String, Object> value: values.entrySet()){
                    newRow.put(value.getKey(), value.getValue());
                }
            }
            result.add(newRow);
        }

        return result;
    }

    public List<Map<String, Object>> parseSimpleListOrException(Response response){

        Response.Status.Family codeFamily = response.getStatusInfo().getFamily();

        switch (codeFamily){
            case CLIENT_ERROR:
                throw parseException(response);
            case SERVER_ERROR:
                throw new Neo4jException(response, "Neo4j reported 5xx error");
            default:
                return parseList(response.readEntity(String.class));
        }
    }

    public Collection<Map<String,Map<String,Object>>> parseOrException(Response response){

        Response.Status.Family codeFamily = response.getStatusInfo().getFamily();

        switch (codeFamily){
            case CLIENT_ERROR:
                throw parseException(response);
            case SERVER_ERROR:
                throw new Neo4jException(response, "Neo4j reported 5xx error");
            default:
                return parse(response.readEntity(String.class));
        }
    }

    public Collection<Map<String,Map<String,Object>>> parse(String json) {
        return statesManager.parse(json).getResultMixed();
    }

    public Collection<Map<String,Map<String,Object>>> parse(InputStream inputStream){
        return statesManager.parse(inputStream).getResultMixed();
    }

    private Neo4jClientErrorException parseException(Response response){

        Reader reader = new InputStreamReader(
                new ByteArrayInputStream(response.readEntity(String.class).getBytes(StandardCharsets.UTF_8)));
        JsonObject jsonObject = Json.createReader(reader).readObject();

        return new Neo4jClientErrorException(
                (String)getObjectFromJsonValue(jsonObject.get("message")),
                (String)getObjectFromJsonValue(jsonObject.get("exception")),
                (String)getObjectFromJsonValue(jsonObject.get("fullname")),
                response);
    }

    private Object getObjectFromJsonValue(JsonValue value){

        switch (value.getValueType()){

            case STRING:
                return ((JsonString) value).getString();

            case NUMBER:
                if (((JsonNumber) value).isIntegral()) {
                    return ((JsonNumber) value).longValue();     // or other methods to get integral value
                } else {
                    return ((JsonNumber) value).doubleValue();   // or other methods to get decimal number value
                }
            case TRUE:
                return true;
            case FALSE:
                return false;
            case NULL:
                return null;
            default:
                throw new RuntimeException("Type for " + value.getValueType() + " not found.");
        }
    }
}
