package com.poolingpeople.utils.neo4jApi;

//import com.poolingpeople.ApplicationException;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eduardo on 6/25/14.
 */
public class SchemaResponse extends Neo4jResponse<SchemaResponse, SchemaResponse.ResponseBody> {

    public SchemaResponse() {}

    @Override
    public Class<ResponseBody> getResponseBodyClass() {
        return ResponseBody.class;
    }

    @Override
    protected ResponseBody mapResponse(String responseBody) {
        ResponseBody r;
        try {
            r = mapper.readValue("{\"indexes\":" + responseBody + "}", getResponseBodyClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    protected static class ResponseBody {

        List<Index> indexes;

        public void setIndexes(List<Index> indexes) {
            this.indexes = indexes;
        }

        public List<Index> getIndexes() {
            return indexes;
        }

    }

    protected static class Index {

        List<String> properties;
        String label;
        /**
         * Constraint request will provide a field saying type: UNIQUE.
         * To match same structure with non-unique indexes, this custom
         * attribute will be set as default.
         */
        String type = "INDEX";


        @JsonProperty("property_keys")
        public List<String> getProperties() {
            return properties;
        }

        @JsonProperty("property_keys")
        public void setProperties(List<String> properties) {
            this.properties = properties;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    @Override
    protected void loadDataMap(){

        for(Index item : responseBody.getIndexes()){

            Map<String, Object> index = new HashMap<>();
            index.put("type", item.getType());
            index.put("label", item.getLabel());
            index.put("properties", item.getProperties());

            dataMap.add(index);

        }
    }
}
