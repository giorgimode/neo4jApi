package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.*;

/**
 * Created by alacambra on 1/17/15.
 */
public class ResultContainerParametrized implements ResultContainer {
    @Override
    public void addColumnName(String columnName) {

    }

    @Override
    public Collection<Map<String, Map<String, Object>>> getResultMixed() {
        return null;
    }

    @Override
    public Map<String, Map<String, Object>> startNewRow() {
        return null;
    }

    @Override
    public Map<String, Object> startNewColumn() {
        return null;
    }

    @Override
    public void addColumnValue(String key, Object value) {

    }

    @Override
    public void addColumnValue(Object value) {

    }

    @Override
    public void columnValueRead() {

    }

    @Override
    public List<Map<String, Object>> getResultParametrized() {
        return null;
    }
//    List<String> columns = new ArrayList<>();
//
//    List<Map<String, Object>> result = new ArrayList<>();
//
//    Integer currentColumnIndex;
//    Map<String,Map<String,Object>> currentRow;
//    Map<String,Object> currentColumnValue;
//
//    @Override
//    public void addColumnName(String columnName){
//        columns.add(columnName);
//    }
//    @Override
//    public Collection<Map<String, Map<String, Object>>> getResultMixed() {
//        throw new RuntimeException("Mixed result not supported. Use Parametrized result");
//    }
//
//    @Override
//    public Map<String,Map<String,Object>> startNewRow(){
//        currentRow = new HashMap<>();
//        result.add(currentRow);
//        currentColumnIndex = 0;
//        return currentRow;
//    }
//
//    @Override
//    public Map<String,Object> startNewColumn(){
//        currentColumnValue = new HashMap<>();
//        currentRow.put(columns.get(currentColumnIndex), currentColumnValue);
//        return currentColumnValue;
//    }
//
//    @Override
//    public void addColumnValue(String key, Object value){
//        currentColumnValue.put(key, value);
//    }
//
//    @Override
//    public void addColumnValue(Object value){
//        currentColumnValue.put(columns.get(currentColumnIndex), value);
//    }
//
//    @Override
//    public void columnValueRead(){
//        currentColumnIndex++;
//    }
//
//    @Override
//    public List<Map<String, Object>> getResultParametrized() {
//        return resultParametrized;
//    }

}

