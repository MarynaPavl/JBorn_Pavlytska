package ru.pavlytskaya.web.form;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Accessors(chain = true)
@Data
public class TransactionCreateForm {
    private Long accountFrom;
    private Long accountTo;
    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal sum;
    private String data;
    private Set<Long> assignmentId;
}
