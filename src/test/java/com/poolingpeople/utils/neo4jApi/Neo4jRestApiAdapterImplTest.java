/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolingpeople.utils.neo4jApi;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alacambra
 */
public class Neo4jRestApiAdapterImplTest {
    
    Neo4jRestApiAdapterImpl cut;
    
    public Neo4jRestApiAdapterImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        cut = new Neo4jRestApiAdapterImpl();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of runParametrizedCypherQuery method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testRunParametrizedCypherQuery_String_Map() {
        System.out.println("runParametrizedCypherQuery");
        String query = "";
        Map<String, Object> params = null;
        List<Map<String, Object>> expResult = null;
        List<Map<String, Object>> result = cut.runParametrizedCypherQuery(query, params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of runParametrizedCypherQuery method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testRunParametrizedCypherQuery_String() {
        System.out.println("runParametrizedCypherQuery");
        String query = "";
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        List<Map<String, Object>> expResult = null;
        List<Map<String, Object>> result = instance.runParametrizedCypherQuery(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of runCypherQuery method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testRunCypherQuery() {
        System.out.println("runCypherQuery");
        String query = "";
        Map<String, Object> params = null;
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        Collection<Map<String, Map<String, Object>>> expResult = null;
        Collection<Map<String, Map<String, Object>>> result = instance.runCypherQuery(query, params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of schemaIsCorrectlyLoaded method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testSchemaIsCorrectlyLoaded() {
        System.out.println("schemaIsCorrectlyLoaded");
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        boolean expResult = false;
        boolean result = instance.schemaIsCorrectlyLoaded();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstraints method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testGetConstraints() {
        System.out.println("getConstraints");
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        List<Map<String, Object>> expResult = null;
        List<Map<String, Object>> result = instance.getConstraints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndexes method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testGetIndexes() {
        System.out.println("getIndexes");
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        List<Map<String, Object>> expResult = null;
        List<Map<String, Object>> result = instance.getIndexes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createIndex method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testCreateIndex() {
        System.out.println("createIndex");
        String label = "";
        String property = "";
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        instance.createIndex(label, property);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dropIndex method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testDropIndex() {
        System.out.println("dropIndex");
        String label = "";
        String property = "";
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        instance.dropIndex(label, property);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createConstraint method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testCreateConstraint() {
        System.out.println("createConstraint");
        String label = "";
        String property = "";
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        instance.createConstraint(label, property);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dropConstraint method, of class Neo4jRestApiAdapterImpl.
     */
    @Test
    public void testDropConstraint() {
        System.out.println("dropConstraint");
        String label = "";
        String property = "";
        Neo4jRestApiAdapterImpl instance = new Neo4jRestApiAdapterImpl();
        instance.dropConstraint(label, property);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
