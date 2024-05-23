package ru.kinoposisk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.MovieHistoryRepository;
import ru.kinoposisk.service.interfaces.MovieHistoryService;
import ru.kinoposisk.service.interfaces.UsersService;

import java.util.List;

@Service
public class MoveHistoryImpl implements MovieHistoryService {

    private final MovieHistoryRepository movieHistoryRepository;
    private final UsersService usersService;


    @Autowired
    public MoveHistoryImpl(MovieHistoryRepository movieHistoryRepository, UsersService usersService) {
        this.movieHistoryRepository = movieHistoryRepository;
        this.usersService = usersService;
    }

    @Override
    public MovieHistory add(MovieHistory movieHistory) {
        return null;
    }

    @Override
    public void deleteByID(Long id) {

    }

    @Override
    public List<MovieHistory> findByUser(Users user) {

        return movieHistoryRepository.findByUser(user);
    }

    @Override
    public List<MovieHistory> getAllMoviesWithRatingByUser(String login) {

        return movieHistoryRepository.findByUser_LoginAndMovie_LocalRatingNotNull(usersService.findByLogin(login).getLogin());
    }

    @Override
    public Integer deleteAllRatingByUser(Users user) {

        return movieHistoryRepository.deleteAllRatingsByUser(user);
    }

    @Override
    public void deleteMovieRatingByUser(Users user, Movies movie) {

        movieHistoryRepository.deleteByUserAndMovie(user, movie);
    }

    @Override
    public MovieHistory findByID(Long id) {
        return null;
    }

    @Override
    public List<MovieHistory> getAll() {
        return null;
    }

}
