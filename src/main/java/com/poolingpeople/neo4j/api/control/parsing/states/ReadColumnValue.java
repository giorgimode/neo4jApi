package com.poolingpeople.neo4j.api.control.parsing.states;

import com.poolingpeople.neo4j.api.control.parsing.JsonValueReader;
import com.poolingpeople.neo4j.api.control.parsing.State;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;

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
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_OBJECT){
            statementsResultContainer.getCurrent().startNewColumn();
            return readObjectValue;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readRow;
        }

        Object value = helper.getValueFromStream(event, parser);
        statementsResultContainer.getCurrent().startNewColumn();
        statementsResultContainer.getCurrent().addColumnValue(value);
        statementsResultContainer.getCurrent().columnValueRead();
        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_COLUMN_VALUE;
    }
}
