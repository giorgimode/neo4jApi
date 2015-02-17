package com.poolingpeople.utils.neo4jApi;

import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponseStreamingParserTest {

    ResponseStreamingParser cut;

    @Before
    public void setUp() throws Exception {
        cut = new ResponseStreamingParser();
    }

    @Test
    public void testParseList() throws Exception {

        InputStream r = this.getClass().getClassLoader().getResourceAsStream("cypherResponse.json");
        List<Map<String,Object>> list = cut.parseList(r);

        System.out.println(list);

    }

    @Test
    public void testParseList1() throws Exception {

    }

    @Test
    public void testParseSimpleListOrException() throws Exception {

    }

    @Test
    public void testParseOrException() throws Exception {

    }

    @Test
    public void testParse() throws Exception {

    }

    @Test
    public void testParse1() throws Exception {

    }
}