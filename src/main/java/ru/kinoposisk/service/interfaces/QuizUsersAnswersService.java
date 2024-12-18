package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.dto.quiz.QuizAnswersDTO;
import ru.kinoposisk.model.QuizAnswers;

import java.util.List;

public interface QuizUsersAnswersService extends BaseService<QuizAnswers> {

    List<QuizAnswers> findByUserLogin(String login);

    QuizAnswers build(QuizAnswersDTO quizAnswersDTO, String login);
}
