package com.poolingpeople.utils.neo4jApi.parsing;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alacambra on 14.04.15.
 */
public class StatementsContainer {

    List<StatementResult> statementResults = new ArrayList<>();

    StatementResult current;

    public StatementResult startStatement(){
        current = new StatementResult();
        return current;
    }

    public void endStatement(){
        statementResults.add(current);
    }

}
