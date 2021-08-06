package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.AccountCreatRequest;
import ru.pavlytskaya.api.json.AccountCreatResponse;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;

@Service("/accountCreat")
@RequiredArgsConstructor
public class AccountCreatController implements SecureController<AccountCreatRequest, AccountCreatResponse> {
    private final AccountService accountService;

    @Override
    public AccountCreatResponse handler(AccountCreatRequest request, Long userId) {
        AccountDTO accountDTO = accountService.accountCreat(request.getNameAccount(), request.getBalance(), request.getCurrency(), userId);
        return new AccountCreatResponse(accountDTO);
    }


    @Override
    public Class<AccountCreatRequest> getRequestClass() {
        return AccountCreatRequest.class;
    }

}
