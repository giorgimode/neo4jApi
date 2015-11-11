package com.poolingpeople.neo4j.api.control;

import com.poolingpeople.neo4j.api.boundary.Neo4jClientErrorException;
import com.poolingpeople.neo4j.api.boundary.Neo4jException;
import com.poolingpeople.neo4j.api.control.parsing.StatementResult;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;
import com.poolingpeople.neo4j.api.control.parsing.states.StatesManager;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alacambra on 05.11.14.
 */
public class ResponseStreamingParser {

    @Inject
    StatesManager statesManager;

    public ResponseStreamingParser() {
    }

    public ResponseStreamingParser(StatesManager statesManager) {
        this.statesManager = statesManager;
    }

    public List<Map<String,Map<String,Object>>> parseSimpleStatementOrException(Response response){

        Response.Status.Family codeFamily = response.getStatusInfo().getFamily();

        if(codeFamily == Response.Status.Family.SERVER_ERROR){
            throw new Neo4jException(response, "Neo4j reported 5xx error");
        }

        StatementsResultContainer statementResult =
                statesManager
                        .parse(response.readEntity(String.class));

        if(statementResult.getError() != null){
            throw parseException(statementResult.getError());
        } else {
            return statementResult.getSingleStatement()
                    .orElse(new StatementResult()).getResultMixed();
        }
    }

    public List<List<Map<String,Map<String,Object>>>> parseMultiStatementOrException(Response response){

        StatementsResultContainer resultContainer = statesManager.parse(response.readEntity(String.class));

        if(resultContainer.getError() != null) {
            throw parseException(resultContainer.getError());
        }

        List<List<Map<String,Map<String,Object>>>> statements =
                resultContainer
                        .getStatementResults()
                        .stream()
                        .map(st -> st.getResultMixed()).collect(Collectors.toList());

        return statements;
    }

    private Neo4jClientErrorException parseException(StatementsResultContainer.Error error){
        return new Neo4jClientErrorException(error.getMessage(), error.getCode(), error.getCode());
    }
}
