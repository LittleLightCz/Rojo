package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.annotations.*;
import com.svetylkovo.rojo.exceptions.MissingDateFormatAnnotationException;
import com.svetylkovo.rojo.exceptions.MissingRegexAnnotationException;
import com.svetylkovo.rojo.exceptions.UnsupportedFieldTypeException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class RojoBeanProcessor<T> {
    private Class<T> rojoBean;

    private Pattern pattern;
    private List<BeanField> beanFields;

    public RojoBeanProcessor(Class<T> rojoBean) {
        this.rojoBean = rojoBean;
    }

    public void processAnnotations() {
        if (!rojoBean.isAnnotationPresent(Regex.class)) {
            throw new MissingRegexAnnotationException("Please annotate the "+rojoBean.getName()+" class with the @"+Regex.class.getSimpleName()+".");
        }

        String regex = rojoBean.getAnnotation(Regex.class).value();
        if (rojoBean.isAnnotationPresent(Flags.class)) {
            int flags = rojoBean.getAnnotation(Flags.class).value();
            pattern = Pattern.compile(regex, flags);
        } else {
            pattern = Pattern.compile(regex);
        }

        beanFields = Arrays.stream(rojoBean.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Group.class))
                    .map( field -> {
                        Method setter = getSetterMethod(field);
                        Group group = field.getAnnotation(Group.class);
                        Class<?> type = field.getType();
                        Function<String, ?> conversion = getConversionFunc(type, field);
                        return new BeanField(setter, group, conversion, type);
                    })
                    .collect(toList());
    }

    private Function<String,?> getConversionFunc(Class<?> type, Field field) {

        //Use custom mapper
        if (field.isAnnotationPresent(Mapper.class)) {
            Class<? extends Function<String, ?>> mapperClass = field.getAnnotation(Mapper.class).value();
            try {
                return mapperClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create an instance of " + mapperClass.getName() + " specified in the @Mapper annotation of the field " + field.getName() + " in the " + rojoBean.getName() + " class");
            }
        }

        //Use nested matching
        if (type.isAnnotationPresent(Regex.class)) {
            RojoBeanProcessor<?> nestedProcessor = new RojoBeanProcessor<>(type);
            nestedProcessor.processAnnotations();

            return groupStr -> {
                MatchIterator matchIterator = new MatchIterator(nestedProcessor.getMatcher(groupStr));
                BeanIterator<?> beanIterator = new BeanIterator<>(matchIterator, nestedProcessor);
                if (beanIterator.hasNext()) {
                    return beanIterator.next();
                } else {
                    return null;
                }
            };
        }

        if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
            return Integer::valueOf;
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
            return Long::valueOf;
        } else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
            return Short::valueOf;
        } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
            return Float::valueOf;
        } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
            return Double::valueOf;
        } else if (type.isAssignableFrom(BigInteger.class)) {
            return BigInteger::new;
        } else if (type.isAssignableFrom(BigDecimal.class)) {
            return BigDecimal::new;
        } else if (type.isAssignableFrom(Date.class)) {
            if (!field.isAnnotationPresent(DateFormat.class)) {
                throw new MissingDateFormatAnnotationException("@DateFormat annotation not specified for the "+field.getName()+" field in the "+rojoBean.getName()+" class.");
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(field.getAnnotation(DateFormat.class).value());
                return s -> {
                    try {
                        return sdf.parse(s);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                };
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Failed to process annotations for field "+field.getName()+" in class "+rojoBean.getName(), e);
            }
        } else if (type.isAssignableFrom(String.class)) {
            return s -> s;
        } else {
            throw new UnsupportedFieldTypeException("The " + type.getName() + " type of the field " + field.getName() + " in class " + rojoBean.getName() + " is not supported by this library (yet). You may want to consider using @Mapper annotation and define your own mapping function.");
        }
    }

    private Method getSetterMethod(Field f) {
        try {
            return new PropertyDescriptor(f.getName(), rojoBean).getWriteMethod();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    public Matcher getMatcher(String str) {
        return pattern.matcher(str);
    }

    public List<BeanField> getBeanFields() {
        return beanFields;
    }

    public T newBeanInstance() {
        try {
            return rojoBean.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void invokeSetter(T beanInstance, BeanField beanField, Matcher matcher) {
        try {
            Method setter = beanField.getSetter();
            Group group = beanField.getGroup();
            String value = matcher.group(group.value());
            setter.invoke(beanInstance, beanField.getConversion().apply(value));
        } catch (Exception e) {
            throw new RuntimeException("Error when calling setter method "+beanField.getSetter().getName() + " in class "+rojoBean.getName(), e);
        }
    }
}
