package ru.kinoposisk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dto.auth.AuthSignUpDTO;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping(path ="/user/auth/")
@Validated
public class ControllerUserAuth {

    @PostMapping(value = "/login")
    public String login() {

        return "login";
    }

    @GetMapping(value = "/login")
    public String loginGet() {

        return "login";
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<AuthSignUpDTO> singingUP(@Valid @RequestBody AuthSignUpDTO authSignUpDTO) throws ConstraintViolationException {

        return ResponseEntity.status(HttpStatus.CREATED).body(authSignUpDTO);
    }

    @PostMapping(value = "/logout")
    public String logout() {

        return "logout";
    }

    @GetMapping(value = "/signup")
    public ResponseEntity<String> singUP() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthSignUpDTO.builder()
                .login("Enter your login")
                .password("Enter your password")
                .email("Enter your email")
                .build().toString());
    }
}
