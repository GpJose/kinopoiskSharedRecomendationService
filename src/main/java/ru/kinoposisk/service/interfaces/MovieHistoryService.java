package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.model.Users;

import java.util.List;

public interface MovieHistoryService  extends BaseService<MovieHistory> {

    List<MovieHistory> findByLogin(String login);

    List<MovieHistory> getAllMoviesWithRatingByUser(String login);

    Integer deleteAllRatingByUser(Users user);

    void deleteMovieRatingByUser(Users user, Movies movie);
}
