package com.svetylkovo.rojo.matcher;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class RojoBeanMatcher<T> {

    private RojoBeanProcessor processor;
    private Class<T> rojoBean;

    public RojoBeanMatcher(Class<T> rojoBean) {
        this.rojoBean = rojoBean;
        processor = new RojoBeanProcessor(rojoBean);
        processor.processAnnotations();
    }

    /**
     * Finds single match
     *
     * @param str Input string
     * @return Optional of matched bean
     */
    public Optional<T> match(String str) {
        Iterator<T> it = matchIterator(str);
        if (it.hasNext()) {
            return Optional.ofNullable(it.next());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Finds List of matches
     *
     * @param str Input string
     * @return List of matched beans
     */
    public List<T> matchList(String str) {
        return matchStream(str).collect(toList());
    }

    /**
     * Gets an Iterator of matched beans
     *
     * @param str Input string
     * @return Iterator of matched beans
     */
    public Iterator<T> matchIterator(String str) {
        MatchIterator matchIter = new MatchIterator(processor.getMatcher(str));
        return new BeanIterator<>(matchIter, processor);
    }

    /**
     * Gets an Iterable of matched beans
     *
     * @param str Input string
     * @return Iterable of matched beans
     */
    public Iterable<T> matchIterable(String str) {
        return () -> matchIterator(str);
    }

    /**
     * Gets Stream of matched beans
     *
     * @param str Input string
     * @param parallel Use true if you want to use a parallel {@link java.util.Spliterator} underneath.
     * @return Stream of matched beans
     */
    public Stream<T> matchStream(String str, boolean parallel) {
        Iterable<T> iterable = () -> matchIterator(str);
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * Gets Stream of matched beans
     *
     * @param str Input string
     * @return Stream of matched beans
     */
    public Stream<T> matchStream(String str) {
        return matchStream(str, false);
    }

}
