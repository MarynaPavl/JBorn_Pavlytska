package ru.pavlytskaya.service;

import ru.pavlytskaya.comverter.AccountModelToAccountDTOConverter;
import ru.pavlytskaya.dao.AccountDao;
import ru.pavlytskaya.dao.AccountModel;

import java.util.List;

public class AccountService {
    private final AccountDao accountDao;
    private final AccountModelToAccountDTOConverter accountDTOConverter;

    public AccountService() {
        this.accountDao = new AccountDao();
        this.accountDTOConverter = new AccountModelToAccountDTOConverter();
    }

    public List<AccountDTO> accountInformation(long userID) {
        List<AccountModel> accountModel = accountDao.listOfAccount(userID);
        if (accountModel == null) {
            return null;
        }
        return accountDTOConverter.convert(accountModel);
    }

    public List<AccountDTO> accountCreat(String nameAccount, double balance, String currency, long userID) {
        List<AccountModel> accountModel = accountDao.creatAccount(nameAccount, balance, currency, userID);
        if (accountModel == null) {
            return null;
        }
        return accountDTOConverter.convert(accountModel);
    }

    public int deleteAccount(long id) {
        return accountDao.delete(id);
    }
}
