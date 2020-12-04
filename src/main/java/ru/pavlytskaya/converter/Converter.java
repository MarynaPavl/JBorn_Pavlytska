package ru.pavlytskaya.converter;

import java.util.List;

public interface Converter<S, T> {
    T convert(S source);

    default List<T> convert(List<S> list) {
        return null;
    }
}
