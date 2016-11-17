package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.Flags;
import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

import java.util.regex.Pattern;

@Regex(".+")
@Flags(Pattern.DOTALL)
public class FlagsBean {

    @Group(0)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
