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
public class ReadData implements State {

    @Inject
    ReadRow readRow;

    @Inject
    ReadStatementResult readStatementResult;

    @Override
    public NAMES process(JsonParser parser, ResultContainer result) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("row")){
            return readRow.getName();
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readStatementResult.getName();
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_DATA;
    }
}
