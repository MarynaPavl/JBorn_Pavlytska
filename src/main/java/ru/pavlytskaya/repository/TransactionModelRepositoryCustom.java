package ru.pavlytskaya.repository;

import ru.pavlytskaya.entity.TransactionInformationModel;

import java.util.List;


public interface TransactionModelRepositoryCustom {
    List<TransactionInformationModel> findByFilter(TransactionModelFilter transactionFilter);
}
