package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
@ApplicationScoped
public class ReadStatementResult implements State {

    @Inject
    ReadColumnsName readColumnName;

    @Inject
    ReadData readData;

    @Inject
    MainState mainState;

    @Override
    public NAMES process(JsonParser parser, ResultContainer resultContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME){
            if(parser.getString().equals("columns")){
                return readColumnName.getName();
            } else if(parser.getString().equals("data")){
                return readData.getName();
            }
        } else if(event == JsonParser.Event.END_ARRAY){
            return mainState.getName();
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_STATEMENT_RESULT;
    }
}
