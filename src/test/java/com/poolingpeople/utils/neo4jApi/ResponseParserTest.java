package com.poolingpeople.utils.neo4jApi;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseParserTest {

    @Test
    public void testListResponse() throws Exception {
        ResponseParser responseParser = new ResponseParser();
        List<Map<String, Object>> result = responseParser.parseList(
                getClass().getClassLoader().getResourceAsStream("attributesJson.json"));

        assertEquals(25, result.size());
        result.get(0).get("n.uuid").equals("df7ca1d1-3baf-48c8-9b73-4e0552ad41e8");
        result.get(0).get("n.username").equals("U-349518368");
    }

    @Test
    public void test200Response() throws Exception {
        ResponseParser responseParser = new ResponseParser();

        Collection<Map<String,Map<String,Object>>> result =
                responseParser.parse(getClass().getClassLoader().getResourceAsStream("cypherResponse.json"));

        Map<String,Map<String,Object>> row = result.iterator().next();

        assertEquals(9, result.size());
        assertNotNull(row.get("c"));
        assertNotNull(row.get("n"));
        assertNotNull(row.get("c.uuid"));

    }

    @Test
    public void test400Response() throws Exception {
        ResponseParser responseParser = new ResponseParser();

        Response response = javax.ws.rs.core.Response
                .status(Response.Status.BAD_REQUEST)
                .entity(getClass().getClassLoader().getResourceAsStream("neo4jErrorResponse.json"))
                .build();

        Neo4jResponse neo4jResponse = mock(Neo4jResponse.class);
        when(neo4jResponse.getReceivedResponse()).thenReturn(response);

        when(neo4jResponse.getRawResponseBody()).
                thenReturn(IOUtils.toString((BufferedInputStream) response.getEntity()));

        try {
            Collection<Map<String, Map<String, Object>>> result = responseParser.parseOrException(neo4jResponse);
        }catch (Neo4jClientErrorException e){
            assertEquals(e.getMessage(), "Expected a parameter named collaborator");
            assertEquals(e.getExceptionFullName(), "org.neo4j.cypher.ParameterNotFoundException");
            assertEquals(e.getExceptionName(), "ParameterNotFoundException");
            return;
        }

        fail("exception not catched");
    }

    @Test
    @Ignore
    public void testErrorResponseOnServer() throws Exception {
        ResponseParser responseParser = new ResponseParser();
        Neo4jRestApiAdapter adapter = new Neo4jRestApiAdapterImpl();

        try{
            adapter.runParametrizedCypherQuery("match n retun n.uuid");
        }catch (Neo4jClientErrorException e){
            assertEquals(e.getMessage(), "Invalid input 'n': expected 'r/R' (line 1, column 13)\n\"match n retun n.uuid\"\n             ^");
            assertEquals(e.getExceptionFullName(), "org.neo4j.cypher.SyntaxException");
            assertEquals(e.getExceptionName(), "SyntaxException");
            return;
        }

        try{
            adapter.runParametrizedCypherQuery("match n retun n.uuid", null);
        }catch (Neo4jClientErrorException e){
            assertEquals(e.getMessage(), "Invalid input 'n': expected 'r/R' (line 1, column 13)\n\"match n retun n.uuid\"\n             ^");
            assertEquals(e.getExceptionFullName(), "org.neo4j.cypher.SyntaxException");
            assertEquals(e.getExceptionName(), "SyntaxException");
            return;
        }

        try{
            adapter.runCypherQuery("match n retun n.uuid", null);
        }catch (Neo4jClientErrorException e){
            assertEquals(e.getMessage(), "Invalid input 'n': expected 'r/R' (line 1, column 13)\n\"match n retun n.uuid\"\n             ^");
            assertEquals(e.getExceptionFullName(), "org.neo4j.cypher.SyntaxException");
            assertEquals(e.getExceptionName(), "SyntaxException");
            return;
        }
    }

    @Test
    public void test500Response() throws Exception {
        ResponseParser responseParser = new ResponseParser();

        Response response = javax.ws.rs.core.Response
                .serverError()
                .build();

        Neo4jResponse neo4jResponse = mock(Neo4jResponse.class);
        when(neo4jResponse.getReceivedResponse()).thenReturn(response);

        try {
            Collection<Map<String, Map<String, Object>>> result = responseParser.parseOrException(neo4jResponse);
        }catch (Neo4jException e){
            assertEquals(e.getMessage(), "Neo4j reported 5xx error");
            return;
        }

        fail("exception not catched");
    }
}