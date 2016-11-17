package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.DateFormat;
import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

import java.util.Date;

@Regex("dummy")
public class BeanWithBadDateFormat {
    @Group(1)
    private String name;

    @Group(2)
    @DateFormat("abc123")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
