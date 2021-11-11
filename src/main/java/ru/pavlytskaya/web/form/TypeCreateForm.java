package ru.pavlytskaya.web.form;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Accessors(chain = true)
@Data
public class TypeCreateForm {
    @NotEmpty
    private String assignment;
}
