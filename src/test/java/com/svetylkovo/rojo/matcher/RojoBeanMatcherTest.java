package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.matcher.beans.SimpleBean;
import com.svetylkovo.rojo.matcher.beans.TestBean;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RojoBeanMatcherTest {

    private RojoBeanMatcher<TestBean> rojo;
    private RojoBeanMatcher<SimpleBean> rojoSimple;
    private String testString;
    private String simpleTestString;

    @Before
    public void setUp() throws Exception {
        rojo = new RojoBeanMatcher<>(TestBean.class);
        rojoSimple = new RojoBeanMatcher<>(SimpleBean.class);
        testString = "Rojo:123:123s:123l:123.4f:123.4d:123big:123.4big:1/2/2016";
        simpleTestString = "apple:12,pear:7,banana:2";
    }

    @Test
    public void matchFoundTest() throws Exception {
        Optional<TestBean> result = rojo.match(testString);
        verifyTestBean(result.get());
    }

    @Test
    public void matchIteratorTest() throws Exception {
        Iterator<TestBean> it = rojo.matchIterator(testString);
        it.hasNext();
        verifyTestBean(it.next());
    }

    private void verifyTestBean(TestBean bean) throws ParseException {
        assertEquals("Rojo", bean.getStr());
        assertEquals(123, bean.getIntNum());
        assertEquals((short)123, bean.getShortNum());
        assertEquals(123l, bean.getLongNum());
        assertEquals(123.4f, bean.getFloatNum(), 0.000001f);
        assertEquals(123.4d, bean.getDoubleNum(), 0.000001d);
        assertEquals(new BigInteger("123"), bean.getBigInt());
        assertEquals(new BigDecimal("123.4"), bean.getBigDecimal());
        assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("1/2/2016"), bean.getDate());
    }

    @Test
    public void matchNotFoundTest() throws Exception {
        Optional<TestBean> result = rojo.match("Rojo123");
        assertFalse(result.isPresent());
    }


    @Test
    public void matchIteratorNoMatchTest() throws Exception {
        Iterator<TestBean> it = rojo.matchIterator("Rojo123");
        assertFalse(it.hasNext());
    }

    @Test
    public void matchListTest() throws Exception {
        List<SimpleBean> result = rojoSimple.matchList(simpleTestString);
        assertEquals(3, result.size());
        assertPair("apple", 12, result.get(0));
        assertPair("pear", 7, result.get(1));
        assertPair("banana", 2, result.get(2));
    }

    private void assertPair(String name, int count, SimpleBean bean) {
        assertEquals(name, bean.getName());
        assertEquals(count, bean.getCount());
    }
}
