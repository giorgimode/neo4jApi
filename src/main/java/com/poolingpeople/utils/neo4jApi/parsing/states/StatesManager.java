package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.JsonValueReader;
import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.inject.Inject;
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

    @Inject
    MainState mainState;
    Map<State.NAMES,State> states;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public StatesManager() {

        JsonValueReader helper = new JsonValueReader();
        states = new HashMap<>();

        mainState = new MainState();
        ReadColumnsName readColumnsName = new ReadColumnsName();
        ReadColumnValue readColumnValue = new ReadColumnValue();
        ReadData readData = new ReadData();
        ReadError readError = new ReadError();
        ReadErrors readErrors = new ReadErrors();
        ReadObjectValue readObjectValue = new ReadObjectValue();
        ReadRow readRow = new ReadRow();
        ReadStatementResult readStatementResult = new ReadStatementResult();


        mainState.readStatementResult = readStatementResult;
        mainState.readErrors = readErrors;

        readColumnsName.readStatementResult = readStatementResult;

        readColumnValue.readObjectValue = readObjectValue;
        readColumnValue.readRow = readRow;
        readColumnValue.helper = helper;

        readData.readRow = readRow;
        readData.readStatementResult = readStatementResult;

        readError.readErrors = readErrors;

        readErrors.readError = readError;

        readObjectValue.readColumnValue = readColumnValue;
        readObjectValue.helper = helper;

        readRow.readColumnValue = readColumnValue;
        readRow.readData = readData;

        readStatementResult.mainState = mainState;
        readStatementResult.readColumnName = readColumnsName;
        readStatementResult.readData = readData;


        states.put(State.NAMES.MAIN_STATE, mainState);
        states.put(State.NAMES.READ_COLUM_NAME, readColumnsName);
        states.put(State.NAMES.READ_COLUM_VALUE, readColumnValue);
        states.put(State.NAMES.READ_DATA, readData);
        states.put(State.NAMES.READ_ERROR, readError);
        states.put(State.NAMES.READ_ERRORS, readErrors);
        states.put(State.NAMES.READ_OBJECT_VALUE, readObjectValue);
        states.put(State.NAMES.READ_ROW, readRow);
        states.put(State.NAMES.READ_STATEMENT_RESULT, readStatementResult);
        states.put(State.NAMES.NONE, new NoneState());
    }

    public ResultContainer parse(InputStream inputStream){

        JsonParser parser = Json.createParser(inputStream);
        State currentState = mainState;
        ResultContainer resultContainer = new ResultContainer();

        State.NAMES lastState = null;

        while (currentState.getName() != State.NAMES.NONE) {
            logger.finer("on state " + currentState.getClass().getSimpleName());
            lastState = currentState.getName();
            currentState = states.get(currentState.process(parser, resultContainer));

            if(currentState == null){
                throw new RuntimeException("state not found" + lastState);
            }
        }

        return resultContainer;
    }

    public ResultContainer parse(String json) {
        return parse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }




}
