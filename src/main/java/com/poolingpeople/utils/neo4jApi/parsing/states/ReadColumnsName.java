package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
@ApplicationScoped
public class ReadColumnsName implements State {

    @Inject
    ReadStatementResult readStatementResult = new ReadStatementResult();

    @Override
    public NAMES process(JsonParser parser,ResultContainer result) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.VALUE_STRING){

            result.addColumnName(parser.getString());
            return this.getName();

        }
        if(event == JsonParser.Event.END_ARRAY){
            return readStatementResult.getName();
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_COLUM_NAME;
    }
}
