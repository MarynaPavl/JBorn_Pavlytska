package ru.pavlytskaya.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.TransactionInformationModel;

@Service
@Component
public interface TransactionModelRepository extends JpaRepository<TransactionInformationModel, Long>, TransactionModelRepositoryCustom {
    void deleteById(@NotNull Long transactionId);

}
