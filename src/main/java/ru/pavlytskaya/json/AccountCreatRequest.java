package ru.pavlytskaya.json;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class AccountCreatRequest {
    private String nameAccount;
    private BigDecimal balance;
    private String currency;
    private long userId;

}
