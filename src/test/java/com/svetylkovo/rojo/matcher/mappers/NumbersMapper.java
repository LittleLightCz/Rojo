package com.svetylkovo.rojo.matcher.mappers;

import java.util.function.Function;

public class NumbersMapper implements Function<String, int[]> {
    @Override
    public int[] apply(String group) {
        return group.chars()
                .boxed()
                .map(Character::toChars)
                .map(String::valueOf)
                .map(Integer::valueOf)
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
