package com.poolingpeople.utils.neo4jApi.parsing.states;

import com.poolingpeople.utils.neo4jApi.parsing.ResultContainer;
import com.poolingpeople.utils.neo4jApi.parsing.State;

import javax.json.stream.JsonParser;
import java.util.Set;

/**
 * Created by alacambra on 1/19/15.
 */
public class NoneState implements State {
    @Override
    public NAMES process(JsonParser parser, ResultContainer resultContainer) {
       throw new RuntimeException("nothing to process on state NONE");
    }

    @Override
    public NAMES getName() {
        return NAMES.NONE;
    }
}
