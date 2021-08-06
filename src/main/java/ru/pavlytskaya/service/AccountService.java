package ru.pavlytskaya.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.entity.AccountModel;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.AccountModelRepository;
import ru.pavlytskaya.repository.UserModelRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountModelRepository accountModelRepository;
    private final Converter<AccountModel, AccountDTO> accountDTOConverter;
    private final UserModelRepository userModelRepository;


    public List<AccountDTO> accountInformation(long userID) {
        List<AccountModel> accountModel = accountModelRepository.findAllByUserId(userID);
        if (accountModel == null) {
            return null;
        }
        return accountDTOConverter.convert(accountModel);
    }

    public AccountDTO accountCreat(String nameAccount, BigDecimal balance, String currency, long userID) {
        UserModel userModel = userModelRepository.findUserById(userID);
        AccountModel accountModel = new AccountModel()
                .setNameAccount(nameAccount)
                .setBalance(balance)
                .setCurrency(currency)
                .setUser(userModel);
        accountModelRepository.save(accountModel);

        return accountDTOConverter.convert(accountModel);
    }

    public int deleteAccount(long id) {
        try {
            accountModelRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return e.getActualSize();
        }
        return 1;
    }
}
