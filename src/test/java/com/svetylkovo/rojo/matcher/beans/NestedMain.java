package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

@Regex("(\\w):(.+)")
public class NestedMain {

    @Group(1)
    private String letter;

    @Group(2)
    private NestedInner inner;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public NestedInner getInner() {
        return inner;
    }

    public void setInner(NestedInner inner) {
        this.inner = inner;
    }
}
