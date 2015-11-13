/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolingpeople.neo4j.api.boundary;

import com.poolingpeople.neo4j.api.control.parsing.states.StatesManager;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author alacambra
 */
public class Endpoint {

    @Inject
    StatesManager statesManager;

    String host = "localhost";
    int port = 7474;
    private String username;
     private String password;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public Endpoint() {
        if ("localhost".equals(host) && System.getenv("neo4j") != null) {
            host = System.getenv("neo4j");
            logger.log(Level.FINER, "envelope found. Using " + host + ":" + port);
        } else {
            logger.log(Level.FINER, "envelope NOT found. Using " + host + ":" + port);
        }
    }

    public Endpoint(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public String getEndpoint() {
        return "http://" + host + ":" + port;
    }

    public URI getCypherEndpoint(Neo4jClient.Transaction transactionType) {
        String neo4jURI = "http://" + host + ":" + port + "/db/data/transaction/";

        try {
            switch (transactionType) {
                case BEGIN:
                    return new URI(neo4jURI);
                case RUN:
                    return new URI(neo4jURI + statesManager.getTransactionID());
                default:
                    return new URI(neo4jURI + statesManager.getTransactionID() + "/commit");

            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public String getEncodedPassword() {
        String originalCredentials = getUsername() +":" + getPassword();
        System.out.println(originalCredentials);
        String base64encodedCredentials="";
        try {
            base64encodedCredentials = Base64.getEncoder().encodeToString(originalCredentials.getBytes("utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return base64encodedCredentials;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return getCypherEndpoint(Neo4jClient.Transaction.BEGIN).toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
