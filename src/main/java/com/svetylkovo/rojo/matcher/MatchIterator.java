package com.svetylkovo.rojo.matcher;

import java.util.Iterator;
import java.util.regex.Matcher;

public class MatchIterator implements Iterator<Matcher> {
    private Matcher matcher;

    public MatchIterator(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean hasNext() {
        return matcher.find();
    }

    @Override
    public Matcher next() {
        return matcher;
    }
}