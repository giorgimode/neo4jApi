package com.poolingpeople.neo4j.api.control.parsing.states;

import com.poolingpeople.neo4j.api.control.parsing.State;
import com.poolingpeople.neo4j.api.control.parsing.StatementsResultContainer;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/19/15.
 */
public class NoneState implements State {
    @Override
    public NAMES process(JsonParser parser, StatementsResultContainer statementsResultContainer) {
       throw new RuntimeException("nothing to process on state NONE");
    }

    @Override
    public NAMES getName() {
        return NAMES.NONE;
    }
}
