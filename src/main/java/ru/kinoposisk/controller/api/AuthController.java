package ru.kinoposisk.controller.api;

import lombok.SneakyThrows;
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
import ru.kinoposisk.dto.auth.AuthLoginDTO;
import ru.kinoposisk.dto.auth.AuthSignUpDTO;
import ru.kinoposisk.dto.changePass.ChangePassByEmailDTO;
import ru.kinoposisk.dto.changePass.ChangePassByLoginDTO;
import ru.kinoposisk.service.interfaces.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path ="/api/user/auth/")
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
    public ResponseEntity<AuthLoginDTO> loginDao() {
        return ResponseEntity.status(HttpStatus.OK).body(AuthLoginDTO.builder()
                .login("Enter your login")
                .password("Enter your password")
                .build());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> performLogin(@Valid @RequestBody AuthLoginDTO authLoginDTO) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLoginDTO.getLogin(), authLoginDTO.getPassword()));
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

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthSignUpDTO.builder()
                .login("Enter your login")
                .password("Enter your password")
                .email("Enter your email")
                .build().toString());
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Void> singingUP(@Valid @RequestBody AuthSignUpDTO authSignUpDTO
//            , HttpServletResponse response
    )
    {

            authSignUpDTO.setPassword(passwordEncoder.encode(authSignUpDTO.getPassword()));
            usersService.add(authSignUpDTO);
            //            response.sendRedirect("/kinopoisk/api/quiz");

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @SneakyThrows
    @PostMapping(value = "/changePasswordByEmail")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePassByEmailDTO changePassByEmailDTO) {

            usersService.changePassword(changePassByEmailDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed to user with email: " + changePassByEmailDTO.getEmail());
    }
    @SneakyThrows
    @PostMapping(value = "/changePasswordByLogin")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePassByLoginDTO changePassByLoginDTO) {

            usersService.changePassword(changePassByLoginDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed to user with login: " + changePassByLoginDTO.getLogin());
    }

    @SneakyThrows
    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
    }

}
