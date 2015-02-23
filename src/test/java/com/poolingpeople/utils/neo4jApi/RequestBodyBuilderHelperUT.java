package com.poolingpeople.utils.neo4jApi;

import com.poolingpeople.utils.neo4jApi.parsing.CypherQueryProperties;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RequestBodyBuilderHelperUT {

    RequestBodyBuilderHelper cut;

    @Before
    public void setUp() throws Exception {

        cut = new RequestBodyBuilderHelper();

    }

    @Test
    public void testGetCypherBody() throws Exception {

        String query = "CREATE (n:a{start:'123'}) return count(n) as total";
        JsonObject object = cut.getCypherBody(query, new HashMap<String, Object>());
        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{start:'123'}) return count(n) as total\",\"parameters\":{}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithParams() throws Exception {

        String query = "CREATE (n:a{props}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().add("props", "start", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{props}) return count(n) as total\",\"parameters\":{\"props\":{\"start\":5}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithMultiParams_create_multiprop() throws Exception {

        String query = "CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
//        {"statements":[{"statement":"CREATE (n:d{start:{start}, end:{end}}) return count(n) as total","parameters":{"start":5, "end":6}}]}

    }

    @Test
    public void testGetCypherBodyWithMultiParams_create() throws Exception {

        String query = "CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
//        {"statements":[{"statement":"CREATE (n:d{start:{start}, end:{end}}) return count(n) as total","parameters":{"start":5, "end":6}}]}

    }

    @Test
    public void testGetCypherBodyWithMultiParams_match(){

        String query = "match (n:c{start: {propsA}.start})-[:rel]->(m:c{end: {propsB}.end}) return n, m";
        CypherQueryProperties params = new CypherQueryProperties().add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"match (n:c{start: {propsA}.start})-[:rel]->(m:c{end: {propsB}.end}) return n, m\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
    }
}