package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.Group;

public class BeanWithNoRegex {
    @Group(1)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
