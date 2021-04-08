package ru.pavlytskaya.service;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class AccountDTO {
    private long id;
    private String nameAccount;
    private BigDecimal balance;
    private String currency;

}
