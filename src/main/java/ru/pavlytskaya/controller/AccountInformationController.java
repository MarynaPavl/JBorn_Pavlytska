package ru.pavlytskaya.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pavlytskaya.api.json.AccountInformationRequest;
import ru.pavlytskaya.api.json.AccountInformationResponse;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;

import java.util.List;

@Service("/accountInformation")
@RequiredArgsConstructor
public class AccountInformationController implements SecureController<AccountInformationRequest, AccountInformationResponse> {
    private final AccountService accountService;

    @Override
    public AccountInformationResponse handler(AccountInformationRequest request, Long userId) {
        List<AccountDTO> accountDTO = accountService.accountInformation(userId);
        return new AccountInformationResponse(accountDTO);

    }

    @Override
    public Class<AccountInformationRequest> getRequestClass() {
        return AccountInformationRequest.class;
    }
}
