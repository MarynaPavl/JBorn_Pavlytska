package ru.pavlytskaya.dao;


import lombok.Data;

import java.util.Objects;

@Data
public class UserModel {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


}
