package com.svetylkovo.rojo.lambda;

@FunctionalInterface
public interface GroupMapArgs2<T,U> {
    public U apply(T g1, T g2);
}
