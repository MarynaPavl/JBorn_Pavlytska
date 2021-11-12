package ru.pavlytskaya.web.form;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Accessors(chain = true)
@Data
public class TypeInformationForm {
    @NotEmpty
    private String string;
}
