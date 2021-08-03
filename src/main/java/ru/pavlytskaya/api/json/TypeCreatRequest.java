package ru.pavlytskaya.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TypeCreatRequest {
    @NotNull
    private String assignment;
}
