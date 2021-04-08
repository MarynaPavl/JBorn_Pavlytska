package ru.pavlytskaya.json;

import lombok.Data;

@Data
public class TransactToCatygoryRequest {
    private long idTransaction;
    private long idCategory;
}
