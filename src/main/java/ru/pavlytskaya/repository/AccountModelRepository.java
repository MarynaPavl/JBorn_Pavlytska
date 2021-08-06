package ru.pavlytskaya.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.AccountModel;

import java.util.List;

@Service
@Component
public interface AccountModelRepository extends JpaRepository<AccountModel, Long> {
    List<AccountModel> findAllByUserId(Long userId);

    void deleteById(@NotNull Long accountId);

    AccountModel findAccountModelById(Long accountId);
}
