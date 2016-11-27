package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.DateFormat;
import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

import java.util.Date;
import java.util.List;

@Regex("(\\w):(.+)")
public class ListBeanDate {

    @Group(1)
    private String letter;

    @Group(2)
    @Regex("\\d+")
    @DateFormat("yyyy")
    private List<Date> years;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<Date> getYears() {
        return years;
    }

    public void setYears(List<Date> years) {
        this.years = years;
    }
}
