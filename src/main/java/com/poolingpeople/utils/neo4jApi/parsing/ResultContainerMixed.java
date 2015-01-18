package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.*;

/**
 * Created by alacambra on 1/17/15.
 */
public class ResultContainerMixed{
    List<String> columns = new ArrayList<>();

    //all-rows/column-name/column-key-value
    Collection<Map<String,Map<String,Object>>> result = new HashSet<>();

    Integer currentColumnIndex;
    Map<String,Map<String,Object>> currentRow;
    Map<String,Object> currentColumnValue;

    public void addColumnName(String columnName){
        columns.add(columnName);
    }
    public Collection<Map<String, Map<String, Object>>> getResult() {
        return result;
    }

    public Map<String,Map<String,Object>> startNewRow(){
        currentRow = new HashMap<>();
        result.add(currentRow);
        currentColumnIndex = 0;
        return currentRow;
    }

    public Map<String,Object> startNewColumn(){
        currentColumnValue = new HashMap<>();
        currentRow.put(columns.get(currentColumnIndex), currentColumnValue);
        return currentColumnValue;
    }

    public void addColumnValue(String key, Object value){
        currentColumnValue.put(key, value);
    }

    public void addColumnValue(Object value){
        currentColumnValue.put(columns.get(currentColumnIndex), value);
    }

    public void columnValueRead(){
        currentColumnIndex++;
    }

}

