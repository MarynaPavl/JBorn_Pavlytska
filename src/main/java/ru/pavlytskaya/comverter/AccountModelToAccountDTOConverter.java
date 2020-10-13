package ru.pavlytskaya.comverter;

import ru.pavlytskaya.dao.AccountModel;
import ru.pavlytskaya.service.AccountDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AccountDTO> convert(List<AccountModel> source) {

        Converter<AccountModel, AccountDTO> convert = a -> new AccountDTO();

        return source.stream()
                .map(convert::convert)
                .collect(Collectors.toList());
    }
}
