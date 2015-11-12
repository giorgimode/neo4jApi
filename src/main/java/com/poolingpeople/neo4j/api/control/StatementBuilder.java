package com.poolingpeople.neo4j.api.control;

import com.poolingpeople.neo4j.api.boundary.QueryCollectionParam;
import com.poolingpeople.neo4j.api.boundary.QueryParams;
import com.poolingpeople.neo4j.api.boundary.HasQueryParams;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by alacambra on 1/17/15.
 */
public class StatementBuilder {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String statement = "statement";
    private static final String statements = "statements";
    private static final String parameters = "parameters";

    /**
     * <code>
     * {
     * "statements" : [ {
     * "statement" : "CREATE (n {props}) RETURN n",
     * "parameters" : {
     * "props" : {
     * "name" : "My Node"
     * }
     * }
     * }]
     * }</code>
     *
     * @param query
     * @return
     */

    public JsonObject getCypherBody(String query, HasQueryParams properties) {
        return properties instanceof QueryCollectionParam ?
                getStatementCollectionParams(query, (QueryCollectionParam) properties) :
                getStatementIndividualParams(query, (QueryParams) properties);
    }

    public JsonObject getStatementCollectionParams(String query, QueryCollectionParam properties) {

        JsonObjectBuilder propertiesBuilder = Json.createObjectBuilder();

        if (properties != null) {
            for (Map.Entry<String, Map<String, Object>> props : properties.getParams().entrySet()) {

                JsonObjectBuilder propertyBuilder = Json.createObjectBuilder();
                String propsName = props.getKey();


                for (Map.Entry<String, Object> param : props.getValue().entrySet()) {

                    Object value = param.getValue();

                    if (value == null)
                        propertyBuilder.addNull(param.getKey());

                    if (value instanceof String)
                        propertyBuilder.add(param.getKey(), (String) param.getValue());

                    if (value instanceof Integer)
                        propertyBuilder.add(param.getKey(), (Integer) param.getValue());

                    if (value instanceof Long)
                        propertyBuilder.add(param.getKey(), (Long) param.getValue());
                }

                propertiesBuilder.add(propsName, propertyBuilder);
            }
        }

        JsonObjectBuilder statementBuilder = Json.createObjectBuilder()
                .add(statement, query.replace("CYPHER 2.0", "CYPHER 2.1"))
                .add(parameters, propertiesBuilder);

        return statementBuilder.build();
    }

    public JsonObject getStatementIndividualParams(String query, QueryParams properties) {


        JsonObjectBuilder propsBuilder = Json.createObjectBuilder();
        Map<String, Object> params = properties.getParams();

        if (params != null)
            for (Map.Entry<String, Object> param : params.entrySet()) {

                Object value = param.getValue();

                if (value == null)
                    propsBuilder.addNull(param.getKey());

                if (value instanceof String)
                    propsBuilder.add(param.getKey(), (String) param.getValue());

                if (value instanceof Integer)
                    propsBuilder.add(param.getKey(), (Integer) param.getValue());

                if (value instanceof Long)
                    propsBuilder.add(param.getKey(), (Long) param.getValue());
            }

        JsonObjectBuilder statementBuilder = Json.createObjectBuilder()
                .add(statement, query.replace("CYPHER 2.0", "CYPHER 2.1"))
                .add(parameters, propsBuilder);

        return statementBuilder.build();
    }
}