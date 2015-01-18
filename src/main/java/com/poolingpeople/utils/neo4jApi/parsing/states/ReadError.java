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
    public State process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            return readErrors;
        }

        if(event == JsonParser.Event.KEY_NAME){
            return this;
        }


        throw new RuntimeException("Unexpected event " + event);
    }
}
