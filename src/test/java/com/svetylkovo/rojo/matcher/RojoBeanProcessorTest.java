package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.matcher.beans.*;
import com.svetylkovo.rojo.exceptions.MissingDateFormatAnnotationException;
import com.svetylkovo.rojo.exceptions.MissingRegexAnnotationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

        BeanIterator<NoFlagsBean> beanProcessor = new BeanIterator<>(it, processor);
        beanProcessor.hasNext();

        NoFlagsBean bean = beanProcessor.next();
        assertEquals("abc,def,", bean.getValue());
    }

    @Test
    public void flagsTest() {
        RojoBeanProcessor<FlagsBean> processor = new RojoBeanProcessor<>(FlagsBean.class);
        processor.processAnnotations();

        MatchIterator it = new MatchIterator(processor.getMatcher(flagsTestString));

        BeanIterator<FlagsBean> beanProcessor = new BeanIterator<>(it, processor);
        beanProcessor.hasNext();

        FlagsBean bean = beanProcessor.next();
        assertEquals("abc,def,\nghi,jkl", bean.getValue());
    }

}