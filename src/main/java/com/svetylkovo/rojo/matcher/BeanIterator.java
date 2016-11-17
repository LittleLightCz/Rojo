package com.svetylkovo.rojo.matcher;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

public class BeanIterator<T> implements Iterator<T> {

    private MatchIterator matchIter;
    private RojoBeanProcessor<T> processor;

    public BeanIterator(MatchIterator matchIter, RojoBeanProcessor<T> processor) {
        this.matchIter = matchIter;
        this.processor = processor;
    }

    @Override
    public boolean hasNext() {
        return matchIter.hasNext();
    }

    @Override
    public T next() {
        T bean = processor.newBeanInstance();
        List<BeanField> beanFields = processor.getBeanFields();

        for ( BeanField beanField : beanFields) {
            Matcher matcher = matchIter.next();
            processor.invokeSetter(bean, beanField, matcher);
        }
        return bean;
    }
}