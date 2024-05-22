package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.dao.quiz.QuizDTO;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.repository.QuizRepository;
import ru.kinoposisk.repository.UsersRepository;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UsersService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class QuizUsersAnswersServiceImpl implements QuizUsersAnswersService {

    private final QuizRepository quizRepository;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Autowired
    public QuizUsersAnswersServiceImpl(QuizRepository quizRepository, UsersRepository usersRepository, UsersService usersService) {
        this.quizRepository = quizRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
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


//        Users user = userRepository.findByLogin(login);
//        log.info(">>>setQuizListByIdAndAnswers, quizAnsers : "+quizAnswers.toString());
//        user.getQuizAnswers().add(quizAnswers);
//        log.info(">>>setQuizListByIdAndAnswers, userEntity : " + user.toString());
//
//        userRepository.save(user);
    }

    @Override
    public QuizAnswers get(Long id) {

        return quizRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void setQuizListByModel(QuizAnswers quizAnswers) throws IllegalArgumentException, NoSuchElementException {

    }

    @Override
    public void setQuizListByIdAndAnswers(Long id, GenreEnums genre, String duration, CountryEnums country) throws IllegalArgumentException, NoSuchElementException {

    }

    @Override
    public List<QuizAnswers> findByUserID(Long id) {

        return quizRepository.findByUsers_Id(id).orElseThrow(() -> new UsernameNotFoundException("Answers not found"));
    }

    @Override
    public List<QuizAnswers> findByUserLogin(String login) {

        return quizRepository.findByUsers_Login(login).orElseThrow(() -> new UsernameNotFoundException("Answers not found"));
    }


    @Override
    public QuizAnswers add(QuizAnswers quizAnswers) {

        if (quizAnswers.getUsers() == null || quizAnswers.getGenre() == null || quizAnswers.getDuration() == null || quizAnswers.getCountry() == null) {
            throw new IllegalArgumentException("One of the parameters is null");
        }
        return quizRepository.save(quizAnswers);
    }

    @Override
    public void deleteByID(Long id) {

    }

    @Override
    public QuizAnswers findByID(Long id) {
        return null;
    }

    @Override
    public List<QuizAnswers> getAll() {

        return quizRepository.findAll();
    }

    @Override
    public QuizAnswers build(QuizDTO quizDTO, String login) {

        return  QuizAnswers.builder()
                .users(usersService.findByLogin(login))
                .duration(quizDTO.getDuration())
                .genre(quizDTO.getGenre())
                .country(quizDTO.getCountry())
                .build();
    }
}
