package ru.pavlytskaya.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TypeCreateForm {
    @NotEmpty
    private String assignment;
}
