package ru.pavlytskaya.service;

import lombok.Data;

import java.util.Objects;

@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

}
