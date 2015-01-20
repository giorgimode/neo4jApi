package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Neo4jRestApiAdapterImplTest {

    Neo4jRestApiAdapterImpl cud;

    @Before
    public void init(){
        cud = new Neo4jRestApiAdapterImpl();
        cud.responseParser = new ResponseStreamingParser();
        cud.helper = new RequestBodyBuilderHelper();
    }

    @Test
    public void testRunParametrizedCypherQuery() throws Exception {

        cud.runCypherQuery("match n return n", null);

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