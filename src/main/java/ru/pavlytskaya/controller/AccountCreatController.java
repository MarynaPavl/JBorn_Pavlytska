package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.AccountCreatRequest;
import ru.pavlytskaya.json.AccountCreatResponse;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;

import java.util.List;

@Service("/accountCreat")
@RequiredArgsConstructor
public class AccountCreatController implements SecureController<AccountCreatRequest, AccountCreatResponse> {
    private final AccountService accountService;

    @Override
    public AccountCreatResponse handler(AccountCreatRequest request, Long userId) {
        List<AccountDTO> accountDTO = accountService.accountCreat(request.getNameAccount(), request.getBalance(), request.getCurrency(), userId);
        return new AccountCreatResponse(accountDTO);
    }


    @Override
    public Class<AccountCreatRequest> getRequestClass() {
        return AccountCreatRequest.class;
    }

}
