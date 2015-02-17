package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

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

//    @Test
//    public void ex(){
//
//        Client client = ClientBuilder.newClient();
//        String query = "MATCH n RETURN n, n.uuid, n.familyName, n as p, n.email LIMIT 5";
//        Response response = client.target("http://localhost:7474/db/data/transaction/commit")
//                .request()
//                .header("Accept", "application/json; charset=UTF-8")
//                .header("Content-Type","application/json")
//                .post(Entity.json(cud.getCypherBody(query, null).toString()));
//
//        System.out.println(response.readEntity(String.class));


//    }
}