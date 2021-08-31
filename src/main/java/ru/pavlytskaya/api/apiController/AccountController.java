package ru.pavlytskaya.api.apiController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pavlytskaya.api.apiConverter.AccountModelToResponseConverter;
import ru.pavlytskaya.api.json.AccountCreatRequest;
import ru.pavlytskaya.api.json.AccountCreatResponse;
import ru.pavlytskaya.api.json.AccountDeleteRequest;
import ru.pavlytskaya.api.json.AccountInformationResponse;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;
import ru.pavlytskaya.web.controller.UserController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;


@RestController
@RequestMapping("/api")
public class AccountController extends UserController {
    private final AccountService accountService;
    private final AccountModelToResponseConverter converter;

    public AccountController(UserModelRepository userModelRepository, AccountService accountService, AccountModelToResponseConverter converter) {
        super(userModelRepository);
        this.accountService = accountService;
        this.converter = converter;
    }

    @GetMapping("/get-account-info")
    public @ResponseBody
    ResponseEntity<AccountInformationResponse> getAccountInfo() {
        UserModel user = currentUser();
        Long userId = user.getId();
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
    ResponseEntity<AccountCreatResponse> createAccount(@RequestBody @Valid AccountCreatRequest request) {
        UserModel user = currentUser();
        Long userId = user.getId();
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
