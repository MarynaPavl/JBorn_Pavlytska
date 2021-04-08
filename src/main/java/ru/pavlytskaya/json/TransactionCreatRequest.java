package ru.pavlytskaya.json;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionCreatRequest {
    private Integer accountFrom;
    private Integer accountTo;
    private BigDecimal sum;
    private String data;
}
