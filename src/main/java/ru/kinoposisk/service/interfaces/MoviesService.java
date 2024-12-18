package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.Movies;

import java.util.List;

public interface MoviesService extends BaseService<Movies> {
    Movies findByKpID(Long id);
    List<Movies> findByGenre(String genre);
    Movies findByDurationBetween(String duration);
    Movies findByCountry(String country);

    Movies getMovieById(Long id);

    List<Long> findAllKpIds();

    void saveAllNewMovies(List<Movies> movies);
}
