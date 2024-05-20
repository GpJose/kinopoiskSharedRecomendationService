package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.Movies;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {
    Optional<Movies> findByKinopoiskId(Long kinopoiskId);

    @Query("SELECT AVG(mh.rating) FROM MovieHistory mh WHERE mh.movie.id = :movieId")
    double getAverageRatingForMovie(@Param("movieId") Long movieId);
    List<Movies> findByGenresContains(String genres);



}