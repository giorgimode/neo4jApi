package com.poolingpeople.utils.neo4jApi.parsing;

import javax.json.stream.JsonParser;
import java.util.Collection;
import java.util.Map;

/**
 * Created by alacambra on 1/17/15.
 */
public interface State {

    /**
     * Proces the next event and add the result in the result object.
     * Collection are the different returned rows.
     * Map<String, Map<String, Object>> gives the column name with its values
     * Map<String, Object> is the value of a column. It can be a json object or just a value.
     * If it is a value the key is the column name
     * @param parser
     * @param resultContainer
     * @return
     */
    public State process(JsonParser parser ,ResultContainer resultContainer);



}
