package ru.pavlytskaya.comverter;

public interface Converter<S, T> {
    T convert(S source);
}
