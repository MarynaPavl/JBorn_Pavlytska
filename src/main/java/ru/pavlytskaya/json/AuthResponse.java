package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.dao.AccountModel;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountModel> accounts;

    public AuthResponse(long id, String firstName, String lastName, String email) {
    }
}
