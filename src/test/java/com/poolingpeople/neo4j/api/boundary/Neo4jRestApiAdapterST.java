package com.poolingpeople.neo4j.api.boundary;

import com.poolingpeople.neo4j.api.control.ResponseStreamingParser;
import com.poolingpeople.neo4j.api.control.StatementBuilder;
import com.poolingpeople.neo4j.api.control.parsing.states.StatesManager;
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

    Neo4jClient cut;

    @Before
    public void setUp() throws Exception {
        cut = new Neo4jClient();
        cut.endpoint = new Endpoint();
        cut.helper = new StatementBuilder();
        StatesManager statesManager = new StatesManager();
        cut.responseParser = new ResponseStreamingParser(statesManager);
        cut.endpoint.statesManager = statesManager;

        cut.beginTransaction();
        cut.cypherSingleEntityQuery(new Statement("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r", new QueryParams()));


    }

    private String createQuery(){

        String query = "CREATE (n:a{props}) return count(n) as total";
        return query;
    }

    @Test
    public void testCreateQuery_exception(){
        String query = "CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total";
        QueryParams params = new QueryParams().add("start", 5).add("end", 6);
        try {

            List<Map<String, Object>> r = cut.cypherParamsQuery(new Statement(query, params));
            System.out.println(r);
        }catch (Neo4jClientErrorException e){
            assertThat("Expected a parameter named props", is(e.getMessage()));
        }

        cut.commitTransaction();

    }
//
//    @Test
//    public void testRunParametrizedCypherQuery() throws Exception {
//        String query = "CREATE (n:a{start:{start}, end:{end}}) return count(n) as total";
//        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//        assertEquals(r.size(), 1);
//        assertEquals(r.get(0).get("total"), 1L);
//
//        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
//        r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//        assertEquals(r.size(), 1);
//        assertTrue(r.get(0).keySet().contains("start"));
//        assertEquals(r.get(0).get("start"), 0L);
//        assertTrue(r.get(0).keySet().contains("end"));
//        assertEquals(r.get(0).get("end"), 1L);
//
//        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
//        r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam()
//                        .forId("id").add("start", 0).done("end", 1).getParams().get("id"));
//
//        assertEquals(r.size(), 1);
//        assertTrue(r.get(0).keySet().contains("start"));
//        assertEquals(r.get(0).get("start"), 0L);
//        assertTrue(r.get(0).keySet().contains("end"));
//        assertEquals(r.get(0).get("end"), 1L);
//    }
//
//    @Test
//    public void testRunCypherQuery() throws Exception {
//
//        String query = "CREATE (n:a{start:{start}, end:{end}}) return count(n) as total";
//        Collection<Map<String, Map<String, Object>>> r =  cut.runCypherQuery(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//        assertEquals(r.size(), 1);
//        assertEquals(r.iterator().next().get("total").get("total"), 1L);
//
//        query = "MATCH (n:a{start:{start}, end:{end}}) return n";
//        r =  cut.runCypherQuery(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//        assertEquals(r.size(), 1);
//        assertTrue(r.iterator().next().keySet().contains("n"));
//        assertEquals(r.iterator().next().get("n").get("start"), 0L);
//        assertTrue(r.iterator().next().get("n").keySet().contains("end"));
//        assertEquals(r.iterator().next().get("n").get("end"), 1L);
//
//        query = "MATCH (n:a{start:{start}, end:{end}}) return n";
//        r =  cut.runCypherQuery(query,
//                new QueryCollectionParam()
//                        .forId("id").add("start", 0).done("end", 1).getParams().get("id"));
//
//        assertEquals(r.size(), 1);
//        assertTrue(r.iterator().next().keySet().contains("n"));
//        assertEquals(r.iterator().next().get("n").get("start"), 0L);
//        assertTrue(r.iterator().next().get("n").keySet().contains("end"));
//        assertEquals(r.iterator().next().get("n").get("end"), 1L);
//
//    }
//
//    @Test
//    public void testGetParametrizedSingleResult() throws Exception {
//
//        String query = "CREATE (n:a{first}), (m:a{second}) " +
//                "return count(n) + count(m) as total";
//
//        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam()
//                        .forId("first").add("start", 0).done("end", 1)
//                        .forId("second").add("start",1).done("end",2));
//
//        assertEquals(1, r.size());
//        assertEquals(r.get(0).get("total"), 2L);
//
//        query = "MATCH (n:a{start:{start}, end:{end}}) return n.start as start, n.end as end";
//        Map<String, Object> sr =  cut.getParametrizedSingleResult(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//        assertTrue(sr.keySet().contains("start"));
//        assertEquals(sr.get("start"), 0L);
//        assertTrue(sr.keySet().contains("end"));
//        assertEquals(sr.get("end"), 1L);
//
//    }
//
//    @Test(expected = NotSingleResultException.class)
//    public void testGetParametrizedSingleResult_exception() throws Exception {
//
//        String query = "CREATE (n:a{first}), (m:a{second}) " +
//                "return count(n) + count(m) as total";
//
//        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam()
//                        .forId("first").add("start", 0).done("end", 1)
//                        .forId("second").add("start",1).done("end",2));
//
//        assertEquals(1, r.size());
//        assertEquals(r.get(0).get("total"), 2L);
//
//        query = "MATCH (n:a) return n";
//        Map<String, Object> sr =  cut.getParametrizedSingleResult(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//    }
//
//    @Test
//    public void testGetSingleResult() throws Exception {
//        String query = "CREATE (n:a{first}), (m:a{second}) " +
//                "return count(n) + count(m) as total";
//
//        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam()
//                        .forId("first").add("start", 0).done("end", 1)
//                        .forId("second").add("start",1).done("end",2));
//
//        assertEquals(1, r.size());
//        assertEquals(r.get(0).get("total"), 2L);
//
//        query = "MATCH (n:a{start:{start}, end:{end}}) return n";
//        Map<String, Map<String, Object>> sr =  cut.getSingleResult(query,
//                new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                        .forId("id").add("start", 0).done("end", 1));
//
//        assertTrue(sr.keySet().contains("n"));
//        assertEquals(sr.get("n").get("start"), 0L);
//        assertEquals(sr.get("n").get("end"), 1L);
//    }
//
//    @Test(expected = NotSingleResultException.class)
//    public void testGetSingleResult_exception() throws Exception {
//        String query = "CREATE (n:a{first}), (m:a{second}) " +
//                "return count(n) + count(m) as total";
//
//        List<Map<String, Object>> r =  cut.runParametrizedCypherQuery(query,
//                new QueryCollectionParam()
//                        .forId("first").add("start", 0).done("end", 1)
//                        .forId("second").add("start",1).done("end",2));
//
//        assertEquals(1, r.size());
//        assertEquals(r.get(0).get("total"), 2L);
//
//        query = "MATCH (n:a) return n";
//        Map<String, Map<String, Object>> sr =  cut.getSingleResult(query,
//                new QueryCollectionParam());
//    }
//
//    @Test
//    public void testSetEndpoint() throws Exception {
//
//        assertEquals(cut, cut.setEndpoint(new Endpoint()));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testSetEndpoint_exception() throws Exception {
//
//        assertEquals(cut, cut.setEndpoint(new Endpoint()));
//        cut.setEndpoint(null);
//    }

}