package com.poolingpeople.utils.neo4jApi.control.parsing.states;

import com.poolingpeople.utils.neo4jApi.control.parsing.State;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementsContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadColumnsName implements State {

    NAMES readStatementResult = NAMES.READ_STATEMENT_RESULT;

    @Override
    public NAMES process(JsonParser parser, StatementsContainer statementsContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.VALUE_STRING){

            statementsContainer.getCurrent().addColumnName(parser.getString());
            return this.getName();

        }
        if(event == JsonParser.Event.END_ARRAY){
            return readStatementResult;
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_COLUMN_NAME;
    }
}
