package ru.pavlytskaya.service;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.pavlytskaya.entity.AccountModel;
import ru.pavlytskaya.security.UserRole;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;

@Accessors(chain = true)
@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountModel> accounts;
    private Set<UserRole> roles = emptySet();

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                ", roles=" + roles +
                '}';
    }
}
