package com.poolingpeople.utils.neo4jApi.boundary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alacambra on 19.02.15.
 */
public class QueryCollectionParam implements HasQueryParams{

    Map<String, Map<String, Object>> params = new HashMap<>();
    Mode mode = Mode.COLLECTION;
    public static final String defaultKey = "individual_default";

    public QueryCollectionParam(Mode mode) {
        this.mode = mode;
        if(mode == Mode.INDIVIDUAL){
            addId(defaultKey);
        }
    }

    public QueryCollectionParam() {
        this(Mode.INDIVIDUAL);
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

            params.get(id).put(key, value);
            return this;
        }

        public QueryCollectionParam done(String key, Object value){
            add(key, value);
            return done();
        }

        public QueryCollectionParam done(){
            return QueryCollectionParam.this;
        }
    }

    public Subproperties forId(String id){
        return new Subproperties(id);
    }

    public QueryCollectionParam add(String id, String key, Object value){

        addId(id);
        params.get(id).put(key, value);
        return this;
    }

    public QueryCollectionParam add(String id, Map<String, Object> props){

        addId(id);
        params.get(id).putAll(props);

        return this;
    }

    public QueryCollectionParam add(Map<String, Object> props){

        if(mode != Mode.INDIVIDUAL)
            throw new Neo4jException("This method is allowed only for INDIVIDUAL params");

        params.get(defaultKey).putAll(props);
        return this;
    }

    public QueryCollectionParam add(String key, Object value){

        if(mode != Mode.INDIVIDUAL)
            throw new Neo4jException("This method is allowed only for INDIVIDUAL params");

        params.get(defaultKey).put(key, value);
        return this;
    }

    private void addId(String key){
        if(!params.containsKey(key)){

            if(params.containsKey(defaultKey)){
                params.remove(defaultKey);
            }

            if(params.size() > 0 && mode == Mode.INDIVIDUAL){
                throw new RuntimeException("Only one params map allowed. Using individual mode");
            }
            params.put(key, new HashMap<>());
        }

    }

    public Map<String, Map<String, Object>> getParams() {
        return params;
    }

    public QueryCollectionParam setMode(Mode mode) {
        if(mode == null)
            throw new IllegalArgumentException("mode can not be null");

        this.mode = mode;
        return this;
    }

    public Mode getMode() {
        return mode;
    }
}
