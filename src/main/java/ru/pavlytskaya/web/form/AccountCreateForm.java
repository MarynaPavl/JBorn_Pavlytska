package ru.pavlytskaya.web.form;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AccountCreateForm {
    private String nameAccount;
    @NotNull
    private BigDecimal balance;
    @NotEmpty
    private String currency;
}
