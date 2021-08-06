package ru.pavlytskaya.web.controller;

import lombok.RequiredArgsConstructor;
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
import ru.pavlytskaya.service.UserDTO;
import ru.pavlytskaya.web.form.LoginForm;
import ru.pavlytskaya.web.form.RegistrationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserModelRepository userModelRepository;
    private final AuthService authService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        UserModel user = userModelRepository.getOne(userId);
        if (user == null) {
            session.removeAttribute("userId");

            return "redirect:/login";
        }
        model.addAttribute("name", user.getFirstName())
                .addAttribute("surname", user.getLastName())
                .addAttribute("userId", user.getId());

        return "index";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {

        model.addAttribute("form", new LoginForm());

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("form") @Valid LoginForm form,
                            BindingResult result,
                            Model model,
                            HttpServletRequest request) {

        if (!result.hasErrors()) {
            UserDTO user = authService.auth(
                    form.getEmail(),
                    form.getPassword());
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());

                return "redirect:/";
            }
            result.addError(new FieldError("form", "email", "Unsuccessful login attempt. Enter the correct username and password."));
        }
        model.addAttribute("form", form);

        return "login";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("form", new RegistrationForm());

        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("form") @Valid RegistrationForm form,
                            BindingResult result,
                            Model model,
                            HttpServletRequest request) throws Exception {
        if (!result.hasErrors()) {
            try {
            UserDTO user = authService.registration(
                    form.getFirstName(),
                    form.getLastName(),
                    form.getEmail(),
                    form.getPassword());

                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());

                return "redirect:/";

            }catch (UnexpectedRollbackException e){
                model.addAttribute("user", userModelRepository.findByEmail(form.getEmail()));
                result.addError(new FieldError("form", "email", "Email " + form.getEmail().concat(" already in use")));
                return "registration";
            }

        }
        model.addAttribute("form", form);

        return "registration";
    }
}