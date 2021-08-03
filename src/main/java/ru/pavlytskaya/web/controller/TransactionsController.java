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
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;
import ru.pavlytskaya.web.form.AccountDeleteForm;
import ru.pavlytskaya.web.form.TransactionCreateForm;
import ru.pavlytskaya.web.form.TransactionInformationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionsController {
    private final TransactionInformationService transactionService;

    @GetMapping("/transaction")
    public String getTransactionInfo(Model model) {
        model.addAttribute("form", new TransactionInformationForm());

        return "transaction";
    }

    @PostMapping("/transaction")
    public String postTypeInfo(@ModelAttribute("form") @Valid TransactionInformationForm form,
                               BindingResult result,
                               Model model,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/index";
        }

        if (!result.hasErrors()) {
            List<TransactionInformationDTO> list = transactionService.informationModels(form.getAssignment(), LocalDate.parse(form.getFromDate()), LocalDate.parse(form.getToData()));
            model.addAttribute("lastSearch", form.getAssignment());
            model.addAttribute("dataFrom", form.getFromDate());
            model.addAttribute("dataTo", form.getToData());
            if (!list.isEmpty()) {
                model.addAttribute("types", list);
                return "transaction";
            }
            result.addError(new FieldError("form", "assignmentId", "Not found"));
        }
        model.addAttribute("form", form);

        return "transaction";
    }

    @GetMapping("/deleteTransaction")
    public String getDeleteTransaction(Model model) {
        model.addAttribute("form", new AccountDeleteForm());

        return "deleteTransaction";
    }

    @PostMapping("/deleteTransaction{id}")
    public String deleteTransaction(@PathVariable(value = "id") Long id,
                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/index";
        }

        transactionService.deleteTransaction(id);

        return "redirect:/transaction";

    }

    @GetMapping("/createTransaction")
    public String getCreateTransaction(Model model){
        model.addAttribute("form", new TransactionCreateForm());

        return "createTransaction";
    }

    @PostMapping("/createTransaction")
    public String postCreateTransaction(
            @ModelAttribute("form")@Valid TransactionCreateForm form,
            BindingResult result,
            Model model){

        if(!result.hasErrors()) {
            String str = form.getData();
            LocalDate date = LocalDate.parse(str);
            TransactionInformationDTO transactionDTO = transactionService.transactionInsert(
                    form.getAccountFrom(), form.getAccountTo(),
                    form.getSum(), date, form.getAssignmentId());

            if (transactionDTO != null) {
                model.addAttribute("successfully", "Transaction was successfully created.");

                return "createTransaction";
            }
            result.addError(new FieldError("form", "sum", "amount must exceed 0.1"));
            result.addError(new FieldError("form", "data", "enter date of the transaction"));
        }
        model.addAttribute("form", form);
        return "createTransaction";
    }

}
