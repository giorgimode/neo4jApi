package com.poolingpeople.neo4j.api.control.parsing.states;

import com.poolingpeople.neo4j.api.control.parsing.State;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadRow implements State {

   NAMES  readColumnValue = NAMES.READ_COLUMN_VALUE;
   NAMES readData = NAMES.READ_DATA;


    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {
        JsonParser.Event event = parser.next();

        if (event == JsonParser.Event.START_ARRAY) {
            statementsResultContainer.getCurrent().startNewRow();
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
