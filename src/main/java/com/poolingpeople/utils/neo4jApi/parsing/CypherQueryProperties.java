package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alacambra on 19.02.15.
 */
public class CypherQueryProperties {
    Map<String, Map<String, Object>> properties;

    public class Subproperies{

        String id;

        public Subproperies(String id) {
            this.id = id;
        }

        Map<String, Object> props = new HashMap<>();

        public Subproperies add(String key, Object value){

            if(properties.containsKey(id)){
                properties.get(id).put(key, value);
            } else {
                properties.put(id, new HashMap<>());
                properties.get(id).put(key, value);
            }

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

    public Subproperies forId(String id){
         return new Subproperies(id);
    }

    public CypherQueryProperties add(String id, String key, Object value){

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
}
