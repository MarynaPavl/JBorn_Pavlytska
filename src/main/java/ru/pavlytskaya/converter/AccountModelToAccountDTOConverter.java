package ru.pavlytskaya.converter;

import org.springframework.stereotype.Service;
import ru.pavlytskaya.entity.AccountModel;
import ru.pavlytskaya.service.AccountDTO;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountModelToAccountDTOConverter implements Converter<AccountModel, AccountDTO> {

    @Override
    public AccountDTO convert(AccountModel source) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(source.getId());
        accountDTO.setNameAccount(source.getNameAccount());
        accountDTO.setBalance(source.getBalance());
        accountDTO.setCurrency(source.getCurrency());

        return accountDTO;
    }

    public List<AccountDTO> convert(List<AccountModel> source) {

        return source.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
