package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by alacambra on 1/18/15.
 */
public interface ResultContainer {
    void addColumnName(String columnName);

    Collection<Map<String, Map<String, Object>>> getResultMixed();

    Map<String,Map<String,Object>> startNewRow();

    Map<String,Object> startNewColumn();

    void addColumnValue(String key, Object value);

    void addColumnValue(Object value);

    void columnValueRead();

    public List<Map<String, Object>> getResultParametrized();
}
