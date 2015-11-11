package com.poolingpeople.neo4j.api.boundary;

import com.poolingpeople.neo4j.api.control.StatementBuilder;

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

    public MultiStatementBuilder(){

    }

    public static MultiStatementBuilder begin(){
        return new MultiStatementBuilder();
    }

    public MultiStatementBuilder add(JsonObject statement){
        statementsList.add(statement);
        return this;
    }

    public MultiStatementBuilder add(Statement statement){
        statementsList.add(helper.getCypherBody(statement.getQuery(), statement.getParams()));
        return this;
    }

    public JsonObject build(){

        JsonArrayBuilder statementsBuilder = Json.createArrayBuilder();
        statementsList.stream().forEach(st -> statementsBuilder.add(st));

        return Json.createObjectBuilder().add(statements, statementsBuilder).build();
    }
}
