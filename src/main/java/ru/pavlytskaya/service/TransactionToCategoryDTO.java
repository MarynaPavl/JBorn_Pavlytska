package ru.pavlytskaya.service;


import lombok.Data;

import java.util.Objects;

@Data
public class TransactionToCategoryDTO {
    private long idTransaction;
    private long idCategory;
}
