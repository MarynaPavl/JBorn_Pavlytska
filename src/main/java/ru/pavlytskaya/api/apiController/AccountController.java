package ru.pavlytskaya.api.apiController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pavlytskaya.api.json.AccountCreatRequest;
import ru.pavlytskaya.api.json.AccountCreatResponse;
import ru.pavlytskaya.api.json.AccountDeleteRequest;
import ru.pavlytskaya.api.json.AccountInformationResponse;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;
import ru.pavlytskaya.api.apiConverter.AccountModelToResponseConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;
    private final AccountModelToResponseConverter converter;

    @GetMapping("/get-account-info")
    public @ResponseBody
    ResponseEntity<AccountInformationResponse> getAccountInfo(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        List<AccountDTO> accounts = accountService.accountInformation(userId);
        if (accounts == null) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        return ok(converter.convert(accounts));
    }

    @PostMapping("/account-create")
    public @ResponseBody
    ResponseEntity<AccountCreatResponse> createAccount(@RequestBody @Valid AccountCreatRequest request,
                                                       HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        AccountDTO accountDTO = accountService.accountCreat(
                request.getNameAccount(),
                request.getBalance(),
                request.getCurrency(),
                userId);
        if (accountDTO == null) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
        return ok(converter.convert(accountDTO));
    }

    @DeleteMapping("/account-delete")
    public @ResponseBody
    ResponseEntity<String> deleteAccount(@RequestBody @Valid AccountDeleteRequest request) {
        int deleteAccount = accountService.deleteAccount(request.getAccountId());
        if (deleteAccount == 0) {
            return status(HttpStatus.NOT_FOUND).build();
        }
        return ok("successfully");
    }
}
