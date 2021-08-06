package ru.pavlytskaya.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pavlytskaya.entity.TypeTransactionModel;

import java.util.List;

public interface TypeTransactionModelRepository extends JpaRepository<TypeTransactionModel, Long> {
    TypeTransactionModel findTypeTransactionModelById(@NotNull Long id);

    List<TypeTransactionModel> findAllByAssignmentIsStartingWith(String str);

    void deleteById(@NotNull Long assigmentId);
}