package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;

import java.util.List;

public interface AdminService {
    List<Users> getAllUsers();

    Users getUser(String login);

    List<MovieHistory> getAllHistoryByUsername(String login);

    List<MovieHistory> getAllRatingsByUsername(String login);

    List<QuizAnswers> getAllAnswersByUsername(String login);

    Boolean blockUser(String login);

    Integer deleteAllRatingByUser(String login);

    void deleteMovieRatingByUser(String login, Long movieId);
}
