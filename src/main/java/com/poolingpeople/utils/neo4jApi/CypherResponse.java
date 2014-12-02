package com.poolingpeople.utils.neo4jApi;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CypherResponse extends Neo4jResponse<CypherResponse, CypherResponse.ResponseBody>{

    public CypherResponse() {}

    @Override
    public Class<ResponseBody> getResponseBodyClass() {
        return ResponseBody.class;
    }

    protected static class ResponseBody {

        List<String> columns;
        List<List<Object>> data;

        public List<String> getColumns() {
            return columns;
        }

        public List<List<Object>> getData() {
            return data;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public void setData(List<List<Object>> data) {
            this.data = data;
        }
    }

    @Override
    public CypherResponse load(Response response) {

        receivedResponse = response;
        rawResponseBody = response.readEntity(String.class);

        return this;
    }


    @Override
    protected void loadDataMap(){

        for(List<Object> item:responseBody.getData()){

            Map<String, Object> node = new HashMap<>();

            for(String columnName:responseBody.getColumns()){
                node.put(columnName, item.get(responseBody.getColumns().indexOf(columnName)));
            }

            dataMap.add(node);
        }
    }
}
