package com.poolingpeople.utils.neo4jApi.boundary;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by alacambra on 15.04.15.
 */
public class Neo4jClient {

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<Map<String, Object>> cypherOneColumnQuery(String query, HasQueryParams params){
        return null;
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<Map<String, Map<String, Object>>> cypherMultipleEntityColumnsQuery(String query, HasQueryParams params){
        return null;
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public List<Map<String, Object>> cypherParamsQuery(String query, HasQueryParams params){

        return null;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public Map<String, Object> cypherSingleEntityQuery(String query, HasQueryParams params){
        return null;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public Object cypherSinglePropertyQuery(String query, HasQueryParams params){
        return null;
    }

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public List<Map<String, Object>> cypherOneColumnQuery(StatementContainer statements){
        return null;
    }

    /**
     * Rows with maps representing columns with maps with the entity attributes. Each column is a different entity.
     * <i>match (p:person)-[r:owns]->(target:uuid) return person, r, t</i>
     * @return
     */
    public List<Map<String, Map<String, Object>>> cypherMultipleEntityColumnsQuery(StatementContainer statements){
        return null;
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public List<Map<String, Object>> cypherParamsQuery(StatementContainer statements){

        return null;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person</i>
     */
    public Map<String, Object> cypherSingleEntityQuery(StatementContainer statements){
        return null;
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public Object cypherSinglePropertyQuery(StatementContainer statements){
        return null;
    }
}