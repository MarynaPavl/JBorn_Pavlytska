package ru.pavlytskaya.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.TransactionInformationModel;

import java.time.LocalDate;
import java.util.List;

@Service
@Component
public interface TransactionModelRepository extends JpaRepository<TransactionInformationModel, Long> {
    void deleteById(@NotNull Long transactionId);

    List<TransactionInformationModel> findAllByTypesIdAndDataBetween(long assignmentId, LocalDate fromDate, LocalDate toData);
}
