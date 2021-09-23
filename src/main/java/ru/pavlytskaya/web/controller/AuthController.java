package ru.pavlytskaya.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pavlytskaya.entity.UserModel;
import ru.pavlytskaya.repository.UserModelRepository;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.web.form.RegistrationForm;

import javax.validation.Valid;

@Controller
public class AuthController extends UserController {
    protected UserModelRepository userModelRepository;
    private final AuthService authService;

    public AuthController(UserModelRepository userModelRepository, AuthService authService) {
        super(userModelRepository);
        this.authService = authService;
    }


    @GetMapping("/personal-area")
    public String index(Model model) {
        UserModel user = currentUser();

        model.addAttribute("name", user.getFirstName())
                .addAttribute("surname", user.getLastName())
                .addAttribute("userId", user.getId());

        return "personal-area";
    }

    @GetMapping("/login-form")
    public String getLogin() {

        return "login-form";
    }


    @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("form", new RegistrationForm());

        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("form") @Valid RegistrationForm form,
                            BindingResult result,
                            Model model) throws Exception {
        if (!result.hasErrors()) {
            try {
            authService.registration(
                    form.getFirstName(),
                    form.getLastName(),
                    form.getEmail(),
                    form.getPassword());


                return "redirect:/personal-area";

            }catch (UnexpectedRollbackException e){
                model.addAttribute("user", form.getEmail());
                result.addError(new FieldError("form", "email", "Email " + form.getEmail().concat(" already in use")));
                return "registration";
            }

        }
        model.addAttribute("form", form);

        return "registration";
    }
}