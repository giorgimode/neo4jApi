package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

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
        JsonObject object = cut.getCypherBody(query, null);
        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{start:'123'}) return count(n) as total\",\"parameters\":{\"props\":{}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithParams() throws Exception {

        String query = "CREATE (n:a{props}) return count(n) as total";
        Map<String, Object> params = new HashMap<>();
        params.put("start", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{props}) return count(n) as total\",\"parameters\":{\"props\":{\"start\":5}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    @Ignore
    public void testGetCypherBodyWithMultiParams() throws Exception {

        String query = "CREATE (n:c{propsA}), \"(m:c{propsB}) return count(n) as total";
        Map<String, Object> params = new HashMap<>();
        params.put("start", 5);
        JsonObject object = cut.getCypherBody(query, params);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{props}) return count(n) as total\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";
        assertThat(object.toString(), is(expected));
    }
}