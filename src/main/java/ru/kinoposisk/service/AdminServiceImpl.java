package ru.kinoposisk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.UsersRepository;
import ru.kinoposisk.service.interfaces.*;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UsersRepository usersRepository;
    private final MovieHistoryService movieHistoryService;
    private final UsersService usersService;
    private final QuizUsersAnswersService quizAnswersService;
    private final MoviesService moviesService;

    @Autowired
    public AdminServiceImpl(UsersRepository usersRepository, MovieHistoryService movieHistoryService, UsersService usersService, QuizUsersAnswersService quizAnswersService, MoviesService moviesService) {
        this.usersRepository = usersRepository;
        this.movieHistoryService = movieHistoryService;
        this.usersService = usersService;
        this.quizAnswersService = quizAnswersService;
        this.moviesService = moviesService;
    }

    @Override
    public List<Users> getAllUsers() {

        return usersRepository.findAll();
    }

    @Override
    public Users getUser(String login) {

        return usersService.findByLogin(login);
    }

    @Override
    public List<MovieHistory> getAllHistoryByUsername(String login) {

        return movieHistoryService.findByUser(usersService.findByLogin(login));
    }
    @Override
    public List<MovieHistory> getAllRatingsByUsername(String login) {

        return movieHistoryService.getAllMoviesWithRatingByUser(login);
    }

    @Override
    public List<QuizAnswers> getAllAnswersByUsername(String login) {

        return quizAnswersService.findByUserLogin(usersService.findByLogin(login).getLogin());
    }

    @Override
    public Boolean blockUser(String login) {

        Users user = usersService.findByLogin(login);

        return user.isActive() ?
                usersRepository.changeActiveStatus(user.getLogin(), false) :
                usersRepository.changeActiveStatus(user.getLogin(), true);
    }

    @Override
    public Integer deleteAllRatingByUser(String login) {

        return movieHistoryService.deleteAllRatingByUser(usersService.findByLogin(login));
    }

    @Override
    public void deleteMovieRatingByUser(String login, Long movieId) {

        movieHistoryService.deleteMovieRatingByUser(usersService.findByLogin(login), moviesService.getMovieById(movieId));
    }
}
