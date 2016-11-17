package com.svetylkovo.rojo;

import com.svetylkovo.rojo.matcher.beans.DateBean;
import com.svetylkovo.rojo.matcher.beans.SimpleBean;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RojoTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private String input = "a:1,b:2,c:3";

    @Test
    public void ofTest() throws Exception {
        SimpleBean bean = Rojo.of(SimpleBean.class)
                            .match(input).get();

        assertEquals("a", bean.getName());
        assertEquals(1, bean.getCount());
    }

    @Test
    public void badDateInputTest() throws Exception {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Error when calling setter method setDate in class "+DateBean.class.getName());
        Rojo.of(DateBean.class).match("badinput");
    }

    @Test(expected = NoSuchElementException.class)
    public void notFoundTest() throws Exception {
        Rojo.of(SimpleBean.class).match("123465").get();
    }

    @Test
    public void asListTest() throws Exception {
        List<String> list = Rojo.asList("[a-z]", input);
        assertEquals(Arrays.asList("a", "b", "c"), list);
    }

    @Test
    public void asMapTest() throws Exception {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("a", "1");
        expected.put("b", "2");
        expected.put("c", "3");

        Map<String, String> map = Rojo.asMap("([a-z]):(\\d)", input);
        assertEquals(expected, map);
    }

    @Test
    public void findTest() throws Exception {
        Optional<String> item = Rojo.find("[a-z]:2", input);
        assertEquals("b:2", item.get());
    }

    @Test
    public void replaceTest() throws Exception {
        String replaced = Rojo.replace("[a-z]", input, String::toUpperCase);
        assertEquals("A:1,B:2,C:3", replaced);
    }

}