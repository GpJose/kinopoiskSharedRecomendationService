package ru.kinoposisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dao.quiz.QuizDTO;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;
import ru.kinoposisk.service.interfaces.MovieHistoryService;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UsersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "kinopoisk/api/")
@Validated
public class KinopoiskController {

    private final KinopoiskAPIService kinopoiskAPIService;
    private final UsersService usersService;
    private final QuizUsersAnswersService quizUsersAnswersService;
    private final MovieHistoryService movieHistoryService;

    @Autowired
    public KinopoiskController(KinopoiskAPIService kinopoiskAPIService,
                               UsersService userRepository1,
                               QuizUsersAnswersService quizUsersAnswersService, MovieHistoryService movieHistoryService) {
        this.kinopoiskAPIService = kinopoiskAPIService;
        this.usersService = userRepository1;
        this.quizUsersAnswersService = quizUsersAnswersService;
        this.movieHistoryService = movieHistoryService;
    }

    @GetMapping(path = "sendRequest")
    public ResponseEntity<String> sendRequest(@Valid @RequestBody Authentication authentication) {

        kinopoiskAPIService.sendRequest(usersService.findByLogin(authentication.getName()));
        return ResponseEntity.ok().body("success");
    }

    @GetMapping(path = "quiz")
    public ResponseEntity<List<QuizAnswers>> getQuiz(Authentication authentication) {

        return ResponseEntity.ok().body(quizUsersAnswersService.findByUserLogin(authentication.getName()));
    }
    @PostMapping(path = "quiz")
    public ResponseEntity<String> setQuiz(@Valid @RequestBody QuizDTO quizDTO, Authentication authentication) {

        try {

            quizUsersAnswersService.add(quizUsersAnswersService.build(quizDTO, authentication.getName()));

            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        catch (UsernameNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping(path = "MovieHistory")
    public ResponseEntity<List<MovieHistory>> getMovieHistory(@PathVariable Authentication authentication) {

        return ResponseEntity.ok().body(movieHistoryService.get(usersService.findByLogin(authentication.getName())));
    }

    /*
        TODO : 1) История просмотров фильмов.
        TODO : Прохождение вопросов
        TODO : 2) Список всех пользвателей
        TODO : 3) Добавление пользователей в друзья
        TODO : 4) Получение рекомендаций по фильму с учетом последних 10ти фильмов или опроса
        TODO : 5) Получение рекомендаций по фильму вместе с другом с учетом последних 10ти фильмов или опроса
        TODO : 6) Выставление оценки фильму и комментарий к фильму
        TODO : 7) Просмотр комментариев и оценки к фильму другого пользователя
        TODO : 8) Просмотр оценки фильмов других пользователей
        TODO : 10) Отправка рекомендаций на почту по фильмам раз в месяц
     */
}
