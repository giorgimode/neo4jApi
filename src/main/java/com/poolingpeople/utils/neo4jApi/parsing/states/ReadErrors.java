package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.StatementResult;
import com.poolingpeople.utils.neo4jApi.parsing.State;
import com.poolingpeople.utils.neo4jApi.parsing.StatementsContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/18/15.
 */
public class ReadErrors implements State{

    NAMES readError = NAMES.READ_ERROR;
    NAMES  mainState = NAMES.MAIN_STATE;

    @Override
    public NAMES process(JsonParser parser, StatementsContainer statementsContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_ARRAY){
             return getName();
        }

        if(event == JsonParser.Event.START_OBJECT){
            statementsContainer.startError();
            return readError;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return mainState;
        }

        throw new RuntimeException("Unexpceted event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_ERRORS;
    }
}
