package ru.pavlytskaya.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.json.AccountDeleteRequest;
import ru.pavlytskaya.json.AccountDeleteResponse;
import ru.pavlytskaya.service.AccountService;

@Service("/accountDelete")
@RequiredArgsConstructor
public class AccountDeleteController implements SecureController<AccountDeleteRequest, AccountDeleteResponse> {
    private final AccountService accountService;

    @Override
    public AccountDeleteResponse handler(AccountDeleteRequest request, Long userId) {
        int row = accountService.deleteAccount(request.getAccountId());
        if (row == 1) {
            return new AccountDeleteResponse("Operation was successfully completed.");
        }
        if (row == 0) {
            return new AccountDeleteResponse("Mistake!");
        }
        return null;
    }

    @Override
    public Class<AccountDeleteRequest> getRequestClass() {
        return AccountDeleteRequest.class;
    }
}
