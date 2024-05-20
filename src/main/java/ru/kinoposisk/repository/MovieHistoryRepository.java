package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.MovieHistory;

import java.util.List;

@Repository
public interface MovieHistoryRepository extends JpaRepository<MovieHistory, Long> {
    List<MovieHistory> findByUserId(Long id);
}