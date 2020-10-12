package ru.pavlytskaya.comverter;

import ru.pavlytskaya.dao.AccountModel;
import ru.pavlytskaya.service.AccountDTO;

import java.util.ArrayList;
import java.util.List;

public class AccountModelToAccountDTOConverter implements Converter<AccountModel, AccountDTO> {

    @Override
    public AccountDTO convert(AccountModel source) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(source.getId());
        accountDTO.setNameAccount(source.getNameAccount());
        accountDTO.setBalance(source.getBalance());
        accountDTO.setCurrency(source.getCurrency());
        accountDTO.setUserID(source.getUserID());
        return accountDTO;
    }

    public List<AccountDTO> convert(List<AccountModel> sourse) {
        List<AccountDTO> accountModelList = new ArrayList<>();
        for (AccountModel model : sourse) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(model.getId());
            accountDTO.setNameAccount(model.getNameAccount());
            accountDTO.setBalance(model.getBalance());
            accountDTO.setCurrency(model.getCurrency());
            accountDTO.setUserID(model.getUserID());
            accountModelList.add(accountDTO);
        }

        return accountModelList;
    }
}
