package com.poolingpeople.utils.neo4jApi.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alacambra on 19.02.15.
 */
public class CypherQueryProperties {

    Map<String, Map<String, Object>> properties = new HashMap<>();
    Mode mode = Mode.COLLECTION;

    public CypherQueryProperties(Mode mode) {
        this.mode = mode;
    }

    public CypherQueryProperties() {
    }

    public static enum Mode{
        COLLECTION,
        INDIVIDUAL
    }


    public class Subproperties {

        String id;

        public Subproperties(String id) {
            this.id = id;
        }

        Map<String, Object> props = new HashMap<>();

        public Subproperties add(String key, Object value){

            addId(id);

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

        addId(id);
        properties.get(id).put(key, value);
        return this;
    }

    public CypherQueryProperties add(String id, Map<String, Object> props){

        addId(id);
        properties.get(id).putAll(props);

        return this;
    }

    private void addId(String key){
        if(!properties.containsKey(key)){
            if(properties.size() > 0 && mode == Mode.INDIVIDUAL){
                throw new RuntimeException("Only one properties map allowed. Using individual mode");
            }
            properties.put(key, new HashMap<>());
        }

    }

    public Map<String, Map<String, Object>> getProperties() {
        return properties;
    }

    public CypherQueryProperties setMode(Mode mode) {
        this.mode = mode;
        return this;
    }
}
