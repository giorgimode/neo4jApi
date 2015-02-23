package com.poolingpeople.utils.neo4jApi;

import com.poolingpeople.utils.neo4jApi.parsing.CypherQueryProperties;
import org.jboss.weld.environment.se.Weld;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class Neo4jRestApiAdapterST {

    Neo4jRestApiAdapter cut;

    @Before
    public void setUp() throws Exception {
        cut = new Weld().initialize().instance().select(Neo4jRestApiAdapter.class).get();
        cut.runCypherQuery("MATCH (n)\n" +
                "OPTIONAL MATCH (n)-[r]-()\n" +
                "DELETE n,r", new CypherQueryProperties());
    }

    private String createQuery(){

        String query = "CREATE (n:a{props}) return count(n) as total";
        return query;
    }

    @Test
    public void testCreateQuery(){

        Collection<Map<String, Map<String, Object>>> r = cut.runCypherQuery(createQuery(),
                new CypherQueryProperties().forId("props").add("start", 1).add("end", 2).done("period", 7)
        );

        assertThat(r.size(), is(1));
        assertThat(r.iterator().next().get("total").get("total"), is(1L));
    }

    @Test
    public void testCreateQuery_exception(){

        try {

            Collection<Map<String, Map<String, Object>>> r = cut.runCypherQuery(createQuery(),
                    new CypherQueryProperties().forId("falseProp").add("start", 1).add("end", 2).done("period", 7)
            );

        }catch (Neo4jClientErrorException e){
            assertThat("Expected a parameter named props", is(e.getMessage()));
        }


    }

    @Test
    public void testRunParametrizedCypherQuery() throws Exception {
        String query = "CREATE (n:a{start:{start}, end:{end}}) return count(n) as total";
        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
                new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL)
                        .forId("id").add("start", 0).done("end", 1));

        assertEquals(r.size(), 1);
        assertEquals(r.get(0).get("total"), 1L);

        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
        r =  cut.runParametrizedCypherQuery(query,
                new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL)
                        .forId("id").add("start", 0).done("end", 1));

        assertEquals(r.size(), 1);
        assertTrue(r.get(0).keySet().contains("start"));
        assertEquals(r.get(0).get("start"), 0L);
        assertTrue(r.get(0).keySet().contains("end"));
        assertEquals(r.get(0).get("end"), 1L);

        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
        r =  cut.runParametrizedCypherQuery(query,
                new CypherQueryProperties()
                        .forId("id").add("start", 0).done("end", 1).getProperties().get("id"));

        assertEquals(r.size(), 1);
        assertTrue(r.get(0).keySet().contains("start"));
        assertEquals(r.get(0).get("start"), 0L);
        assertTrue(r.get(0).keySet().contains("end"));
        assertEquals(r.get(0).get("end"), 1L);
    }

    @Test
    public void testRunCypherQuery() throws Exception {
        String query = "CREATE (n:a{start:{start}, end:{end}}) return count(n) as total";
        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
                new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL)
                        .forId("id").add("start", 0).done("end", 1));

        assertEquals(r.size(), 1);
        assertEquals(r.get(0).get("total"), 1L);

        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
        r =  cut.runParametrizedCypherQuery(query,
                new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL)
                        .forId("id").add("start", 0).done("end", 1));

        assertEquals(r.size(), 1);
        assertTrue(r.get(0).keySet().contains("start"));
        assertEquals(r.get(0).get("start"), 0L);
        assertTrue(r.get(0).keySet().contains("end"));
        assertEquals(r.get(0).get("end"), 1L);

        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
        r =  cut.runParametrizedCypherQuery(query,
                new CypherQueryProperties()
                        .forId("id").add("start", 0).done("end", 1).getProperties().get("id"));

        assertEquals(r.size(), 1);
        assertTrue(r.get(0).keySet().contains("start"));
        assertEquals(r.get(0).get("start"), 0L);
        assertTrue(r.get(0).keySet().contains("end"));
        assertEquals(r.get(0).get("end"), 1L);
    }

}