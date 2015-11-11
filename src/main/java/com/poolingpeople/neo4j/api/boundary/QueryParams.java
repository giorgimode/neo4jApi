package com.poolingpeople.neo4j.api.boundary;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by alacambra on 19.02.15.
 */
public class QueryParams implements HasQueryParams{

    Logger logger = Logger.getLogger(getClass().getName());
    Map<String, Object> params = new HashMap<>();

    public QueryParams() {}

    public QueryParams add(String key, Object value){
        Object old = params.put(key, value);

        if(old != null)
            logger.finer("Replacing value:" + value + " for " + old);

        return this;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }
}
