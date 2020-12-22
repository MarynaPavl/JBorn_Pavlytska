package ru.pavlytskaya.converter;

import java.util.ArrayList;
import java.util.List;

public interface Converter<S, T> {
    T convert(S source);

    default List<T> convert(List<S> list) {
        return new ArrayList<>();
    }
}
