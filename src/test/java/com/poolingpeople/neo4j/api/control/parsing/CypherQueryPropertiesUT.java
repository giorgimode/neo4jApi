package com.poolingpeople.neo4j.api.control.parsing;

import com.poolingpeople.neo4j.api.boundary.QueryCollectionParam;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CypherQueryPropertiesUT {

    QueryCollectionParam cut;

    @Before
    public void setUp(){
        cut = new QueryCollectionParam();
    }

    @Test
    public void testForId_add_collection_mode() throws Exception {

        cut.forId("test1").add("key", "value");
        cut.forId("test2").add("key", "value");
        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertNotNull(cut.getParams().get("test2"));
        assertThat(cut.getParams().get("test2").get("key"), is("value"));

    }

    @Test
    public void testForId_done_String_Object_collection_mode() throws Exception {

        QueryCollectionParam q1 = cut.forId("test1").done("key", "value");
        QueryCollectionParam q2 = cut.forId("test2").done("key", "value");

        assertThat(cut, is(q1));
        assertThat(cut, is(q2));

        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertNotNull(cut.getParams().get("test2"));
        assertThat(cut.getParams().get("test2").get("key"), is("value"));

    }

    @Test
    public void testForId_add_done_collection_mode() throws Exception {

        QueryCollectionParam q1 = cut.forId("test1").add("key", "value").done();
        QueryCollectionParam q2 = cut.forId("test2").add("key", "value").done();

        assertThat(cut, is(q1));
        assertThat(cut, is(q2));

        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertNotNull(cut.getParams().get("test2"));
        assertThat(cut.getParams().get("test2").get("key"), is("value"));

    }

    @Test
    public void testAdd_Sring_String_Object_collection_mode() throws Exception {

        cut.add("test1", "key", "value");
        cut.add("test2", "key", "value");
        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertNotNull(cut.getParams().get("test2"));
        assertThat(cut.getParams().get("test2").get("key"), is("value"));

    }

    @Test
    public void testAdd_String_map_collection_mode() throws Exception {

        Map<String, Object> props = new HashMap<>();
        props.put("key", "value");
        cut.add("test1", props);

        props = new HashMap<>();
        props.put("key", "value");
        cut.add("test2", props);

        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertNotNull(cut.getParams().get("test2"));
        assertThat(cut.getParams().get("test2").get("key"), is("value"));

    }

    @Test
    public void testForId_add_individual_mode() throws Exception {
        
        cut.forId("test1").add("key", "value");
        cut.forId("test1").add("key2", "value2");
        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertThat(cut.getParams().get("test1").get("key2"), is("value2"));

    }

    @Test
    public void testForId_done_String_Object_individual_mode() throws Exception {
        
        QueryCollectionParam q1 = cut.forId("test1").done("key", "value").forId("test1").done("key2", "value2");

        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertThat(cut.getParams().get("test1").get("key2"), is("value2"));

    }

    @Test
    public void testForId_add_done_individual_mode() throws Exception {
        
        QueryCollectionParam q1 = cut
                .forId("test1").add("key", "value").done()
                .forId("test1").add("key2", "value2").done();

        assertThat(cut, is(q1));
        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertThat(cut.getParams().get("test1").get("key2"), is("value2"));
    }

    @Test
    public void testAdd_Sring_String_Object_individual_mode() throws Exception {
        
        cut.add("test1", "key", "value").add("test1", "key2", "value2");
        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertThat(cut.getParams().get("test1").get("key2"), is("value2"));

    }

    @Test
    public void testAdd_String_map_individual_mode() throws Exception {
        
        Map<String, Object> props = new HashMap<>();
        props.put("key", "value");
        cut.add("test1", props);

        props = new HashMap<>();
        props.put("key2", "value2");
        cut.add("test1", props);

        assertNotNull(cut.getParams().get("test1"));
        assertThat(cut.getParams().get("test1").get("key"), is("value"));
        assertThat(cut.getParams().get("test1").get("key2"), is("value2"));

    }
}