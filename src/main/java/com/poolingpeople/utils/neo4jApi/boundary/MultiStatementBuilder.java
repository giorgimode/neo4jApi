package com.poolingpeople.utils.neo4jApi.boundary;

import com.poolingpeople.utils.neo4jApi.control.RequestBodyBuilderHelper;
import com.poolingpeople.utils.neo4jApi.control.StatementBuilder;
import com.poolingpeople.utils.neo4jApi.control.parsing.State;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by alacambra on 1/17/15.
 */
public class MultiStatementBuilder {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String statements = "statements";

    @Inject
    StatementBuilder helper = new StatementBuilder();

    Collection<JsonObject> statementsList = new ArrayList<>();

    private MultiStatementBuilder(){

    }

    public static MultiStatementBuilder begin(){
        return new MultiStatementBuilder();
    }

    public MultiStatementBuilder add(JsonObject statement){
        statementsList.add(statement);
        return this;
    }

    public MultiStatementBuilder add(String query,  HasQueryParams params){
        statementsList.add(helper.getCypherBody(query, params));
        return this;
    }

    public JsonObject build(){

        JsonArrayBuilder statementsBuilder = Json.createArrayBuilder();
        statementsList.stream().forEach(st -> statementsBuilder.add(st));

        return Json.createObjectBuilder().add(statements, statementsBuilder).build();
    }
}
