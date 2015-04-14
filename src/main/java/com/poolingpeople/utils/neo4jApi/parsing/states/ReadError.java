package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.StatementResult;
import com.poolingpeople.utils.neo4jApi.parsing.State;
import com.poolingpeople.utils.neo4jApi.parsing.StatementsContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/18/15.
 */
public class ReadError implements State{

    NAMES readErrors= NAMES.READ_ERRORS;

    @Override
    public NAMES process(JsonParser parser, StatementsContainer statementsContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            return readErrors;
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            parser.next();
            statementsContainer.getError().addParam(key, parser.getString());
            return this.getName();
        }

        throw new RuntimeException("Unexpected event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_ERROR;
    }
}
