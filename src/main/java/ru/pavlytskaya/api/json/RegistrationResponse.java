package ru.pavlytskaya.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
}
