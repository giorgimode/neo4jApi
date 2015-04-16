package com.poolingpeople.utils.neo4jApi.control.parsing.states;

import com.poolingpeople.utils.neo4jApi.control.parsing.JsonValueReader;
import com.poolingpeople.utils.neo4jApi.control.parsing.State;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementsContainer;

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
    public NAMES process(JsonParser parser, StatementsContainer statementsContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            statementsContainer.getCurrent().columnValueRead();
            return readColumnValue;
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            JsonParser.Event ev = parser.next();
            statementsContainer.getCurrent().addColumnValue(key, helper.getValueFromStream(ev, parser));
            return this.getName();
        }

        throw new RuntimeException("unsupported event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_OBJECT_VALUE;
    }
}
