package com.poolingpeople.utils.neo4jApi.parsing;

import com.poolingpeople.utils.neo4jApi.parsing.states.StatesManager;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StatesManagerUT {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParse() throws Exception {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("TransactionalResponse.json");

        StatesManager statesManager = new StatesManager();
        StatementResult statementResult = statesManager
                .parse(stream)
                .getSingleStatement()
                .orElse(new StatementResult());

        Collection<Map<String, Map<String, Object>>> result = statementResult.getResultMixed();

        assertEquals(5, result.size());

        for(Map<String, Map<String, Object>> row : result) {
            assertTrue(row.containsKey("n"));
            assertTrue(row.containsKey("n.uuid"));
            assertTrue(row.containsKey("n.familyName"));
            assertTrue(row.containsKey("p"));
            assertTrue(row.containsKey("n.email"));
        }
    }

    @Test
    public void testParseError() throws Exception {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("TxResponseError.json");

        StatesManager statesManager = new StatesManager();
        StatementResult statementResult = statesManager
                .parse(stream)
                .getSingleStatement()
                .orElse(new StatementResult());


        StatementResult.Error result = statementResult.getError();

        assertThat(result.getCode(), is("Neo.ClientError.Statement.InvalidSyntax"));
        assertThat(result.getMessage(), is("Invalid input ' ': expected 'm/M' or 't/T' (line 1, column 11)\n\"match n re turn n limit 10\"\n           ^"));

    }
}