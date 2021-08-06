package ru.pavlytskaya.service;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.pavlytskaya.entity.AccountModel;

import java.util.List;

@Accessors(chain = true)
@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountModel> accounts;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ",\n accounts=" + accounts +
                '}';
    }
}
