package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.*;

/**
 * Created by alacambra on 1/17/15.
 */
public class ResultContainer {
    List<String> columns = new ArrayList<>();

    //all-rows/column-name/column-key-value
    Collection<Map<String,Map<String,Object>>> resultMixed = new HashSet<>();

    Integer currentColumnIndex;
    Map<String,Map<String,Object>> currentRow;
    Map<String,Object> currentColumnValue;
    Error error;


    public void addColumnName(String columnName){
        columns.add(columnName);
    }

    public Collection<Map<String, Map<String, Object>>> getResultMixed() {
        return resultMixed;
    }


    public List<Map<String, Object>> getResultParametrized() {
        throw new RuntimeException("Parametrized result not supported. Use Mixed result");
    }

    public Map<String,Map<String,Object>> startNewRow(){
        currentRow = new HashMap<>();
        resultMixed.add(currentRow);
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

    public Error startError(){
        error = new Error();
        return error;
    }

    public Error getError() {
        return error;
    }

    public class Error{
        String code = "";
        String message = "";

        public void addParam(String key, String value){

            if(key.equals("code")) code = value;

            else if(key.equals("message")) message = value;

            else{
                throw new RuntimeException("key " + key + " not valid");
            }
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return code + ":" + message;
        }
    }

}

