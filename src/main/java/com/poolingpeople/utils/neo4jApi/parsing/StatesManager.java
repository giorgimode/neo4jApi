package com.poolingpeople.utils.neo4jApi.parsing;

import com.poolingpeople.utils.neo4jApi.parsing.states.MainState;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Created by alacambra on 1/17/15.
 */
public class StatesManager {

    @Inject
    MainState mainState;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public ResultContainer parse(InputStream inputStream){

        JsonParser parser = Json.createParser(inputStream);
        State currentState = mainState;
        ResultContainer resultContainer = new ResultContainerMixed();

        while (currentState != null) {
            logger.finer("on state " + currentState.getClass().getSimpleName());
            currentState = currentState.process(parser, resultContainer);
        }

        return resultContainer;
    }

    public ResultContainer parse(String json) {
        return parse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }



}
