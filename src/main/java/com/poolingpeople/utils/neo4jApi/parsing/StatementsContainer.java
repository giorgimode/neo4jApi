package com.poolingpeople.utils.neo4jApi.parsing;

import com.poolingpeople.utils.neo4jApi.Neo4jAdapterException;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by alacambra on 14.04.15.
 */
public class StatementsContainer {

    List<StatementResult> statementResults = new ArrayList<>();

    StatementResult current;

    public StatementResult startStatement(){
        current = new StatementResult();
        return current;
    }

    public void endStatement(){
        statementResults.add(current);
    }

    public StatementResult getCurrent() {
        return current;
    }

    public List<StatementResult> getStatementResults() {
        return statementResults;
    }

    public Optional<StatementResult> getSingleStatement(){

        if(statementResults.size() > 1){
            throw new Neo4jAdapterException("More than one available statement result");
        }

        return Optional.ofNullable(statementResults.get(0));
    }
}
