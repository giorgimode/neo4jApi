package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alacambra on 19.02.15.
 */
public class CypherQueryProperties {
    Map<String, Map<String, Object>> properties = new HashMap<>();

    public class Subproperties {

        String id;

        public Subproperties(String id) {
            this.id = id;
        }

        Map<String, Object> props = new HashMap<>();

        public Subproperties add(String key, Object value){

            if(!properties.containsKey(id)){
                properties.put(id, new HashMap<>());
            }

            properties.get(id).put(key, value);
            return this;
        }

        public CypherQueryProperties done(String key, Object value){
            add(key, value);
            return done();
        }

        public CypherQueryProperties done(){
            return CypherQueryProperties.this;
        }
    }

    public Subproperties forId(String id){
         return new Subproperties(id);
    }

    public CypherQueryProperties add(String id, String key, Object value){
        if(!properties.containsKey(id)){
            properties.put(id, new HashMap<>());
        }

        properties.get(id).put(key, value);
        return this;
    }

    public CypherQueryProperties add(String id, Map<String, Object> props){

        if(properties.containsKey(id)){
            properties.get(id).putAll(props);
        } else {
            properties.put(id, props);
        }

        return this;
    }

    public Map<String, Map<String, Object>> getProperties() {
        return properties;
    }
}
