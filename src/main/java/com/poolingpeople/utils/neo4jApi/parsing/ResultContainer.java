package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.*;

/**
 * Created by alacambra on 1/17/15.
 */
public class ResultContainer {
    List<String> columns = new ArrayList<>();
    Collection<Map<String,Map<String,Object>>> result = new HashSet<>();

    String currentColumnName;
    Integer currentColumnIndex;
    Map<String,Map<String,Object>> currentRow;
    Map<String,Object> currentColumnValue;

    public List<String> getColumns() {
        return columns;
    }

    public Collection<Map<String, Map<String, Object>>> getResult() {
        return result;
    }

    public Map<String,Map<String,Object>> addRow(){
        currentRow = new HashMap<String,Map<String,Object>>();
        result.add(currentRow);
        return currentRow;
    }

    public Map<String,Object> addColumn(){
        currentColumnValue = currentRow.put(columns.get(currentColumnIndex), new HashMap<String, Object>());
        return currentColumnValue;
    }

    public void addColumnValue(String key, Object value){
        currentColumnValue.put(key, value);
    }
}
