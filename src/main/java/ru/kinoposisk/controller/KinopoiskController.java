package ru.kinoposisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dao.quiz.QuizDTO;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.service.interfaces.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "kinopoisk/profile/")
@Validated
public class KinopoiskController {

    private final KinopoiskAPIService kinopoiskAPIService;
    private final UsersService usersService;
    private final QuizUsersAnswersService quizUsersAnswersService;
    private final MovieHistoryService movieHistoryService;
    private final FriendsService friendsService;

    @Autowired
    public KinopoiskController(KinopoiskAPIService kinopoiskAPIService,
                               UsersService userRepository1,
                               QuizUsersAnswersService quizUsersAnswersService, MovieHistoryService movieHistoryService, FriendsService friendsService) {
        this.kinopoiskAPIService = kinopoiskAPIService;
        this.usersService = userRepository1;
        this.quizUsersAnswersService = quizUsersAnswersService;
        this.movieHistoryService = movieHistoryService;
        this.friendsService = friendsService;
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

        quizUsersAnswersService.add(quizUsersAnswersService.build(quizDTO, authentication.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
    @GetMapping(path = "movieHistory")
    public ResponseEntity<List<MovieHistory>> getMovieHistory(Authentication authentication) {

        return ResponseEntity.ok().body(movieHistoryService.findByUser(usersService.findByLogin(authentication.getName())));
    }

    @GetMapping(path = "friends")
    public ResponseEntity<List<Friends>> getFriends(Authentication authentication) {

        return ResponseEntity.ok().body(friendsService.findAllFriendsByUsers(usersService.findByLogin(authentication.getName())));
    }

    @GetMapping(path = "friends/friendsRequests")
    public ResponseEntity<List<Friends>> getFriendsRequests(Authentication authentication) {

        return ResponseEntity.ok().body(friendsService.findAllFriendsRequestByUser(usersService.findByLogin(authentication.getName())));
    }

    @GetMapping(path = "friends/{friendLogin}")
    public ResponseEntity<Friends> getFriendInfo(Authentication authentication, @PathVariable String friendLogin) {

        // TODO : Проверить друзья ли они. Если друзья - возвращаем профиль друга.
        // TODO :
        return ResponseEntity.status(HttpStatus.OK).body(friendsService.findUserFriendByLogin(
                authentication.getName(), friendsService.find
        ));
    }

    @PostMapping(path = "friends/{friendLogin}/sendFriendRequest")
    public ResponseEntity<String> sendFriendRequest(Authentication authentication, @PathVariable String friendLogin) {

        usersService.findByLogin(authentication.getName());
        usersService.findByLogin(friendLogin);

    }

    @GetMapping(path = "friends/{friendLogin}/status")
    public ResponseEntity<Friends> getFriendStatus(Authentication authentication, @PathVariable String friendLogin) {

        // TODO get status request by friendLogin
        return ResponseEntity.status(HttpStatus.OK).body(friendsService.findUserFriendByLogin(
                authentication.getName(), friendLogin
        ));
    }


    /*
        TODO : 1) История просмотров фильмов.
        TODO : Прохождение вопросов
        TODO : 3) Добавление пользователей в друзья
        TODO : 4) Получение рекомендаций по фильму с учетом последних 10ти фильмов или опроса
        TODO : 5) Получение рекомендаций по фильму вместе с другом с учетом последних 10ти фильмов или опроса
        TODO : 6) Выставление оценки фильму и комментарий к фильму
        TODO : 7) Просмотр комментариев и оценки к фильму другого пользователя
        TODO : 8) Просмотр оценки фильмов других пользователей
        TODO : 10) Отправка рекомендаций на почту по фильмам раз в месяц
     */
}
