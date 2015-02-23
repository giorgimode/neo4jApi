package com.poolingpeople.utils.neo4jApi;

import com.poolingpeople.utils.neo4jApi.parsing.CypherQueryProperties;
import org.jboss.weld.environment.se.Weld;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class Neo4jRestApiAdapterST {

    Neo4jRestApiAdapter neo4jRestApiAdapter;

    @Before
    public void setUp() throws Exception {
        neo4jRestApiAdapter = new Weld().initialize().instance().select(Neo4jRestApiAdapter.class).get();
        neo4jRestApiAdapter.runParametrizedCypherQuery(createQuery());
    }

    private String createQuery(){

        String query = "CREATE (n:a{props}) return count(n) as total";
        return query;
    }

    @Test
    public void testCreateQuery(){

        Collection<Map<String, Map<String, Object>>> r = neo4jRestApiAdapter.runCypherQuery(createQuery(),
                new CypherQueryProperties().forId("props").add("start", 1).add("end", 2).done("period", 7)
        );

        assertThat(r.size(), is(1));
        assertThat(r.iterator().next().get("total").get("total"), is(1L));
    }

    @Test
    public void testCreateQuery_exception(){

        try {

            Collection<Map<String, Map<String, Object>>> r = neo4jRestApiAdapter.runCypherQuery(createQuery(),
                    new CypherQueryProperties().forId("falseProp").add("start", 1).add("end", 2).done("period", 7)
            );

        }catch (Neo4jClientErrorException e){
            assertThat("Expected a parameter named props", is(e.getMessage()));
        }


    }

    @Test
    public void testRunParametrizedCypherQuery() throws Exception {

    }

    @Test
    public void testRunParametrizedCypherQuery1() throws Exception {

    }

    @Test
    public void testRunCypherQuery() throws Exception {

    }

    @Test
    public void testSchemaIsCorrectlyLoaded() throws Exception {

    }

    @Test
    public void testGetConstraints() throws Exception {

    }

    @Test
    public void testGetIndexes() throws Exception {

    }

    @Test
    public void testCreateIndex() throws Exception {

    }

    @Test
    public void testDropIndex() throws Exception {

    }

    @Test
    public void testCreateConstraint() throws Exception {

    }

    @Test
    public void testDropConstraint() throws Exception {

    }
}