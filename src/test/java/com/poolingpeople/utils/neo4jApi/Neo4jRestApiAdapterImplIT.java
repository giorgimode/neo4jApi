package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Neo4jRestApiAdapterImplIT {

    Neo4jRestApiAdapterImpl cud;

    @Before
    public void init(){
        cud = new Neo4jRestApiAdapterImpl();
        cud.responseParser = new ResponseStreamingParser();
        cud.helper = new RequestBodyBuilderHelper();
    }

    @Test
    public void testRunParametrizedCypherQuery() throws Exception {

        List<Map<String, Object>> r = cud.runParametrizedCypherQuery("match n return n");
//        System.out.println(r);
        r = cud.runParametrizedCypherQuery("match n return n");
        cud.runParametrizedCypherQuery("match n return n");

    }

    @Test
    public void testRunParametrizedCypherQuery1() throws Exception {
        Long start = System.currentTimeMillis();

        int max = 500;

        for(int i = 0; i<max; i++) {
            List<Map<String, Object>> r = cud.runParametrizedCypherQuery("match n return n", null);
//            cud.runParametrizedCypherQuery("match n return n", null);
//            cud.runParametrizedCypherQuery("match n return n", null);
        }

        Long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    @Test
    public void testRunCypherQuery() throws Exception {
        Collection<Map<String, Map<String, Object>>> r =cud.runCypherQuery("match n return n", null);
        cud.runCypherQuery("match n return n", null);
        cud.runCypherQuery("match n return n", null);

        System.out.println(r);
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