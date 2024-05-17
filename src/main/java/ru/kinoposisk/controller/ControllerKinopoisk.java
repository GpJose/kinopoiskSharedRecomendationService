package ru.kinoposisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dto.quiz.QuizDTO;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/kinopoisk/api/")
@Validated
public class ControllerKinopoisk {
    private final KinopoiskAPIService kinopoiskAPIService;
    private final UserService userService;
    private final QuizUsersAnswersService quizUsers;

    @Autowired
    public ControllerKinopoisk(KinopoiskAPIService kinopoiskAPIService,
                               UserService userRepository1,
                               QuizUsersAnswersService quizUsers) {
        this.kinopoiskAPIService = kinopoiskAPIService;
        this.userService = userRepository1;
        this.quizUsers = quizUsers;
    }

    @GetMapping(path = "sendRequest")
    public ResponseEntity<String> sendRequest(@Valid @RequestBody Users user) {

        kinopoiskAPIService.sendRequest(user);
        return ResponseEntity.ok().body("success");
    }
    @PutMapping(path = "createUser")
    public ResponseEntity<Users> createUser(Users user) {
        return ResponseEntity.ok().body(userService.addUser(user));
    }
    @GetMapping(path = "test")
    public ResponseEntity<String> test() {
        // TODO Создание двух пользователей, прохождение вопросов, получения реузльтатов поиска
        return ResponseEntity.ok().body(quizUsers.getGenreQuizList());
    }

    @GetMapping(path = "quiz")
    // TODO Вопросы по жанру
    public ResponseEntity<String> quiz() {
        QuizDTO quizDTO = QuizDTO.builder()
                .build();
        return ResponseEntity.ok().body();
    }
}
