package ru.pavlytskaya.service;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class TransactionInformationDTO {
    private long id;
    private String transfer;
    private BigDecimal sum;
    private String data;

}
