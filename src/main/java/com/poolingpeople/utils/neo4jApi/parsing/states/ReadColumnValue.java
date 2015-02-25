package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.JsonValueReader;
import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.enterprise.context.ApplicationScoped;
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
    public NAMES process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_OBJECT){
            resultContainer.startNewColumn();
            return readObjectValue;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readRow;
        }

        Object value = helper.getValueFromStream(event, parser);
        resultContainer.startNewColumn();
        resultContainer.addColumnValue(value);
        resultContainer.columnValueRead();
        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_COLUM_VALUE;
    }
}
