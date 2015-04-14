package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.StatementResult;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadColumnsName implements State {

    NAMES readStatementResult = NAMES.READ_STATEMENT_RESULT;

    @Override
    public NAMES process(JsonParser parser,StatementResult result) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.VALUE_STRING){

            result.addColumnName(parser.getString());
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
