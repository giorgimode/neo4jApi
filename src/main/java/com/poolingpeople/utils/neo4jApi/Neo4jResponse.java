package com.poolingpeople.utils.neo4jApi;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.internal.ClientInvocation;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alacambra on 23.06.14.
 */
public abstract class Neo4jResponse<T extends Neo4jResponse, R> {

    List<Map<String, Object>> dataMap = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Response receivedResponse;
    String rawResponseBody;
    R responseBody;

    public List<Map<String, Object>> getReceivedDataAsMap() {
        return dataMap;
    }

    public boolean hasResponseItem() {
        return dataMap.size() > 0;
    }


    public Map<String, Object> getUniqueReceivedItemAsMap() {

        List<Map<String, Object>> map = dataMap;

        if (map.size() > 1) {
            throw new RuntimeException("not a unique item");
        }

        if (map.size() == 0) {
            throw new RuntimeException("No response");
        }

        return map.get(0);
    }

    public Response getReceivedResponse() {
        return receivedResponse;
    }

    public T load(Response response) {

        receivedResponse = response;
        rawResponseBody = response.readEntity(String.class);

        try {
            this.responseBody = mapResponse(rawResponseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadDataMap();

        return (T) this;
    }

    /**
     *
     * @param responseBody
     * @return
     * @throws java.io.IOException
     *
     * Use only if the returned JSON has an object as a root element, not a collection
     *
     */
    protected R mapResponse(String responseBody) throws IOException {
        R mappedResponse;
        try {
            mappedResponse = mapper.readValue(responseBody, getResponseBodyClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mappedResponse;
    };

    protected abstract void loadDataMap();

    public abstract Class<R> getResponseBodyClass();

    public String getRawResponseBody() {
        return rawResponseBody;
    }
}
