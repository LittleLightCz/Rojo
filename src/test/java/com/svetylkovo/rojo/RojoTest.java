package com.svetylkovo.rojo;

import com.svetylkovo.rojo.matcher.beans.DateBean;
import com.svetylkovo.rojo.matcher.beans.SimpleBean;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

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
    public void asIterableTest() throws Exception {
        List<String> list = new ArrayList<>();

        for ( String match : Rojo.asIterable("[a-z]", input)) {
            list.add(match);
        }

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

    @Test
    public void forEachTest2() throws Exception {
        List<String> expected = Arrays.asList("a1", "b2", "c3");
        List<String> result = new ArrayList<>();
        Rojo.forEach("([a-z]):(\\d)", input, (letter, num) -> result.add(letter+num));
        assertEquals(expected, result);
    }

    @Test
    public void forEachTest3() throws Exception {
        List<String> expected = Arrays.asList(":a1", ":b2", ":c3");
        List<String> result = new ArrayList<>();
        Rojo.forEach("([a-z])(:)(\\d)", input, (letter, colon, num) -> result.add(colon+letter+num));
        assertEquals(expected, result);
    }

    @Test
    public void badForEachTest() throws Exception {
        expectedEx.expect(IndexOutOfBoundsException.class);
        expectedEx.expectMessage("No group 4");
        Rojo.forEach("([a-z])(:)(\\d)", input, (letter, colon, num, unused) -> {});
    }

    @Test
    public void firstGroupTest() throws Exception {
        List<String> result = Rojo.firstGroup("\\{(.+?)\\}", "{one},{two},{three}")
                                .collect(Collectors.toList());

        assertEquals(Arrays.asList("one","two","three"), result);
    }

    @Test
    public void mapTest2() throws Exception {
        List<String> expected = Arrays.asList("a1", "b2", "c3");
        List<String> result = Rojo.map("([a-z]):(\\d)", input, (letter, num) -> letter+num)
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void mapTest3() throws Exception {
        List<String> expected = Arrays.asList(":a1", ":b2", ":c3");
        List<String> result = Rojo.map("([a-z])(:)(\\d)", input, (letter, colon, num) -> colon+letter+num)
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void badMapTest() throws Exception {
        expectedEx.expect(IndexOutOfBoundsException.class);
        expectedEx.expectMessage("No group 4");
        Rojo.map("([a-z])(:)(\\d)", input, (letter, colon, num, unused) -> "").findFirst();
    }


}