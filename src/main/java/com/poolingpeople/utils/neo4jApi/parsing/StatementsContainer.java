package com.poolingpeople.utils.neo4jApi.parsing;

import com.poolingpeople.utils.neo4jApi.boundary.Neo4jAdapterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by alacambra on 14.04.15.
 */
public class StatementsContainer {

    List<StatementResult> statementResults = new ArrayList<>();
    StatementResult current;
    Error error;

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
        } else if(statementResults.size() == 1){
            statementResults.get(0).setError(error);
        }

        return Optional.ofNullable(statementResults.get(0));
    }

    public Error startError(){
        error = new Error();
        return error;
    }

    public Error getError() {
        return error;
    }

    public class Error{
        String code = "";
        String message = "";

        public void addParam(String key, String value){

            if(key.equals("code")) code = value;

            else if(key.equals("message")) message = value;

            else{
                throw new RuntimeException("key " + key + " not valid");
            }
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return code + ":" + message;
        }
    }


}
