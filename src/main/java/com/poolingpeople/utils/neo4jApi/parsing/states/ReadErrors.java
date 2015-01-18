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
public class ReadErrors implements State{

    @Inject
    ReadError readError = new ReadError();

    @Inject
    MainState mainState = new MainState();

    @Override
    public State process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_ARRAY){
             return readError;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return mainState;
        }

        throw new RuntimeException("Unexpceted event " + event);
    }
}
