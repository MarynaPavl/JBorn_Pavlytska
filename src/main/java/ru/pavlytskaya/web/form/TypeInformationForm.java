package ru.pavlytskaya.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TypeInformationForm {
    @NotEmpty
    private String string;
}
