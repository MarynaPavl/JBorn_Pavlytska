package ru.pavlytskaya.api.json;

import lombok.Data;

@Data
public class TransactionDeleteRequest {
    private long transactionId;
}
