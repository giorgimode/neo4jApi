package com.poolingpeople.neo4j.api.control.parsing.states;

import com.poolingpeople.neo4j.api.control.parsing.State;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadColumnsName implements State {

    NAMES readStatementResult = NAMES.READ_STATEMENT_RESULT;

    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.VALUE_STRING){

            statementsResultContainer.getCurrent().addColumnName(parser.getString());
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
