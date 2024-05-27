package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.dto.quiz.QuizDTO;
import ru.kinoposisk.model.QuizAnswers;

import java.util.List;

public interface QuizUsersAnswersService extends BaseService<QuizAnswers> {

    List<QuizAnswers> findByUserLogin(String login);

    QuizAnswers build(QuizDTO quizDTO, String login);
}
