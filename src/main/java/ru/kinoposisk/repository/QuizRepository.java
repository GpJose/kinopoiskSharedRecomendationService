package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.QuizAnswers;

@Repository
public interface QuizRepository extends JpaRepository<QuizAnswers, Long> {

}