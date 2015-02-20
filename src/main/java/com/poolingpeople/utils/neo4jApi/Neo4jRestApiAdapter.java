package com.poolingpeople.utils.neo4jApi;

import com.poolingpeople.utils.neo4jApi.parsing.CypherQueryProperties;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by alacambra on 24.06.14.
 */
public interface Neo4jRestApiAdapter {

    public List<Map<String, Object>> runParametrizedCypherQuery(String query, CypherQueryProperties params);
    public List<Map<String, Object>> runParametrizedCypherQuery(String query, Map<String, Object> params);

    @Deprecated
    public List<Map<String, Object>> runParametrizedCypherQuery(String query);

    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, CypherQueryProperties params);
    public Collection<Map<String, Map<String, Object>>> runCypherQuery(String query, Map<String, Object> params);

    public boolean schemaIsCorrectlyLoaded();

    public List<Map<String, Object>> getConstraints();

    public List<Map<String, Object>> getIndexes();

    public void createIndex(String label, String property);

    public void dropIndex(String label, String property);

    public void createConstraint(String label, String property);

    public void dropConstraint(String label, String property);

}
