package ru.pavlytskaya.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pavlytskaya.service.AccountDTO;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AccountCreatResponse {
    @NotNull
    private AccountDTO accountDTO;

}
