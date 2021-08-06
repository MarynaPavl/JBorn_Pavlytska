package ru.pavlytskaya.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;
import ru.pavlytskaya.web.form.AccountCreateForm;
import ru.pavlytskaya.web.form.AccountDeleteForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountsController {
    private final AccountService accountService;

    @GetMapping("/accountInfo")
    public String accountInfo(Model model,
                              HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }
        List<AccountDTO> accounts = accountService.accountInformation(userId);
        model.addAttribute("items", accounts);

        return "accountInfo";
    }

    @GetMapping("/delete")
    public String getDeleteAccount(Model model) {
        model.addAttribute("form", new AccountDeleteForm());

        return "delete";
    }

    @PostMapping("/delete{id}")
    public String deleteAccount(@PathVariable(value = "id") Long id,
                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/index";
        }

        accountService.deleteAccount(id);

        return "redirect:/accountInfo";

    }

    @GetMapping("/create")
    public String getCreateAccount(Model model) {
        model.addAttribute("form", new AccountCreateForm());

        return "create";
    }


    @PostMapping("/create")
    public String postCreateAccount(@ModelAttribute("form") @Valid AccountCreateForm form,
                                    BindingResult result,
                                    Model model,
                                    HttpServletRequest request) {

        if (!result.hasErrors()) {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");

            if (userId != null) {
                accountService.accountCreat(form.getNameAccount(), form.getBalance(), form.getCurrency(), userId);

                return "redirect:/accountInfo";
            }
            result.addError(new FieldError("form", "balance", "account was not created, this field cannot be empty"));
            result.addError(new FieldError("form", "currency", "account was not created, this field cannot be empty"));

        }
        model.addAttribute("form", form);

        return "accountInfo";
    }
}
