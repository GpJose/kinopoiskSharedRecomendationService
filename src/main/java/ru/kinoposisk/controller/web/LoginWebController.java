package ru.kinoposisk.controller.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kinoposisk.dto.auth.AuthLoginDTO;

import javax.validation.Valid;

@Log4j2
@Controller
public class LoginWebController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginWebController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authLoginDTO", new AuthLoginDTO());
        return "login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("authLoginDTO") AuthLoginDTO authLoginDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLoginDTO.getLogin(), authLoginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (LockedException e) {
            model.addAttribute("userBlocked", e.getMessage());
            return "login";
        }
        catch (Exception e) {

            model.addAttribute("exception", e.getMessage());
            log.error(e.getCause());
            e.printStackTrace();

            return "login";
        }
        return "redirect:/";
    }
}
