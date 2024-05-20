package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;

import java.util.NoSuchElementException;

public interface QuizUsersAnswersService extends BaseService<QuizAnswers> {

    String getGenreQuizList();

    void set(QuizAnswers quizAnswers, String login) throws IllegalArgumentException, NoSuchElementException;
    QuizAnswers get(String login);
    void setQuizListByModel(QuizAnswers quizAnswers) throws IllegalArgumentException, NoSuchElementException;
    void setQuizListByIdAndAnswers(Long id, GenreEnums genre, String duration, CountryEnums country) throws IllegalArgumentException, NoSuchElementException;
    QuizAnswers findByUserID(Long id);
    QuizAnswers findByUserLogin(String login);
}
