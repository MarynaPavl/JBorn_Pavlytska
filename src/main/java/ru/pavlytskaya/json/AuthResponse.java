package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String firstName;
    private String lastNsme;
    private String email;
}
