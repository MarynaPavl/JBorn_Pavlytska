package ru.pavlytskaya.json;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {
    @NotNull
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @NotNull
    private String password;
}
