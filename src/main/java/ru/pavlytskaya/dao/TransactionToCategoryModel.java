package ru.pavlytskaya.dao;

import lombok.Data;

import java.util.Objects;

@Data
public class TransactionToCategoryModel {
    private long idTransaction;
    private long idCategory;
}
