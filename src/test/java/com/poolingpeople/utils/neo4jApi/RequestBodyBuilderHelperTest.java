package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RequestBodyBuilderHelperTest {

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
        CypherQueryProperties params = new CypherQueryProperties().setMode(CypherQueryProperties.Mode.COLLECTION).add("props", "start", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{props}) return count(n) as total\",\"parameters\":{\"props\":{\"start\":5}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithParamsContainingNulls() throws Exception {

        String query = "MATCH (n) SET n.start = {start}, n.end = {end} RETURN count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().setMode(CypherQueryProperties.Mode.COLLECTION)
                .add("props", "start", 5)
                .add("props", "end", null);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"MATCH (n) SET n.start = {start}, n.end = {end} RETURN count(n) as total\",\"parameters\":{\"props\":{\"start\":5,\"end\":null}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithMultiParams_create_multiprop_collection_mode() throws Exception {

        String query = "CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().setMode(CypherQueryProperties.Mode.COLLECTION).add("propsA", "start", 5).add("propsB", "end", 5);
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
    public void testGetCypherBodyWithMultiParams_create_multiprop_individual_mode() throws Exception {

        String query = "CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL)
                .add("propsA", "start", 5).add("propsA", "end", 5);

        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total\"," +
                "\"parameters\":{\"start\":5,\"end\":5}" +
                "}]}";

        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithMultiParams_create_singleprop_individual_mode() throws Exception {

        String query = "CREATE (n:c{start:{start}, end:{end}}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties(CypherQueryProperties.Mode.INDIVIDUAL)
                .add("propsA", "start", 5).add("propsA", "end", 5);

        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{start:{start}, end:{end}}) return count(n) as total\"," +
                "\"parameters\":{\"start\":5,\"end\":5}" +
                "}]}";

        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithMultiParams_create() throws Exception {

        String query = "CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().setMode(CypherQueryProperties.Mode.COLLECTION).add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithMultiParams_match(){

        String query = "match (n:c{start: {propsA}.start})-[:rel]->(m:c{end: {propsB}.end}) return n, m";
        CypherQueryProperties params = new CypherQueryProperties().setMode(CypherQueryProperties.Mode.COLLECTION).add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"match (n:c{start: {propsA}.start})-[:rel]->(m:c{end: {propsB}.end}) return n, m\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
    }
}