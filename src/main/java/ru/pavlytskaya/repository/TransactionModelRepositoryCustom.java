package ru.pavlytskaya.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.TransactionInformationModel;

import java.util.List;

@Service
@Component
public interface TransactionModelRepositoryCustom {
    List<TransactionInformationModel> findByFilter(TransactionModelFilter transactionFilter);
}
