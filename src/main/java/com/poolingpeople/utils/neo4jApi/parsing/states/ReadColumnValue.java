package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.JsonValueReader;
import com.poolingpeople.utils.neo4jApi.parsing.StatementResult;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadColumnValue implements State {

    NAMES readRow = NAMES.READ_ROW;
    NAMES readObjectValue = NAMES.READ_OBJECT_VALUE;

    @Inject
    JsonValueReader helper;

    public ReadColumnValue() {
    }

    public ReadColumnValue(JsonValueReader helper) {
        this.helper = helper;
    }

    @Override
    public NAMES process(JsonParser parser, StatementResult statementResult) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_OBJECT){
            statementResult.startNewColumn();
            return readObjectValue;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readRow;
        }

        Object value = helper.getValueFromStream(event, parser);
        statementResult.startNewColumn();
        statementResult.addColumnValue(value);
        statementResult.columnValueRead();
        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_COLUMN_VALUE;
    }
}
