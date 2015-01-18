package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.*;

/**
 * Created by alacambra on 1/17/15.
 */
public class ResultContainerMixed implements ResultContainer {
    List<String> columns = new ArrayList<>();

    //all-rows/column-name/column-key-value
    Collection<Map<String,Map<String,Object>>> resultMixed = new HashSet<>();

    Integer currentColumnIndex;
    Map<String,Map<String,Object>> currentRow;
    Map<String,Object> currentColumnValue;

    @Override
    public void addColumnName(String columnName){
        columns.add(columnName);
    }

    @Override
    public Collection<Map<String, Map<String, Object>>> getResultMixed() {
        return resultMixed;
    }


    public List<Map<String, Object>> getResultParametrized() {
        throw new RuntimeException("Parametrized result not supported. Use Mixed result");
    }

    @Override
    public Map<String,Map<String,Object>> startNewRow(){
        currentRow = new HashMap<>();
        resultMixed.add(currentRow);
        currentColumnIndex = 0;
        return currentRow;
    }

    @Override
    public Map<String,Object> startNewColumn(){
        currentColumnValue = new HashMap<>();
        currentRow.put(columns.get(currentColumnIndex), currentColumnValue);
        return currentColumnValue;
    }

    @Override
    public void addColumnValue(String key, Object value){
        currentColumnValue.put(key, value);
    }

    @Override
    public void addColumnValue(Object value){
        currentColumnValue.put(columns.get(currentColumnIndex), value);
    }

    @Override
    public void columnValueRead(){
        currentColumnIndex++;
    }

}

