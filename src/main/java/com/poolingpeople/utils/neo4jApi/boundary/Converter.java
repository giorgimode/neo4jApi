package com.poolingpeople.utils.neo4jApi.boundary;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by alacambra on 04/05/15.
 */
public class Converter {

    public static List<Map<String, Map<String, Object>>> identity(List<Map<String, Map<String, Object>>> sts){
        return sts;
    }

    /**
     * Rows with maps representing the entity attributes. The entity is only a column.
     * <i>match (person) return person</i>
     * @return
     */
    public static List<Map<String, Object>> toOneColumn(List<Map<String, Map<String, Object>>> sts){
        return sts.stream().map(c -> {

            if(c.keySet().size() != 1) throw new InvalidParameterException(c.keySet().size() + " column found");
            return c.values().stream().findFirst().get();

        }).collect(Collectors.toList());
    }

    /**
     * rows representing columns where each column is a single property
     * <i>match (person) return person.uuid as uuid, person.name as name</i>
     * @return
     */
    public static List<Map<String, Object>> toParams(List<Map<String,Map<String,Object>>> sts){
        return sts.stream().map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    c.keySet().stream().forEach(k -> map.put(k, c.get(k).get(k)));
                    return map;
                }
        ).collect(Collectors.toList());
    }

    /**
     * Returns a map representing the attributes of an entity
     * @param sts
     * @return
     */
    public static Map<String, Object> toSingleEntity(List<Map<String,Map<String,Object>>> sts){
        return sts.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            if (c.size() > 1) throw new Neo4jException("More than one entity found");

            return c.values().stream().findFirst().get();
        }).findFirst().orElse(new HashMap<>());
    }

    /**
     * Only one row representing the values of the entity
     * <i>match (person:uuid{uuid:{uuid}}) return person.email as email</i>
     */
    public static Object toSingleProperty(List<Map<String, Map<String, Object>>> sts){

        Object obj = sts.stream().map(c -> {
            if(c.keySet().size() != 1){
                throw new InvalidParameterException(c.keySet().size() + " column found");
            }

            return c.values().stream().findFirst().get();
        }).map(entity -> {

            if(entity.size() > 1) throw new Neo4jException("More than one result returned");
            if(entity.values().size() > 1) throw new Neo4jException("More than one column found");
            return entity.values().iterator().next();

        }).findFirst().orElse(null);

        return obj;
    }

}
