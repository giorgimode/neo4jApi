package com.poolingpeople.neo4j.api.control.parsing.states;

import com.poolingpeople.neo4j.api.control.parsing.State;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/18/15.
 */
public class ReadErrors implements State {

    NAMES readError = NAMES.READ_ERROR;
    NAMES  mainState = NAMES.MAIN_STATE;

    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_ARRAY){
             return getName();
        }

        if(event == JsonParser.Event.START_OBJECT){
            statementsResultContainer.startError();
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
