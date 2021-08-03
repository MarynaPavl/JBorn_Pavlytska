package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.AccountDeleteRequest;
import ru.pavlytskaya.api.json.AccountDeleteResponse;
import ru.pavlytskaya.service.AccountService;

@Service("/accountDelete")
@RequiredArgsConstructor
public class AccountDeleteController implements SecureController<AccountDeleteRequest, AccountDeleteResponse> {
    private final AccountService accountService;

    @Override
    public AccountDeleteResponse handler(AccountDeleteRequest request, Long userId) {
        accountService.deleteAccount(request.getAccountId());
        return null;
    }

    @Override
    public Class<AccountDeleteRequest> getRequestClass() {
        return AccountDeleteRequest.class;
    }
}
