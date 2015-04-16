package com.poolingpeople.utils.neo4jApi.control;

import com.poolingpeople.utils.neo4jApi.boundary.HasQueryParams;
import com.poolingpeople.utils.neo4jApi.boundary.QueryCollectionParam;
import com.poolingpeople.utils.neo4jApi.boundary.QueryParams;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by alacambra on 1/17/15.
 */
public class RequestBodyBuilderHelper {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String statement = "statement";
    private static final String statements = "statements";
    private static final String parameters = "parameters";

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
     * @return
     */

    public JsonObject getCypherBody(String query, HasQueryParams properties) {
        return ( properties instanceof QueryCollectionParam) ?
                getCypherBodyCollectionParams(query, (QueryCollectionParam) properties)
                : getCypherBodyIndividualParams(query, (QueryParams) properties);
    }

    public JsonObject getCypherBodyCollectionParams(String query, QueryCollectionParam properties) {

        JsonObjectBuilder propertiesBuilder = Json.createObjectBuilder();

        if (properties != null) {
            for(Map.Entry<String, Map<String, Object>> props : properties.getParams().entrySet()) {

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
                .add(statement, query)
                .add(parameters, propertiesBuilder);

        JsonArrayBuilder statementsBuilder = Json.createArrayBuilder().add(statementBuilder);
        JsonObjectBuilder bodyBuilder = Json.createObjectBuilder().add(statements, statementsBuilder);

        return bodyBuilder.build();
    }

    public JsonObject getCypherBodyIndividualParams(String query, QueryParams queryParams) {

        Map<String, Object> params = queryParams.getParams();

        JsonObjectBuilder propsBuilder = Json.createObjectBuilder();

        if(params != null)
            for (Map.Entry<String, Object> param : params.entrySet()) {

                Object value = param.getValue();

                if (value == null)
                    propsBuilder.addNull(param.getKey());

                if(value instanceof String)
                    propsBuilder.add(param.getKey(), (String)param.getValue());

                if(value instanceof Integer)
                    propsBuilder.add(param.getKey(), (Integer)param.getValue());

                if(value instanceof Long)
                    propsBuilder.add(param.getKey(), (Long)param.getValue());
            }

        JsonObjectBuilder statementBuilder = Json.createObjectBuilder()
                .add(statement, query)
                .add(parameters, propsBuilder);

        JsonArrayBuilder statementsBuilder = Json.createArrayBuilder().add(statementBuilder);
        JsonObjectBuilder bodyBuilder = Json.createObjectBuilder().add(statements, statementsBuilder);

        return bodyBuilder.build();
    }

}
