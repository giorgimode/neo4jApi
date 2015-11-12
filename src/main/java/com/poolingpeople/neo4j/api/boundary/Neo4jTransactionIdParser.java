package com.poolingpeople.neo4j.api.boundary;

/**
 * Created by oem on 10.11.15.
 */
public class Neo4jTransactionIdParser {

    public static String transactionResponseURL(String URL) {
      //  System.out.println("URL: " + URL);
        String id = URL.replaceAll(".*transaction/", "").replace("/commit", "");

        System.out.println("transactionURL: " + id);

       return id;

    }





}
