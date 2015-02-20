package com.poolingpeople.utils.neo4jApi.parsing;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class CypherQueryPropertiesTest {

    CypherQueryProperties cut;


    @Before
    public void setUp(){
        cut = new CypherQueryProperties();
    }

    @Test
    public void testForId_add() throws Exception {

        cut.forId("test1").add("key", "value");
        cut.forId("test2").add("key", "value");
        assertNotNull(cut.getProperties().get("test1"));
        assertThat(cut.getProperties().get("test1").get("key"), is("value"));
        assertNotNull(cut.getProperties().get("test2"));
        assertThat(cut.getProperties().get("test2").get("key"), is("value"));

    }

    @Test
    public void testForId_done_String_Object() throws Exception {

        CypherQueryProperties q1 = cut.forId("test1").done("key", "value");
        CypherQueryProperties q2 = cut.forId("test2").done("key", "value");

        assertThat(cut, is(q1));
        assertThat(cut, is(q2));

        assertNotNull(cut.getProperties().get("test1"));
        assertThat(cut.getProperties().get("test1").get("key"), is("value"));
        assertNotNull(cut.getProperties().get("test2"));
        assertThat(cut.getProperties().get("test2").get("key"), is("value"));

    }

    @Test
    public void testForId_add_done() throws Exception {

        CypherQueryProperties q1 = cut.forId("test1").add("key", "value").done();
        CypherQueryProperties q2 = cut.forId("test2").add("key", "value").done();

        assertThat(cut, is(q1));
        assertThat(cut, is(q2));

        assertNotNull(cut.getProperties().get("test1"));
        assertThat(cut.getProperties().get("test1").get("key"), is("value"));
        assertNotNull(cut.getProperties().get("test2"));
        assertThat(cut.getProperties().get("test2").get("key"), is("value"));

    }

    @Test
    public void testAdd_Sring_String_Object() throws Exception {

        cut.add("test1", "key", "value");
        cut.add("test2", "key", "value");
        assertNotNull(cut.getProperties().get("test1"));
        assertThat(cut.getProperties().get("test1").get("key"), is("value"));
        assertNotNull(cut.getProperties().get("test2"));
        assertThat(cut.getProperties().get("test2").get("key"), is("value"));

    }

    @Test
    public void testAdd_String_map() throws Exception {

        Map<String, Object> props = new HashMap<>();
        props.put("key", "value");
        cut.add("test1", props);

        props = new HashMap<>();
        props.put("key", "value");
        cut.add("test2", props);

        assertNotNull(cut.getProperties().get("test1"));
        assertThat(cut.getProperties().get("test1").get("key"), is("value"));
        assertNotNull(cut.getProperties().get("test2"));
        assertThat(cut.getProperties().get("test2").get("key"), is("value"));

    }
}