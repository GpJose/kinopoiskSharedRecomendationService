package ru.kinoposisk.controller.api;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dto.profile.UsersDTO;
import ru.kinoposisk.dto.quiz.QuizAnswersDTO;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;
import ru.kinoposisk.service.interfaces.FriendsService;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UsersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/kinopoisk/")
@Validated
public class KinopoiskController {

    private final KinopoiskAPIService kinopoiskAPIService;
    private final UsersService usersService;
    private final QuizUsersAnswersService quizUsersAnswersService;
    private final FriendsService friendsService;

    @Autowired
    public KinopoiskController(KinopoiskAPIService kinopoiskAPIService,
                               UsersService usersService,
                               QuizUsersAnswersService quizUsersAnswersService,
                               FriendsService friendsService) {

        this.kinopoiskAPIService = kinopoiskAPIService;
        this.usersService = usersService;
        this.quizUsersAnswersService = quizUsersAnswersService;
        this.friendsService = friendsService;
    }

    // Профиль пользователя
    @GetMapping(path = "profile")
    public ResponseEntity<UsersDTO> getProfile(Authentication authentication) {

        return ResponseEntity.ok().body(usersService.getProfile(usersService.findByLogin(authentication.getName())));
    }

    // Отправка запроса в кинопоиск
    @GetMapping(path = "sendRequest")
    public ResponseEntity<String> sendRequest(@Valid @RequestBody Authentication authentication) {

        kinopoiskAPIService.sendRequest(usersService.findByLogin(authentication.getName()));

        return ResponseEntity.ok().body("Success");
    }

    // TODO совместное просмотр фильтма

    // Получение всех ответов на вопросы
    @GetMapping(path = "quiz")
    public ResponseEntity<List<QuizAnswers>> getQuiz(Authentication authentication) {

        return ResponseEntity.ok().body(quizUsersAnswersService.findByUserLogin(authentication.getName()));
    }

    // Отправка ответов на вопросы
    @PostMapping(path = "quiz")
    public ResponseEntity<String> setQuiz(@Valid @RequestBody QuizAnswersDTO quizAnswersDTO, Authentication authentication) {

        quizUsersAnswersService.add(quizUsersAnswersService.build(quizAnswersDTO, authentication.getName()));

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    // Просмотр запросов в друзья пользователя
    @GetMapping(path = "friends/friendsRequests")
    public ResponseEntity<List<Friends>> getFriendsRequests(Authentication authentication) {

        return ResponseEntity.ok().body(friendsService.getAllFriendsRequestByUser(usersService.findByLogin(authentication.getName())));
    }

    @PostMapping(path = "friends/friendsRequests/{friendLogin}")
    public ResponseEntity<String> getFriendsRequests(Authentication authentication, @PathVariable String friendLogin) {

        friendsService.add(usersService.findByLogin(authentication.getName()),usersService.findByLogin(friendLogin));

        return ResponseEntity.ok().body("Success");
    }

    // Просмотр статуса заявки в друзья
    @GetMapping(path = "friends/{friendLogin}/status")
    public ResponseEntity<FriendsRequestStatusEnum> getFriendStatus(Authentication authentication, @PathVariable String friendLogin) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        friendsService.findFriendRequestStatus(
                                usersService.findByLogin(authentication.getName()),
                                usersService.findByLogin(friendLogin)
                        ));
    }

    // Отправка запроса в друзья
    @PostMapping(path = "friends/{friendLogin}/sendFriendRequest")
    public ResponseEntity<String> sendFriendRequest(Authentication authentication, @PathVariable String friendLogin) {

        friendsService.sendFriendRequest(
                usersService.findByLogin(authentication.getName()),
                usersService.findByLogin(friendLogin));

        return  ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    // Получение профиля друга
    @SneakyThrows
    @GetMapping(path = "friends/{friendLogin}")
    public ResponseEntity<UsersDTO> getFriendProfile(Authentication authentication, @PathVariable String friendLogin) {

        return ResponseEntity.status(HttpStatus.OK).body(friendsService.getFriendProfile(
                usersService.findByLogin(authentication.getName()),
                usersService.findByLogin(friendLogin)));
    }

    @DeleteMapping(path = "friends/{friendLogin}")
    public ResponseEntity<String> removeFriend(Authentication authentication, @PathVariable String friendLogin) {

        friendsService.removeFriend(
                usersService.findByLogin(authentication.getName()),
                usersService.findByLogin(friendLogin));

        return ResponseEntity.status(HttpStatus.OK).body("Has been deleted");
    }


    /*
        TODO : 4) Получение рекомендаций по фильму с учетом последних 10ти фильмов или опроса
        TODO : 5) Получение рекомендаций по фильму вместе с другом с учетом последних 10ти фильмов или опроса
        TODO : 6) Выставление оценки фильму и комментарий к фильму
        TODO : 10) Отправка рекомендаций на почту по фильмам раз в месяц
     */
}
