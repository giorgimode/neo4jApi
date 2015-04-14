package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

import javax.json.Json;

import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MultiStatementBuilderTest {

    MultiStatementBuilder cut;

    @Before
    public void setUp() throws Exception {
        cut = MultiStatementBuilder.begin();
    }

    @Test
    public void testBegin() throws Exception {
        assertNotNull(cut);
    }

    @Test
    public void testAdd() throws Exception {
        cut.add(Json.createObjectBuilder().build());
        assertThat(cut.statementsList.size(), is(1));
    }

    @Test
    public void testBuild() throws Exception {
        StatementBuilder builder = new StatementBuilder();
        String query = "CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties().add("start", 5).add("end", 6);

        String expected = "{\"statements\":[{\"statement\":\"CREATE (n:c{start:{start}}), (m:c{end:{end}})" +
                " return count(n) as total\",\"parameters\":{\"start\":5,\"end\":6}}]}";


        assertThat(cut.add(builder.getCypherBody(query, params)).build().toString(), is(expected));

    }

    @Test
    public void testBuildMultipleStatements() throws Exception {
        StatementBuilder builder = new StatementBuilder();
        String query = "CREATE (n:c{start:{start}}), (m:c{end:{end}}) return count(n) as total";
        CypherQueryProperties params = new CypherQueryProperties();
        params.properties = new LinkedHashMap<>();
        params.properties.put(CypherQueryProperties.defaultKey, new LinkedHashMap<>());
        params.add("start", 5).add("end", 6);

        String query2 = "CREATE (n:c{start2:{start2}}), (m:c{end2:{end2}}) return count(n) as total";
        CypherQueryProperties params2 = new CypherQueryProperties();
        params2.properties = new LinkedHashMap<>();
        params2.properties.put(CypherQueryProperties.defaultKey, new LinkedHashMap<>());
        params2.add("start2", 7).add("end2", 8);

        String expected = "{\"statements\":[" +
                "{\"statement\":\"CREATE (n:c{start:{start}}), (m:c{end:{end}})" +
                " return count(n) as total\",\"parameters\":{\"start\":5,\"end\":6}}," +
                "{\"statement\":\"CREATE (n:c{start2:{start2}}), (m:c{end2:{end2}})" +
                " return count(n) as total\",\"parameters\":{\"start2\":7,\"end2\":8}}" +
                "]}";


        assertThat(cut
                .add(builder.getCypherBody(query, params))
                .add(builder.getCypherBody(query2, params2))
                .build().toString(), is(expected));

    }
}