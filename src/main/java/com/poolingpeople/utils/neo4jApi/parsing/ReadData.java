package com.poolingpeople.utils.neo4jApi.parsing;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import java.util.Collection;
import java.util.Map;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadData implements State {

    @Inject
    ReadRow next;

    @Override
    public State process(JsonParser parser, ResultContainer result) {

        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("row")){
            return next;
        }

        return this;
    }
}
