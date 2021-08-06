package ru.pavlytskaya.api.apiConverter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.pavlytskaya.api.json.AccountCreatResponse;
import ru.pavlytskaya.api.json.AccountInformationResponse;
import ru.pavlytskaya.service.AccountDTO;

import java.util.List;

@Component
public class AccountModelToResponseConverter implements Converter<AccountDTO, AccountCreatResponse> {


    @Override
    public AccountCreatResponse convert(@NotNull AccountDTO account) {
        return new AccountCreatResponse(account);
    }

    public AccountInformationResponse convert(List<AccountDTO> accounts) {
        return new AccountInformationResponse(accounts);
    }
}

