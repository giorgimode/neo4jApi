package com.poolingpeople.utils.neo4jApi.parsing;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import java.util.Collection;
import java.util.Map;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadRow implements State {

    @Inject
    ReadData readData;

    @Inject
    ReadObjectValue readObjectValue;

    @Inject
    ReadPrimitiveValue readPrimitiveValue;


    @Override
    public State process(JsonParser parser, ResultContainer resultContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_ARRAY){
            resultContainer.addRow();
            return this;
        }

        if(event == JsonParser.Event.END_ARRAY){
            return readData;
        }

        if(event == JsonParser.Event.START_OBJECT){
            return readObjectValue;
        }

        return readPrimitiveValue;
    }
}
