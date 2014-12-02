package com.poolingpeople;

import com.poolingpeople.utils.neo4jApi.SchemaRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by eduardo on 6/30/14.
 */
public class SchemaRequestTest {

    @Test
    public void testGetPath() throws Exception {


        SchemaRequest schemaRequest = new SchemaRequest("index");
        assertEquals("/db/data/schema/index", schemaRequest.getPath());

        SchemaRequest schemaRequest2 = new SchemaRequest("constraint");
        assertEquals("/db/data/schema/constraint", schemaRequest2.getPath());

    }


}
