package ru.pavlytskaya.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 8, max = 10)
    private String password;
}
