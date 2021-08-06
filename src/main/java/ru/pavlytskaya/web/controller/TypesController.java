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
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;
import ru.pavlytskaya.web.form.TypeCreateForm;
import ru.pavlytskaya.web.form.TypeDeleteForm;
import ru.pavlytskaya.web.form.TypeInformationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TypesController {
    private final TypeService typeService;

    @GetMapping("/type")
    public String getTypeInfo(Model model) {
        model.addAttribute("form", new TypeInformationForm());

        return "type";
    }

    @PostMapping("/type")
    public String postTypeInfo(@ModelAttribute("form") @Valid TypeInformationForm form,
                               BindingResult result,
                               Model model,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/index";
        }

        if (!result.hasErrors()) {
            List<TypeDTO> type = typeService.typeInformation(form.getString());
            model.addAttribute("lastSearch", form.getString());
            if (!type.isEmpty()) {
                model.addAttribute("types", type);
                return "type";
            }
            result.addError(new FieldError("form", "string", "Not found"));
        }
        model.addAttribute("form", form);

        return "type";
    }

    @GetMapping("/deleteType")
    public String getDeleteType(Model model) {
        model.addAttribute("form", new TypeDeleteForm());

        return "deleteType";
    }

    @PostMapping("/deleteType{id}")
    public String deleteType(@PathVariable(value = "id") Long id,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/index";
        }
        typeService.delete(id);

        return "redirect:/type";

    }

    @GetMapping("/createType")
    public String getCreateType(Model model) {
        model.addAttribute("form", new TypeCreateForm());

        return "createType";
    }


    @PostMapping("/createType")
    public String postCreateType(@ModelAttribute("form") @Valid TypeCreateForm form,
                                 BindingResult result,
                                 Model model,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/index";
        }

        if (!result.hasErrors()) {

            TypeDTO typeCreat = typeService.typeCreat(form.getAssignment());
            if (typeCreat != null) {

                return "redirect:/type";
            }
            result.addError(new FieldError("form", "assignment", "account was not created, this field cannot be empty"));

        }
        model.addAttribute("form", form);

        return "type";
    }
}