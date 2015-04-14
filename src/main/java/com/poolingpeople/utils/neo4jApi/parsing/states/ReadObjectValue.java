package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.JsonValueReader;
import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadObjectValue implements State {

    NAMES readColumnValue = NAMES.READ_COLUMN_VALUE;

    @Inject
    JsonValueReader helper;

    @Override
    public NAMES process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            resultContainer.columnValueRead();
            return readColumnValue;
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            JsonParser.Event ev = parser.next();
            resultContainer.addColumnValue(key, helper.getValueFromStream(ev, parser));
            return this.getName();
        }

        throw new RuntimeException("unsupported event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_OBJECT_VALUE;
    }
}
