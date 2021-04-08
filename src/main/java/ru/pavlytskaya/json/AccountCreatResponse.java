package ru.pavlytskaya.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.UserDTO;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountCreatResponse {
    private long id;
    private String nameAccount;
    private BigDecimal balance;
    private String currency;

}
