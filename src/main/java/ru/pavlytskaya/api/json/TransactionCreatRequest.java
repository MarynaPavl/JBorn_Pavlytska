package ru.pavlytskaya.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class TransactionCreatRequest {
    private Long accountFrom;
    private Long accountTo;
    @NotNull
    private BigDecimal sum;
    @NotNull
    private LocalDate data;
    private Set<Long> assignmentId;
}
