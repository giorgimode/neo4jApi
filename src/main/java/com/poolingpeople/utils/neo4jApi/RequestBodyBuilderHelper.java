package com.poolingpeople.utils.neo4jApi;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alacambra on 1/17/15.
 */
public class RequestBodyBuilderHelper {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String statement = "statement";
    private static final String statements = "statements";
    private static final String parameters = "parameters";
    private static final String props = "props";


    /**
     * <code>
     *  {
     *      "statements" : [ {
     *          "statement" : "CREATE (n {props}) RETURN n",
     *          "parameters" : {
     *              "props" : {
     *                  "name" : "My Node"
     *               }
     *           }
     *       }]
     *   }</code>
     *
     * @param query
     * @param params
     * @return
     */
    public JsonObject getCypherBody(String query, Map<String, Object> params) {

        JsonObjectBuilder propsBuilder = Json.createObjectBuilder();

        if(params != null)
            for (Map.Entry<String, Object> param : params.entrySet()) {

                Object value = param.getValue();

                if(value instanceof String)
                    propsBuilder.add(param.getKey(), (String)param.getValue());

                if(value instanceof Integer)
                    propsBuilder.add(param.getKey(), (Integer)param.getValue());

                if(value instanceof Long)
                    propsBuilder.add(param.getKey(), (Long)param.getValue());
            }

        JsonObjectBuilder parametersBuilder = Json.createObjectBuilder()
                .add(props, propsBuilder);

        JsonObjectBuilder statementBuilder = Json.createObjectBuilder()
                .add(statement, query)
                .add(parameters, parametersBuilder);

        JsonArrayBuilder statementsBuilder = Json.createArrayBuilder().add(statementBuilder);
        JsonObjectBuilder bodyBuilder = Json.createObjectBuilder().add(statements, statementsBuilder);

        return bodyBuilder.build();
    }
}
