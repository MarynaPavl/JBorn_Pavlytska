package ru.pavlytskaya.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;
import ru.pavlytskaya.web.form.AccountDeleteForm;
import ru.pavlytskaya.web.form.TransactionCreateForm;
import ru.pavlytskaya.web.form.TransactionInformationForm;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class TransactionsController extends UserController{
    private final TransactionInformationService transactionService;

    public TransactionsController(UserModelRepository userModelRepository, TransactionInformationService transactionService) {
        super(userModelRepository);
        this.transactionService = transactionService;
    }


    @GetMapping("/transaction")
    public String getTransactionInfo(Model model) {
        model.addAttribute("form", new TransactionInformationForm());

        return "transaction";
    }

    @PostMapping("/transaction")
    public String postTypeInfo(@ModelAttribute("form") @Valid TransactionInformationForm form,
                               BindingResult result,
                               Model model) {
        UserModel user = currentUser();
        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/personal-area";
        }

        if (!result.hasErrors()) {
            List<TransactionInformationDTO> list = transactionService.informationModels(form.getAssignment(), LocalDate.parse(form.getFromDate()), LocalDate.parse(form.getToData()));
            model.addAttribute("lastSearch", form.getAssignment());
            model.addAttribute("dataFrom", form.getFromDate());
            model.addAttribute("dataTo", form.getToData());
            if (list != null) {
                model.addAttribute("types", list);
                return "transaction";
            }
            result.addError(new FieldError("form", "assignmentId", "Not found"));
        }
        model.addAttribute("types", null);

        return "transaction";
    }

    @GetMapping("/deleteTransaction")
    public String getDeleteTransaction(Model model) {
        model.addAttribute("form", new AccountDeleteForm());

        return "deleteTransaction";
    }

    @PostMapping("/deleteTransaction{id}")
    public String deleteTransaction(@PathVariable(value = "id") Long id) {
        UserModel user = currentUser();
        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/personal-area";
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
