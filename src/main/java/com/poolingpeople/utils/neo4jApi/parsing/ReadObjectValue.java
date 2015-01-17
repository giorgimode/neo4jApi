package com.poolingpeople.utils.neo4jApi.parsing;

import javax.inject.Inject;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadObjectValue implements State{

    @Inject
    ReadRow readRow;

    @Inject
    JsonValueReader helper;

    @Override
    public State process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            return readRow;
        }

        if(event == JsonParser.Event.START_OBJECT){
            return this;
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            JsonParser.Event ev = parser.next();
            resultContainer.addColumnValue(key, helper.getValueFromStream(ev, parser));
            return this;
        }

        throw new RuntimeException("unsupported event");
    }
}
