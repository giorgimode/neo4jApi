package com.poolingpeople.utils.neo4jApi.parsing;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import java.util.Collection;
import java.util.Map;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadPrimitiveValue implements State{
    @Inject
    ReadRow readRow;

    @Inject
    ReadObjectValue readObjectValue;


    @Override
    public State process(JsonParser parser, ResultContainer resultContainer) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.START_OBJECT){
            resultContainer.
        }

        return this;
    }
}
