package ru.pavlytskaya.dao;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class TransactionInformationModel {
    private long id;
    private Integer accountFrom;
    private Integer accountTo;
    private BigDecimal sum;
    private LocalDate data;


}
