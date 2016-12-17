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
    public void forEachTest1() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "a";
        Rojo.forEach("(\\w)", input, (a) -> sb.append(a));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest2() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "ab";
        Rojo.forEach("(\\w)(\\w)", input, (a,b) -> sb.append(a+b));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest3() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abc";
        Rojo.forEach("(\\w)(\\w)(\\w)", input, (a,b,c) -> sb.append(a+b+c));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest4() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcd";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d) -> sb.append(a+b+c+d));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest5() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcde";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e) -> sb.append(a+b+c+d+e));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest6() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdef";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f) -> sb.append(a+b+c+d+e+f));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest7() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefg";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g) -> sb.append(a+b+c+d+e+f+g));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest8() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefgh";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h) -> sb.append(a+b+c+d+e+f+g+h));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest9() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefghi";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h,i) -> sb.append(a+b+c+d+e+f+g+h+i));
        assertEquals(input, sb.toString());
    }

    @Test
    public void forEachTest10() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefghij";
        Rojo.forEach("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h,i,j) -> sb.append(a+b+c+d+e+f+g+h+i+j));
        assertEquals(input, sb.toString());
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
    public void mapTest1() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "a";
        Rojo.map("(\\w)", input, (a) -> a).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest2() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "ab";
        Rojo.map("(\\w)(\\w)", input, (a,b) -> a+b).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest3() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abc";
        Rojo.map("(\\w)(\\w)(\\w)", input, (a,b,c) -> a+b+c).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest4() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcd";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d) -> a+b+c+d).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest5() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcde";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e) -> a+b+c+d+e).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest6() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdef";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f) -> a+b+c+d+e+f).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest7() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefg";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g) -> a+b+c+d+e+f+g).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest8() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefgh";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h) -> a+b+c+d+e+f+g+h).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest9() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefghi";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h,i) -> a+b+c+d+e+f+g+h+i).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void mapTest10() throws Exception {
        StringBuffer sb = new StringBuffer();
        input = "abcdefghij";
        Rojo.map("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h,i,j) -> a+b+c+d+e+f+g+h+i+j).forEach(sb::append);
        assertEquals(input, sb.toString());
    }

    @Test
    public void badMapTest() throws Exception {
        expectedEx.expect(IndexOutOfBoundsException.class);
        expectedEx.expectMessage("No group 4");
        Rojo.map("([a-z])(:)(\\d)", input, (letter, colon, num, unused) -> "").findFirst();
    }

    @Test
    public void replaceGroupTest1() throws Exception {
        input = "a";
        String replaced = Rojo.replaceGroup("(\\w)", input, (a) -> (a).toUpperCase());
        assertEquals("A", replaced);
    }

    @Test
    public void replaceGroupTest2() throws Exception {
        input = "ab";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)", input, (a,b) -> (a+b).toUpperCase());
        assertEquals("AB", replaced);
    }

    @Test
    public void replaceGroupTest3() throws Exception {
        input = "abc";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)", input, (a,b,c) -> (a+b+c).toUpperCase());
        assertEquals("ABC", replaced);
    }

    @Test
    public void replaceGroupTest4() throws Exception {
        input = "abcd";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d) -> (a+b+c+d).toUpperCase());
        assertEquals("ABCD", replaced);
    }

    @Test
    public void replaceGroupTest5() throws Exception {
        input = "abcde";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e) -> (a+b+c+d+e).toUpperCase());
        assertEquals("ABCDE", replaced);
    }

    @Test
    public void replaceGroupTest6() throws Exception {
        input = "abcdef";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f) -> (a+b+c+d+e+f).toUpperCase());
        assertEquals("ABCDEF", replaced);
    }

    @Test
    public void replaceGroupTest7() throws Exception {
        input = "abcdefg";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g) -> (a+b+c+d+e+f+g).toUpperCase());
        assertEquals("ABCDEFG", replaced);
    }

    @Test
    public void replaceGroupTest8() throws Exception {
        input = "abcdefgh";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h) -> (a+b+c+d+e+f+g+h).toUpperCase());
        assertEquals("ABCDEFGH", replaced);
    }

    @Test
    public void replaceGroupTest9() throws Exception {
        input = "abcdefghi";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h,i) -> (a+b+c+d+e+f+g+h+i).toUpperCase());
        assertEquals("ABCDEFGHI", replaced);
    }

    @Test
    public void replaceGroupTest10() throws Exception {
        input = "abcdefghij";
        String replaced = Rojo.replaceGroup("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", input, (a,b,c,d,e,f,g,h,i,j) -> (a+b+c+d+e+f+g+h+i+j).toUpperCase());
        assertEquals("ABCDEFGHIJ", replaced);
    }

}