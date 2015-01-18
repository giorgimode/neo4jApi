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

    @Inject
    ReadColumnValue readValue;

    @Inject
    JsonValueReader helper;

    @Override
    public State process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.END_OBJECT){
            resultContainer.columnValueRead();
            return readValue;
        }

        if(event == JsonParser.Event.KEY_NAME){
            String key = parser.getString();
            JsonParser.Event ev = parser.next();
            resultContainer.addColumnValue(key, helper.getValueFromStream(ev, parser));
            return this;
        }

        throw new RuntimeException("unsupported event " + event);
    }
}
