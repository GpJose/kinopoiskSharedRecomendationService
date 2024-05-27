package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.dto.quiz.QuizDTO;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.repository.QuizRepository;
import ru.kinoposisk.repository.UsersRepository;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;
import ru.kinoposisk.service.interfaces.UsersService;

import java.util.List;

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
