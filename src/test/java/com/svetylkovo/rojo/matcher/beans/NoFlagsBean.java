package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

@Regex(".+")
public class NoFlagsBean {

    @Group(0)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
