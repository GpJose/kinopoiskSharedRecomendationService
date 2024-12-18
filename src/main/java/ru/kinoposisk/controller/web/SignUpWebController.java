package ru.kinoposisk.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kinoposisk.dto.auth.AuthSignUpDTO;
import ru.kinoposisk.service.interfaces.UsersService;

import javax.validation.Valid;

@Controller
public class SignUpWebController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpWebController(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("authSignUpDTO", new AuthSignUpDTO());
        return "signup";
    }


    @PostMapping("/signup")
    public String addUser(@Valid @ModelAttribute("authSignUpDTO") AuthSignUpDTO signUpForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
            usersService.add(signUpForm);
        }
        catch (DuplicateKeyException e) {
            model.addAttribute("userAlreadyExist", e.getMessage());
            return "signup";
        }
        return "redirect:/quiz";
    }
}
