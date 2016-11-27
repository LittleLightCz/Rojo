package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.exceptions.MissingDateFormatAnnotationException;
import com.svetylkovo.rojo.exceptions.MissingRegexAnnotationException;
import com.svetylkovo.rojo.matcher.beans.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

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
        NoFlagsBean bean = getMatchedBean(NoFlagsBean.class, flagsTestString);
        assertEquals("abc,def,", bean.getValue());
    }

    @Test
    public void flagsTest() {
        FlagsBean bean = getMatchedBean(FlagsBean.class, flagsTestString);
        assertEquals("abc,def,\nghi,jkl", bean.getValue());
    }

    @Test
    public void mapperTest() {
        MapperBean bean = getMatchedBean(MapperBean.class, "123");
        assertArrayEquals(new int[]{1,2,3}, bean.getNumbers());
    }

    @Test
    public void nestedTest() {
        NestedMain bean = getMatchedBean(NestedMain.class, "a:123");
        NestedInner inner = bean.getInner();

        assertEquals("a", bean.getLetter());
        assertEquals(1, inner.getFirst());
        assertEquals(2, inner.getSecond());
        assertEquals(3, inner.getThird());
    }

    @Test
    public void nestedWrongMatchTest() {
        NestedMain bean = getMatchedBean(NestedMain.class, "a:xxx");
        NestedInner inner = bean.getInner();

        assertEquals("a", bean.getLetter());
        assertNull(inner);
    }

    @Test
    public void listOfFieldsPlainTest() {
        ListBeanPlain bean = getMatchedBean(ListBeanPlain.class, "a:123");
        List<Integer> numbers = bean.getNumbers();

        assertEquals("a", bean.getLetter());
        assertEquals(1, numbers.get(0).intValue());
        assertEquals(2, numbers.get(1).intValue());
        assertEquals(3, numbers.get(2).intValue());
    }

    @Test
    public void listOfFieldsBadTest() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Could't get the regexp pattern for the List<> field numbers in the "+ListBeanBad.class.getName()+" class. Use either @Regex to annotate the List<> field itself, or use the List whose generic type is of a class, which is annotated by the @Regex and @Group accordingly.");
        ListBeanBad bean = getMatchedBean(ListBeanBad.class, "a:123");
    }

    @Test
    public void listOfFieldsNestedTest() {
        ListBeanNested bean = getMatchedBean(ListBeanNested.class, "a:123456789");
        List<NestedInner> numbers = bean.getNumbers();

        assertEquals("a", bean.getLetter());

        int expected = 1;
        for ( NestedInner num : numbers) {
            assertEquals(expected++, num.getFirst());
            assertEquals(expected++, num.getSecond());
            assertEquals(expected++, num.getThird());
        }
    }

    @Test
    public void listOfFieldsDateTest() throws ParseException {
        ListBeanDate bean = getMatchedBean(ListBeanDate.class, "a:2000/2001/2002");
        List<Date> years = bean.getYears();

        assertEquals("a", bean.getLetter());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        assertEquals(sdf.parse("2000"), years.get(0));
        assertEquals(sdf.parse("2001"), years.get(1));
        assertEquals(sdf.parse("2002"), years.get(2));
    }

    private <T> T getMatchedBean(Class<T> clazz, String input) {
        RojoBeanProcessor<T> processor = new RojoBeanProcessor<>(clazz);
        processor.processAnnotations();

        MatchIterator it = new MatchIterator(processor.getMatcher(input));

        BeanIterator<T> beanIterator = new BeanIterator<>(it, processor);
        beanIterator.hasNext();

        return beanIterator.next();
    }

}