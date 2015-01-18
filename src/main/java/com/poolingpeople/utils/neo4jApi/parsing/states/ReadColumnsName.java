package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadColumnsName implements State {

    @Inject
    ReadStatementResult readStatementResult = new ReadStatementResult();

    @Override
    public State process(JsonParser parser,ResultContainer result) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.VALUE_STRING){

            result.addColumnName(parser.getString());
            return this;

        }
        if(event == JsonParser.Event.END_ARRAY){
            return readStatementResult;
        }

        return this;
    }
}
