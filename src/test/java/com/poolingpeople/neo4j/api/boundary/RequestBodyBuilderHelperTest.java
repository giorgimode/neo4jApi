package com.poolingpeople.neo4j.api.boundary;

import com.poolingpeople.neo4j.api.boundary.MultiStatementBuilder;
import com.poolingpeople.neo4j.api.boundary.QueryCollectionParam;
import com.poolingpeople.neo4j.api.control.StatementBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RequestBodyBuilderHelperTest {

    StatementBuilder cut;
    MultiStatementBuilder multiStatementBuilder;

    @Before
    public void setUp() throws Exception {

        cut = new StatementBuilder();
        multiStatementBuilder =  new MultiStatementBuilder();

    }

    @Test
    public void testGetCypherBody() throws Exception {

        String query = "CREATE (n:a{start:'123'}) return count(n) as total";
        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, new QueryCollectionParam())).build();
        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{start:'123'}) return count(n) as total\",\"parameters\":{}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithParams() throws Exception {
        String query = "CREATE (n:a{props}) return count(n) as total";
        QueryCollectionParam params = new QueryCollectionParam().add("props", "start", 5);
        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:a{props}) return count(n) as total\",\"parameters\":{\"props\":{\"start\":5}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithParamsContainingNulls() throws Exception {

        String query = "MATCH (n) SET n.start = {start}, n.end = {end} RETURN count(n) as total";
        QueryCollectionParam params = new QueryCollectionParam()
                .add("props", "start", 5)
                .add("props", "end", null);
        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();

        String expected = "{\"statements\":[{\"statement\":\"MATCH (n) SET n.start = {start}, n.end = {end} RETURN count(n) as total\",\"parameters\":{\"props\":{\"start\":5,\"end\":null}}}]}";
        assertThat(object.toString(), is(expected));
    }

    @Test
    public void testGetCypherBodyWithMultiParams_create_multiprop_collection_mode() throws Exception {

        String query = "CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total";
        QueryCollectionParam params = new QueryCollectionParam().add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
//        {"statements":[{"statement":"CREATE (n:d{start:{start}, end:{end}}) return count(n) as total","parameters":{"start":5, "end":6}}]}

    }

//    @Test
//    public void testGetCypherBodyWithMultiParams_create_multiprop_individual_mode() throws Exception {
//
//        String query = "CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total";
//        QueryCollectionParam params = new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                .add("propsA", "start", 5).add("propsA", "end", 5);
//
//        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();
//
//        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total\"," +
//                "\"parameters\":{\"start\":5,\"end\":5}" +
//                "}]}";
//
//        assertThat(object.toString(), is(expected));
//    }

//    @Test
//    public void testGetCypherBodyWithMultiParams_create_singleprop_individual_mode() throws Exception {
//
//        String query = "CREATE (n:c{start:{start}, end:{end}}) return count(n) as total";
//        QueryCollectionParam params = new QueryCollectionParam(QueryCollectionParam.Mode.INDIVIDUAL)
//                .add("propsA", "start", 5).add("propsA", "end", 5);
//
//        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();
//
//        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{start:{start}, end:{end}}) return count(n) as total\"," +
//                "\"parameters\":{\"start\":5,\"end\":5}" +
//                "}]}";
//
//        assertThat(object.toString(), is(expected));
//    }

    @Test
    public void testGetCypherBodyWithMultiParams_create() throws Exception {

        String query = "CREATE (n:c{propsA}), (m:c{propsB}) return count(n) as total";
        QueryCollectionParam params = new QueryCollectionParam().add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();

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
        QueryCollectionParam params = new QueryCollectionParam().add("propsA", "start", 5).add("propsB", "end", 5);
        JsonObject object = multiStatementBuilder.add(cut.getCypherBody(query, params)).build();

        String expected = "{\"statements\":[{\"statement\":\"match (n:c{start: {propsA}.start})-[:rel]->(m:c{end: {propsB}.end}) return n, m\"," +
                "\"parameters\":{" +
                "\"propsA\":{\"start\":5}," +
                "\"propsB\":{\"end\":5}" +
                "}}]}";

        assertThat(object.toString(), is(expected));
    }
}