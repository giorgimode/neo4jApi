package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainerMixed;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadData implements State {

    @Inject
    ReadRow readRow;

    @Inject
    ReadStatementResult readStatementResult;

    @Override
    public State process(JsonParser parser, ResultContainerMixed result) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("row")){
            return readRow;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readStatementResult;
        }

        return this;
    }
}
