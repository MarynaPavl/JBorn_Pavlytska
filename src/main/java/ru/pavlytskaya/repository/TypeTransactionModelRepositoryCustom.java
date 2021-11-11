package ru.pavlytskaya.repository;

import ru.pavlytskaya.entity.TypeTransactionModel;

import java.util.List;

public interface TypeTransactionModelRepositoryCustom {

    List<TypeTransactionModel> findByFilter(TypeTransactionModelFilter filter);

}