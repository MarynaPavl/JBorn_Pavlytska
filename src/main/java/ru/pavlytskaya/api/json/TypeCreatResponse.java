package ru.pavlytskaya.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeCreatResponse {
    private long id;
    private String assignment;
}
