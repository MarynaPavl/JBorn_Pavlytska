package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.service.AccountDTO;

@Data
@AllArgsConstructor
public class AccountCreatResponse {
    private AccountDTO accountDTO;

}
