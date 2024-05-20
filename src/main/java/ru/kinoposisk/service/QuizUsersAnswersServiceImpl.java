package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.repository.QuizRepository;
import ru.kinoposisk.repository.UserRepository;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class QuizUsersAnswersServiceImpl implements QuizUsersAnswersService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public QuizUsersAnswersServiceImpl(QuizRepository quizRepository, UserRepository userRepository, UserService userService) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public String getGenreQuizList() {
        return Arrays.toString(GenreEnums.values());
    }

    @Override
    public void set(QuizAnswers quizAnswers, String login) throws IllegalArgumentException, NoSuchElementException {

        if (login == null || quizAnswers.getGenre() == null || quizAnswers.getDuration() == null || quizAnswers.getCountry() == null) {
            throw new IllegalArgumentException("One of the parameters is null");
        }
//
//        Users user = userRepository.findByLogin(login);
//        log.info(">>>setQuizListByIdAndAnswers, quizAnsers : "+quizAnswers.toString());
//        user.getQuizAnswers().add(quizAnswers);
//        log.info(">>>setQuizListByIdAndAnswers, userEntity : " + user.toString());
//
//        userRepository.save(user);
    }
    @Override
    public QuizAnswers get(String login) {

        return null;
    }

    @Override
    public void setQuizListByModel(QuizAnswers quizAnswers) throws IllegalArgumentException, NoSuchElementException {

    }

    @Override
    public void setQuizListByIdAndAnswers(Long id, GenreEnums genre, String duration, CountryEnums country) throws IllegalArgumentException, NoSuchElementException {

    }

    @Override
    public QuizAnswers findByUserID(Long id) {
        return null;
    }

    @Override
    public QuizAnswers findByUserLogin(String login) {
        return null;
    }


    @Override
    public QuizAnswers add(QuizAnswers quizAnswers) {
        return null;
    }

    @Override
    public void deleteByID(Long id) {

    }

    @Override
    public QuizAnswers findByID(Long id) {
        return null;
    }

    public List<QuizAnswers> getAll() {
        List<QuizAnswers> quizAnswersList = new ArrayList<>();
//        quizAnswersList
        return null;
    }
}
