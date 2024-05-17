package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;

import java.util.NoSuchElementException;

public interface QuizUsersAnswersService {
    String getGenreQuizList();
    void setQuizListByModel(QuizAnswers quizAnswers) throws IllegalArgumentException, NoSuchElementException;
    void setQuizListByIdAndAnswers(Long id, GenreEnums genre, Integer duration, CountryEnums country) throws IllegalArgumentException, NoSuchElementException;
}
