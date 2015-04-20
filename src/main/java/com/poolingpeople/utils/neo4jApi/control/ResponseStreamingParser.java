package com.poolingpeople.utils.neo4jApi.control;

import com.poolingpeople.utils.neo4jApi.boundary.Neo4jClientErrorException;
import com.poolingpeople.utils.neo4jApi.boundary.Neo4jException;
import com.poolingpeople.utils.neo4jApi.boundary.Statement;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementResult;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementsResultContainer;
import com.poolingpeople.utils.neo4jApi.control.parsing.states.StatesManager;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

        StatementResult statementResult =
                statesManager
                        .parse(response.readEntity(String.class))
                        .getSingleStatement()
                        .orElse(new StatementResult());

        if(statementResult.getError() != null){
            throw parseException(statementResult.getError());
        } else {
            return statementResult.getResultMixed();
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
