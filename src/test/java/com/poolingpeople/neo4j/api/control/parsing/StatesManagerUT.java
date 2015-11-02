package com.poolingpeople.neo4j.api.control.parsing;

import com.poolingpeople.neo4j.api.control.parsing.states.StatesManager;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
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
    public void testParseMultipleStatements() throws Exception {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("multistatement-response.json");

        StatesManager statesManager = new StatesManager();

        List<StatementResult> statementResults =  statesManager.parse(stream).getStatementResults();

        assertThat(statementResults.size(), is(2));
        assertThat(statementResults.get(0).getResultMixed().size(), is(5));
        assertThat(statementResults.get(1).getResultMixed().size(), is(2));

        for(Map<String, Map<String, Object>> r : statementResults.get(1).getResultMixed()){

            assertTrue(r.get("n.email").get("n.email")
                    .equals("sebastian.baum@ion2s.com") || r.get("n.email").get("n.email")
                    .equals("arne.cornelius@ion2s.com"));

            assertTrue(r.get("state").get("state").equals("COMPLETED"));
        }

    }

    @Test
    public void testParseError() throws Exception {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("TxResponseError.json");

        StatesManager statesManager = new StatesManager();

        StatementsResultContainer statementsResultContainer = statesManager.parse(stream);

        StatementsResultContainer.Error result = statementsResultContainer.getError();

        assertThat(result.getCode(), is("Neo.ClientError.Statement.InvalidSyntax"));
        assertThat(result.getMessage(), is("Invalid input ' ': expected 'm/M' or 't/T' (line 1, column 11)\n\"match n re turn n limit 10\"\n           ^"));

    }
}