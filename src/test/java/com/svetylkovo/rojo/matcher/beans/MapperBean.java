package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Mapper;
import com.svetylkovo.rojo.annotations.Regex;
import com.svetylkovo.rojo.matcher.mappers.NumbersMapper;

@Regex("(\\d+)")
public class MapperBean {

    @Group(1)
    @Mapper(NumbersMapper.class)
    private int[] numbers;

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }
}
