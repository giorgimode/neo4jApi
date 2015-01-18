package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainerMixed;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadStatementResult implements State {

    @Inject
    ReadColumnsName readColumnName;

    @Inject
    ReadData readData;

    @Inject
    MainState mainState;

    @Override
    public State process(JsonParser parser, ResultContainerMixed resultContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME){
            if(parser.getString().equals("columns")){
                return readColumnName;
            } else if(parser.getString().equals("data")){
                return readData;
            }
        } else if(event == JsonParser.Event.END_ARRAY){
            return mainState;
        }

        return this;
    }
}
