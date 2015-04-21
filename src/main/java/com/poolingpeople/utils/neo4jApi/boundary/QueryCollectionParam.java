package com.poolingpeople.utils.neo4jApi.boundary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alacambra on 19.02.15.
 */
public class QueryCollectionParam implements HasQueryParams{

    Map<String, Map<String, Object>> params = new HashMap<>();

    public QueryCollectionParam() {

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

    private void addId(String key){
        if(!params.containsKey(key)){
            params.put(key, new HashMap<>());
        }

    }

    public Map<String, Map<String, Object>> getParams() {
        return params;
    }
}
