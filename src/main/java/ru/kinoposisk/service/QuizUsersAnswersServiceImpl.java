package ru.kinoposisk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kinoposisk.model.QuizAnswers;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.repository.QuizRepository;
import ru.kinoposisk.repository.UserRepository;
import ru.kinoposisk.service.interfaces.QuizUsersAnswersService;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
public class QuizUsersAnswersServiceImpl implements QuizUsersAnswersService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizUsersAnswersServiceImpl(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String getGenreQuizList() {
        return Arrays.toString(GenreEnums.values());
    }

    @Override
    public void setQuizListByModel(QuizAnswers quizAnswers) throws IllegalArgumentException, NoSuchElementException {
        setQuizListByIdAndAnswers(quizAnswers.getId(), quizAnswers.getGenre(), quizAnswers.getDuration(),quizAnswers.getCountry());
    }

    public void setQuizListByIdAndAnswers(Long id, GenreEnums genre, Integer duration, CountryEnums country) throws IllegalArgumentException, NoSuchElementException {

        if (id == null || genre == null || duration == null || country == null) {
            throw new IllegalArgumentException("One of the parameters is null");
        }

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));

        QuizAnswers quizAnswers = QuizAnswers.builder()
                .userId(user)
                .genre(genre)
                .country(country)
                .duration(duration)
                .build();


        user.getQuizAnswers().add(quizAnswers);
        userRepository.save(user);
    }


}
