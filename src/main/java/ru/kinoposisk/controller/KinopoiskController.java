package ru.kinoposisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kinoposisk.dao.auth.AuthSignUpDAO;
import ru.kinoposisk.dao.quiz.QuizDTO;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.repository.UserRepository;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "kinopoisk/api/")
@Validated
public class KinopoiskController {

    private final KinopoiskAPIService kinopoiskAPIService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final QuizUsersAnswersService quizUsers;

    @Autowired
    public KinopoiskController(KinopoiskAPIService kinopoiskAPIService,
                               UserService userRepository1,
                               UserRepository userRepository, QuizUsersAnswersService quizUsers) {
        this.kinopoiskAPIService = kinopoiskAPIService;
        this.userService = userRepository1;
        this.userRepository = userRepository;
        this.quizUsers = quizUsers;
    }

    @GetMapping(path = "sendRequest")
    public ResponseEntity<String> sendRequest(@Valid @RequestBody Users user) {

        kinopoiskAPIService.sendRequest(user);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping(path = "test")
    public ResponseEntity<String> test() {

        // TODO Создание двух пользователей, прохождение вопросов, получения реузльтатов поиска

        return ResponseEntity.ok().body(quizUsers.getGenreQuizList());
    }

    @GetMapping(value = "username")
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
    @GetMapping(value = "username2")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


    @GetMapping(path = "quiz")
    public ResponseEntity<QuizDTO> getQuiz() {

        return ResponseEntity.ok().body(QuizDTO.builder()
                .country(CountryEnums.values())
                .duration("60-120")
                .genre(GenreEnums.values())
                .build());
    }
    @PostMapping(path = "quiz")
    public ResponseEntity<String> setQuiz(@Valid @RequestBody QuizDTO quizDTO, Authentication authentication) {

        try {
            String login = authentication.getName();
            Users user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            QuizAnswers quizAnswers = QuizAnswers.builder()
                    .userId(user)
                    .duration(quizDTO.getDuration())
                    .genre(Arrays.toString(quizDTO.getGenre()))
                    .country(Arrays.toString(quizDTO.getCountry()))
                    .build();

            List<QuizAnswers> quizAnswersList = user.getQuizAnswers();
            quizAnswersList.add(quizAnswers);
            user.setQuizAnswers(quizAnswersList);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        catch (UsernameNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
