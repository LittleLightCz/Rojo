package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.exceptions.MissingDateFormatAnnotationException;
import com.svetylkovo.rojo.exceptions.MissingRegexAnnotationException;
import com.svetylkovo.rojo.matcher.beans.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RojoBeanProcessorTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private String flagsTestString = "abc,def,\nghi,jkl";


    @Test
    public void processAnnotationsMissingRegexTest() {
        expectedEx.expect(MissingRegexAnnotationException.class);
        expectedEx.expectMessage("Please annotate the "+BeanWithNoRegex.class.getName()+" class with the @Regex.");
        new RojoBeanProcessor<>(BeanWithNoRegex.class).processAnnotations();
    }

    @Test
    public void processAnnotationsBadDateFormatTest() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Failed to process annotations for field date in class "+BeanWithBadDateFormat.class.getName());
        new RojoBeanProcessor<>(BeanWithBadDateFormat.class).processAnnotations();
    }

    @Test
    public void processAnnotationsMissingDateFormatTest() {
        expectedEx.expect(MissingDateFormatAnnotationException.class);
        expectedEx.expectMessage("@DateFormat annotation not specified for the date field in the "+BeanWithNoDateFormat.class.getName()+" class.");
        new RojoBeanProcessor<>(BeanWithNoDateFormat.class).processAnnotations();
    }

    @Test
    public void noFlagsTest() {
        RojoBeanProcessor<NoFlagsBean> processor = new RojoBeanProcessor<>(NoFlagsBean.class);
        processor.processAnnotations();

        MatchIterator it = new MatchIterator(processor.getMatcher(flagsTestString));

        BeanIterator<NoFlagsBean> beanIterator = new BeanIterator<>(it, processor);
        beanIterator.hasNext();

        NoFlagsBean bean = beanIterator.next();
        assertEquals("abc,def,", bean.getValue());
    }

    @Test
    public void flagsTest() {
        RojoBeanProcessor<FlagsBean> processor = new RojoBeanProcessor<>(FlagsBean.class);
        processor.processAnnotations();

        MatchIterator it = new MatchIterator(processor.getMatcher(flagsTestString));

        BeanIterator<FlagsBean> beanIterator = new BeanIterator<>(it, processor);
        beanIterator.hasNext();

        FlagsBean bean = beanIterator.next();
        assertEquals("abc,def,\nghi,jkl", bean.getValue());
    }

    @Test
    public void mapperTest() {
        RojoBeanProcessor<MapperBean> processor = new RojoBeanProcessor<>(MapperBean.class);
        processor.processAnnotations();

        MatchIterator it = new MatchIterator(processor.getMatcher("123"));

        BeanIterator<MapperBean> beanIterator = new BeanIterator<>(it, processor);
        beanIterator.hasNext();

        MapperBean bean = beanIterator.next();

        assertArrayEquals(new int[]{1,2,3}, bean.getNumbers());
    }

}