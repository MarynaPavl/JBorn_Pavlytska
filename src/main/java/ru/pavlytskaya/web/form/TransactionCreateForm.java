package ru.pavlytskaya.web.form;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class TransactionCreateForm {
    private Long accountFrom;
    private Long accountTo;
    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal sum;
//   @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String data;
    private Set<Long> assignmentId;
}
