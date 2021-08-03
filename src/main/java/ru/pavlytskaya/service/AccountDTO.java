package ru.pavlytskaya.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Accessors(chain = true)
@Data
public class AccountDTO {
    private long id;
    private String nameAccount;
    private BigDecimal balance;
    private String currency;

}
