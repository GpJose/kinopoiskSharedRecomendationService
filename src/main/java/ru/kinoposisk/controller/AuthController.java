package ru.kinoposisk.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dao.auth.AuthLoginDAO;
import ru.kinoposisk.dao.auth.AuthSignUpDAO;
import ru.kinoposisk.dao.changePass.ChangePassByEmailDAO;
import ru.kinoposisk.dao.changePass.ChangePassByLoginDAO;
import ru.kinoposisk.service.interfaces.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path ="/user/auth/")
@Validated
@Log4j2
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final UsersService usersService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UsersService usersService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    @GetMapping(value = "/login")
    public ResponseEntity<AuthLoginDAO> loginDao() {
        return ResponseEntity.status(HttpStatus.OK).body(AuthLoginDAO.builder()
                .login("Enter your login")
                .password("Enter your password")
                .build());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> performLogin(@Valid @RequestBody AuthLoginDAO authLoginDAO) {

        log.info(authLoginDAO.toString());

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLoginDAO.getLogin(), authLoginDAO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok().build();
        }
        catch (AuthenticationException e ) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping(value = "/signup")
    public ResponseEntity<String> singUP() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthSignUpDAO.builder()
                .login("Enter your login")
                .password("Enter your password")
                .email("Enter your email")
                .build().toString());
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> singingUP(@Valid @RequestBody AuthSignUpDAO authSignUpDAO
//            , HttpServletResponse response
    )
    {

        try {

            authSignUpDAO.setPassword(passwordEncoder.encode(authSignUpDAO.getPassword()));
            usersService.add(authSignUpDAO);
            //            response.sendRedirect("/kinopoisk/api/quiz");

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping(value = "/changePasswordByEmail")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePassByEmailDAO changePassByEmailDAO) {

        try {
            usersService.changePassword(changePassByEmailDAO);

        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed to user with email: " + changePassByEmailDAO.getEmail());
    }
    @PostMapping(value = "/changePasswordByLogin")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePassByLoginDAO changePassByLoginDAO) {

        try {
            usersService.changePassword(changePassByLoginDAO);

        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed to user with login: " + changePassByLoginDAO.getLogin());
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        try {

            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);
            request.getSession().invalidate();

            response.sendRedirect(request.getContextPath() + "/login");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
