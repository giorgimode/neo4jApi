package com.poolingpeople.utils.neo4jApi.parsing;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class JsonValueReader {

    public Object getObjectFromJsonValue(JsonValue value) {

        switch (value.getValueType()) {

            case STRING:
                return ((JsonString) value).getString();

            case NUMBER:
                if (((JsonNumber) value).isIntegral()) {
                    return ((JsonNumber) value).longValue();     // or other methods to get integral value
                } else {
                    return ((JsonNumber) value).doubleValue();   // or other methods to get decimal number value
                }
            case TRUE:
                return true;
            case FALSE:
                return false;
            case NULL:
                return null;
            default:
                throw new RuntimeException("Type for " + value.getValueType() + " not found.");
        }
    }

    public Object getValueFromStream(JsonParser.Event event, JsonParser parser){
        if(event == JsonParser.Event.VALUE_STRING){
            return parser.getString();
        } else if(event == JsonParser.Event.VALUE_NUMBER){
            return parser.getLong();
        } else if(event == JsonParser.Event.VALUE_TRUE){
            return true;
        } else if(event == JsonParser.Event.VALUE_FALSE){
            return false;
        } else if(event == JsonParser.Event.VALUE_NULL){
            return null;
        }

        throw new InvalidJsonValueException("Type for " + event.name() + " not found.");

    }
}