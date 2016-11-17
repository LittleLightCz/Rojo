package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.DateFormat;
import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

import java.util.Date;

@Regex(".+")
public class DateBean {

    @Group(0)
    @DateFormat("yyyyMMdd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
