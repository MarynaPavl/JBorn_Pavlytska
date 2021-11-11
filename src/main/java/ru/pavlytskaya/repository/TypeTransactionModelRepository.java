package ru.pavlytskaya.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.TypeTransactionModel;

@Component
@Service
public interface TypeTransactionModelRepository extends JpaRepository<TypeTransactionModel, Long>,TypeTransactionModelRepositoryCustom {
    TypeTransactionModel findTypeTransactionModelById(@NotNull Long id);

    void deleteById(@NotNull Long assigmentId);
}