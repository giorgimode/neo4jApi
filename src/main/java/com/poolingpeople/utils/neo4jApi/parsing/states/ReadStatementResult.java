package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.State;
import com.poolingpeople.utils.neo4jApi.parsing.StatementsContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public class ReadStatementResult implements State {

    NAMES readColumnName = NAMES.READ_COLUMN_NAME;
    NAMES readData = NAMES.READ_DATA;
    NAMES mainState = NAMES.MAIN_STATE;

    @Override
    public NAMES process(JsonParser parser, StatementsContainer statementsContainer) {
        JsonParser.Event event = parser.next();

        if(event == JsonParser.Event.KEY_NAME){
            if(parser.getString().equals("columns")){
                return readColumnName;
            } else if(parser.getString().equals("data")){
                return readData;
            }
        } else if(event == JsonParser.Event.END_ARRAY){
            return mainState;
        } else if(event == JsonParser.Event.START_OBJECT){
            statementsContainer.startStatement();
        } else if(event == JsonParser.Event.END_OBJECT){
            statementsContainer.endStatement();
        }

        return this.getName();
    }

    @Override
    public NAMES getName() {
        return NAMES.READ_STATEMENT_RESULT;
    }
}
