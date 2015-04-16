package com.poolingpeople.utils.neo4jApi.boundary;

import java.util.ArrayList;
import java.util.List;

public class StatementContainer {

    List<Statement> statements;

    public StatementContainer() {
        this.statements = new ArrayList<>();
    }

    public StatementContainer add(Statement statement){
        statements.add(statement);
        return this;
    }


}
