package ru.pavlytskaya.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionCreateResponse {
    private long id;
    private String transfer;
    private BigDecimal sum;
    private LocalDate data;

}
