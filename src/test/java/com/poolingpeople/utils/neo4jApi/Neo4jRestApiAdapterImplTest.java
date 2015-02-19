package com.poolingpeople.utils.neo4jApi;

import org.jboss.weld.environment.se.Weld;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Neo4jRestApiAdapterImplTest {

    Neo4jRestApiAdapter neo4jRestApiAdapter;

    @Before
    public void setUp() throws Exception {
        neo4jRestApiAdapter = new Weld().initialize().instance().select(Neo4jRestApiAdapterImpl.class).get();
        neo4jRestApiAdapter.runCypherQuery(createEvent(), null);
    }

    private String createEvent(){

        String query = "CREATE (n:a{start:'123'}) return count(n) as total";
        return query;
    }

    @Test
    public void testCreateQuery(){
        neo4jRestApiAdapter.runCypherQuery(createEvent(), null);
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