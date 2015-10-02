package com.poolingpeople.utils.neo4jApi.control.parsing.states;

import com.poolingpeople.utils.neo4jApi.control.parsing.State;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadData implements State {

    NAMES readRow = NAMES.READ_ROW;
    NAMES  readStatementResult = NAMES.READ_STATEMENT_RESULT;

    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("row")){
            return readRow;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readStatementResult;
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_DATA;
    }
}
