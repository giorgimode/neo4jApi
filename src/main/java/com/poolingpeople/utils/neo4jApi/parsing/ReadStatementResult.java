package com.poolingpeople.utils.neo4jApi.parsing;

import com.sun.imageio.plugins.common.ReaderUtil;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadStatementResult implements State{

    @Inject
    ReadColumnName next;

    @Override
    public State process(JsonParser parser, ResultContainer resultContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME && parser.getString().equals("columns")){
            Map<String, Map<String, Object>> row = new HashMap<>();
            return next;
        }

        return this;
    }
}
