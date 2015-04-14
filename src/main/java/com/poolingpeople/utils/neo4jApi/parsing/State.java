package com.poolingpeople.utils.neo4jApi.parsing;

import javax.json.stream.JsonParser;

/**
 * Created by alacambra on 1/17/15.
 */
public interface State {

    public static enum NAMES{
        MAIN_STATE,
        READ_COLUMN_NAME,
        READ_COLUMN_VALUE,
        READ_DATA,
        READ_ERROR,
        READ_ERRORS,
        READ_OBJECT_VALUE,
        READ_ROW,
        READ_STATEMENT_RESULT,
        NONE
    }

    /**
     * Proces the next event and add the resultMixed in the resultMixed object.
     * Collection are the different returned rows.
     * Map<String, Map<String, Object>> gives the column name with its values
     * Map<String, Object> is the value of a column. It can be a json object or just a value.
     * If it is a value the key is the column name
     * @param parser
     * @param statementsContainer
     * @return
     */
    public NAMES process(JsonParser parser ,StatementsContainer statementsContainer);
    public NAMES getName();



}
