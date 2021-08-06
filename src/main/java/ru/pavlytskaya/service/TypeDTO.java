package ru.pavlytskaya.service;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TypeDTO {
    private long id;
    private String assignment;

}
