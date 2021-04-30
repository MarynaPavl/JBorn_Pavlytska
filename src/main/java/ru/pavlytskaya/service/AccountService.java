package ru.pavlytskaya.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.converter.Converter;
import ru.pavlytskaya.dao.AccountDao;
import ru.pavlytskaya.dao.AccountModel;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;
    private final Converter<AccountModel, AccountDTO> accountDTOConverter;


    public List<AccountDTO> accountInformation(long userID) {
        List<AccountModel> accountModel = accountDao.listOfAccount(userID);
        if (accountModel == null) {
            return null;
        }
        return accountDTOConverter.convert(accountModel);
    }

    public AccountDTO accountCreat(String nameAccount, BigDecimal balance, String currency, long userID) {
        AccountModel accountModel = accountDao.creatAccount(nameAccount, balance, currency, userID);
        if (accountModel == null) {
            return null;
        }
        return accountDTOConverter.convert(accountModel);
    }

    public int deleteAccount(long id) {
        return accountDao.delete(id);
    }
}
