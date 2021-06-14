package ru.pavlytskaya.json;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class AccountCreatRequest {
    private String nameAccount;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private String currency;
    @NotNull
    private long userId;

}
