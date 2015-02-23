package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadRow implements State {

   NAMES  readColumnValue = NAMES.READ_COLUM_VALUE;
   NAMES readData = NAMES.READ_DATA;


    @Override
    public NAMES process(JsonParser parser, ResultContainer resultContainer) {
        JsonParser.Event event = parser.next();

        if (event == JsonParser.Event.START_ARRAY) {
            resultContainer.startNewRow();
            return readColumnValue;
        }

        if (event == JsonParser.Event.END_ARRAY || event == JsonParser.Event.END_OBJECT) {
            return readData;
        }

        throw new RuntimeException("unsupported event " + event);
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_ROW;
    }
}
