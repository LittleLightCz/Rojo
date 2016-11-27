package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

import java.util.List;

@Regex("(\\w):(.+)")
public class ListBeanNested {

    @Group(1)
    private String letter;

    @Group(2)
    private List<NestedInner> numbers;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<NestedInner> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<NestedInner> numbers) {
        this.numbers = numbers;
    }
}
