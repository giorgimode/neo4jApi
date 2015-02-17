package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/18/15.
 */
@ApplicationScoped
public class ReadError implements State{

    @Inject
    ReadErrors readErrors;

    @Override
    public NAMES process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_OBJECT){
            resultContainer.startError();
            return getName();
        }

        if(event == JsonParser.Event.END_OBJECT){
            return readErrors.getName();
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            parser.next();
            resultContainer.getError().addParam(key, parser.getString());
            return this.getName();
        }

        throw new RuntimeException("Unexpected event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_ERROR;
    }
}
