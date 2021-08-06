package ru.pavlytskaya.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Accessors(chain = true)
@Data
public class TransactionInformationDTO {
    private long id;
    private String transfer;
    private BigDecimal sum;
    private LocalDate data;

}
