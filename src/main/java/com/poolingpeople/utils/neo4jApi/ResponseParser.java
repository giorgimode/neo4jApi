package com.poolingpeople.utils.neo4jApi;

import javax.json.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by alacambra on 05.11.14.
 */
public class ResponseParser {

    public List<Map<String,Object>> parseList(String json) {
        return parseList(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    public List<Map<String,Object>> parseList(InputStream inputStream) {
        Reader reader = new InputStreamReader(inputStream);

        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = jsonReader.readObject();

        JsonArray columnNames = (JsonArray) jsonObject.get("columns");
        JsonArray rows = (JsonArray) jsonObject.get("data");

        List<Map<String,Object>> result = new ArrayList<>();

        for(JsonValue rowValue : rows){

            JsonArray row = (JsonArray) rowValue;
            Map<String,Object> rowResult = new HashMap<>();

            for(int i = 0; i < columnNames.size(); i++){
                Map.Entry<String,Map<String,Object>> column =
                        getColumn(((JsonString) columnNames.get(i)).getString(), row.get(i));

                rowResult.put(column.getKey(), column.getValue());

            }
            result.add(rowResult);
        }

        return result;
    }

    public List<Map<String, Object>> parseSimpleListOrException(Neo4jResponse response){

        Response.Status.Family codeFamily =
                Response.Status.fromStatusCode(response.getReceivedResponse().getStatus()).getFamily();

        switch (codeFamily){
            case CLIENT_ERROR:
                throw parseException(response);
            case SERVER_ERROR:
                throw new Neo4jException(response.getReceivedResponse(), "Neo4j reported 5xx error");
            default:
                return parseList(response.getRawResponseBody());
        }
    }

    public Collection<Map<String,Map<String,Object>>> parseOrException(Neo4jResponse response){

        Response.Status.Family codeFamily =
                Response.Status.fromStatusCode(response.getReceivedResponse().getStatus()).getFamily();

        switch (codeFamily){
            case CLIENT_ERROR:
                throw parseException(response);
            case SERVER_ERROR:
                throw new Neo4jException(response.getReceivedResponse(), "Neo4j reported 5xx error");
            default:
                return parse(response.getRawResponseBody());
        }
    }

    public Collection<Map<String,Map<String,Object>>> parse(String json) {

        return parse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));

    }

    public List<Map<String, Object>> parseSchema(){
        return null;
    }

    public Collection<Map<String,Map<String,Object>>> parse(InputStream inputStream){

        Reader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = jsonReader.readObject();

        JsonArray columnNames = (JsonArray) jsonObject.get("columns");
        JsonArray rows = (JsonArray) jsonObject.get("data");

        Collection<Map<String,Map<String,Object>>> result = new HashSet<>();

        for(JsonValue rowValue : rows){
            result.add(getRow(columnNames, (JsonArray) rowValue));
        }

        return result;
    }

    private Map<String,Map<String,Object>> getRow(JsonArray columnNames, JsonArray row){

        Map<String,Map<String,Object>> rowResult = new HashMap<>();

        for(int i = 0; i < columnNames.size(); i++){
            Map.Entry<String,Map<String,Object>> column =
                    getColumn(((JsonString) columnNames.get(i)).getString(), row.get(i));

            rowResult.put(column.getKey(), column.getValue());
        }

        return rowResult;
    }

    private Map.Entry<String,Map<String,Object>> getColumn(String columnName, JsonValue columnValue){

        Map<String, Object> v = new HashMap<>();
        if (columnValue.getValueType() == JsonValue.ValueType.OBJECT) {
            for (Map.Entry<String, JsonValue> jsonValue :
                    ((JsonObject) ((JsonObject) columnValue).get("data")).entrySet()) {

                Object o =  getObjectFromJsonValue(jsonValue.getValue());
                v.put(jsonValue.getKey(), o);
            }
        } else if (columnValue.getValueType() != JsonValue.ValueType.ARRAY) {

            v.put(columnName, getObjectFromJsonValue(columnValue));
        }

        Map.Entry<String, Map<String,Object>> entry = new AbstractMap.SimpleEntry<>(columnName, v);
        return entry;
    }

    private Neo4jClientErrorException parseException(Neo4jResponse response){

        Reader reader = new InputStreamReader(
                new ByteArrayInputStream(response.getRawResponseBody().getBytes(StandardCharsets.UTF_8)));
        JsonObject jsonObject = Json.createReader(reader).readObject();

        return new Neo4jClientErrorException(
                (String)getObjectFromJsonValue(jsonObject.get("message")),
                (String)getObjectFromJsonValue(jsonObject.get("exception")),
                (String)getObjectFromJsonValue(jsonObject.get("fullname")),
                response.getReceivedResponse());
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
