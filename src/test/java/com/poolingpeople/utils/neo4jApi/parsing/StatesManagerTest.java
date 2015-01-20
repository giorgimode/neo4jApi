package com.poolingpeople.utils.neo4jApi.parsing;

import com.poolingpeople.utils.neo4jApi.parsing.states.StatesManager;
import org.jboss.weld.environment.se.Weld;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

public class StatesManagerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParse() throws Exception {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("TransactionalResponse.json");

        StatesManager statesManager = new Weld().initialize().instance().select(StatesManager.class).get();
        ResultContainer resultContainer = statesManager.parse(stream);

        Collection<Map<String, Map<String, Object>>> result = resultContainer.getResultMixed();

        assertEquals(5, result.size());

        for(Map<String, Map<String, Object>> row : result) {
            assertTrue(row.containsKey("n"));
            assertTrue(row.containsKey("n.uuid"));
            assertTrue(row.containsKey("n.familyName"));
            assertTrue(row.containsKey("p"));
            assertTrue(row.containsKey("n.email"));

        }


    }
}