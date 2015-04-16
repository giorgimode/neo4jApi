package com.poolingpeople.utils.neo4jApi.control.parsing.states;

import com.poolingpeople.utils.neo4jApi.control.parsing.JsonValueReader;
import com.poolingpeople.utils.neo4jApi.control.parsing.State;
import com.poolingpeople.utils.neo4jApi.control.parsing.StatementsResultContainer;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by alacambra on 1/17/15.
 */
public class StatesManager {

    Map<State.NAMES,State> states;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public StatesManager() {

        JsonValueReader helper = new JsonValueReader();
        states = new HashMap<>();

        MainState mainState = new MainState();
        ReadColumnsName readColumnsName = new ReadColumnsName();
        ReadColumnValue readColumnValue = new ReadColumnValue();
        ReadData readData = new ReadData();
        ReadError readError = new ReadError();
        ReadErrors readErrors = new ReadErrors();
        ReadObjectValue readObjectValue = new ReadObjectValue();
        ReadRow readRow = new ReadRow();
        ReadStatementResult readStatementResult = new ReadStatementResult();

        readColumnValue.helper = helper;
        readObjectValue.helper = helper;

        states.put(State.NAMES.MAIN_STATE, mainState);
        states.put(State.NAMES.READ_COLUMN_NAME, readColumnsName);
        states.put(State.NAMES.READ_COLUMN_VALUE, readColumnValue);
        states.put(State.NAMES.READ_DATA, readData);
        states.put(State.NAMES.READ_ERROR, readError);
        states.put(State.NAMES.READ_ERRORS, readErrors);
        states.put(State.NAMES.READ_OBJECT_VALUE, readObjectValue);
        states.put(State.NAMES.READ_ROW, readRow);
        states.put(State.NAMES.READ_STATEMENT_RESULT, readStatementResult);
        states.put(State.NAMES.NONE, new NoneState());
    }

    public StatementsResultContainer parse(InputStream inputStream){

        JsonParser parser = Json.createParser(inputStream);
        State currentState = states.get(State.NAMES.MAIN_STATE);
        StatementsResultContainer statementContainer = new StatementsResultContainer();

        State.NAMES lastState = null;

        while (currentState.getName() != State.NAMES.NONE) {

            logger.finer("on state " + currentState.getClass().getSimpleName());
            lastState = currentState.getName();
            currentState = states.get(currentState.process(parser, statementContainer));

            if(currentState == null){
                throw new RuntimeException("state not found" + lastState);
            }
        }

        return statementContainer;
    }

    public StatementsResultContainer parse(String json) {
        return parse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }




}
