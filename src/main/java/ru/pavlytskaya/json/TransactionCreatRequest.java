package ru.pavlytskaya.json;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class TransactionCreatRequest {
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal sum;
    private String data;
    private Set<Long> assignmentId;
}
