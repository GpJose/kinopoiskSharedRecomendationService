package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.QuizAnswers;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<QuizAnswers, Long> {
    Optional<List<QuizAnswers>> findByUsers_Id(Long id);
    Optional<List<QuizAnswers>> findByUsers_Login(String login);

}