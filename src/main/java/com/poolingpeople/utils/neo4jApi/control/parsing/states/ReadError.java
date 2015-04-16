package com.poolingpeople.utils.neo4jApi.control.parsing.states;

import com.poolingpeople.utils.neo4jApi.control.parsing.State;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/18/15.
 */
public class ReadError implements State{

    NAMES readErrors= NAMES.READ_ERRORS;

    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            return readErrors;
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            parser.next();
            statementsResultContainer.getError().addParam(key, parser.getString());
            return this.getName();
        }

        throw new RuntimeException("Unexpected event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_ERROR;
    }
}
