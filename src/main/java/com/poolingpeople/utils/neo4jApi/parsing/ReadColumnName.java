package com.poolingpeople.utils.neo4jApi.parsing;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import java.util.*;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadColumnName implements State{

    @Inject
    ReadRow next;

    @Override
    public State process(JsonParser parser,ResultContainer result) {
        JsonParser.Event event = parser.next();

        Iterator<Map<String, Map<String, Object>>> it = result.getResult().iterator();

        if(event == JsonParser.Event.VALUE_STRING){

            Map<String, Map<String, Object>> row;

            if(it.hasNext()) {
                row = it.next();
            }else{
                row = new LinkedHashMap<>();
            }

            row.put(parser.getString(), null);
            return this;

        }
        if(event == JsonParser.Event.END_ARRAY){
            return next;
        }
        if(event == JsonParser.Event.START_ARRAY){
            return this;
        }


        throw new RuntimeException("Unexpected event");
    }
}
