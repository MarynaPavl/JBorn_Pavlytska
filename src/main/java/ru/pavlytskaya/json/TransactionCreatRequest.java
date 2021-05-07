package ru.pavlytskaya.json;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionCreatRequest {
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal sum;
    private String data;
    private Long assignmentId;
}
