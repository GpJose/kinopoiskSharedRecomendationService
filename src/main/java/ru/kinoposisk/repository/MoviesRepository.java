package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.Movies;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

}