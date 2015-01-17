package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestBodyBuilderHelperTest {

    RequestBodyBuilderHelper cud;

    @Before
    public void setup(){
        cud = new RequestBodyBuilderHelper();
    }

    @Test
    public void testGetCypherBody() throws Exception {
        String query = "CREATE (n {props}) RETURN n";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "My Node");
        String expected = "{\"statements\":[{\"statement\":\"" + query + "\"," +
                "\"parameters\":{\"props\":{\"name\":\"My Node\"}}}]}";

        assertEquals(expected, cud.getCypherBody(query, params).toString());
    }

    @Test
    public void ex(){

        Client client = ClientBuilder.newClient();
        String query = "MATCH n RETURN n, n.uuid LIMIT 25";
        Response response = client.target("http://localhost:7474/db/data/transaction/commit")
                .request()
                .header("Accept", "application/json; charset=UTF-8")
                .header("Content-Type","application/json")
                .post(Entity.json(cud.getCypherBody(query, null).toString()));

        System.out.println(response.readEntity(String.class));


    }
}