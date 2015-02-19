/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolingpeople.utils.neo4jApi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alacambra
 */
public class Endpoint {

    String host = "localhost";
    int port = 7474;
    Logger logger = Logger.getLogger(this.getClass().getName());
    
    public String getEndpoint(){        
        return "http://" + host + ":" + port;
    }

    public Endpoint(){
        if("localhost".equals(host) && System.getenv("neo4j") != null) {
            host = System.getenv("neo4j");
            logger.log(Level.FINER, "envelope found. Using " + host + ":" + port);
        }else{
            logger.log(Level.FINER, "envelope NOT found. Using " + host + ":" + port);
        }
    }

    public Endpoint(String host, int port){
        this.port = port;
        this.host = host;
    }

    public URI getCypherEndpoint(){
        try {
            return new URI("http://" + host + ":" + port + "/db/data/transaction/commit");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return getCypherEndpoint().toString();
    }
}
