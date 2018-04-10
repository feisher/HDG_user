package com.yusong.yslib.utils;

public interface Builder<T> {

    /**
     * Returns a reference to the object being constructed or result being 
     * calculated by the builder.
     * 
     * @return the object constructed or result calculated by the builder.
     */
    T build();
}