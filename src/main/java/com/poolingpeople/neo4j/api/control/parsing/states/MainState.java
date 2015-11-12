package com.poolingpeople.neo4j.api.control.parsing.states;

import com.poolingpeople.neo4j.api.control.parsing.State;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/18/15.
 */
public class MainState implements State {

    NAMES readStatementResult = NAMES.READ_STATEMENT_RESULT;
    NAMES readErrors = NAMES.READ_ERRORS;

    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {

        JsonParser.Event event = parser.next();
        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("commit")){
            parser.next();

            statementsResultContainer.setCommitValue(parser.getString());
            return this.getName();
        }

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("results")){
            return readStatementResult;
        }

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("errors")){
            return readErrors;
        }

        if(event == JsonParser.Event.END_OBJECT){
            return NAMES.NONE;
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.MAIN_STATE;
    }
}
