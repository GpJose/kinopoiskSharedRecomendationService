package ru.kinoposisk.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.dto.response.Movie;
import ru.kinoposisk.dto.response.MovieResultResponseDTO;
import ru.kinoposisk.exception.moviesExceptions.MovieNotFoundByIdException;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.repository.MoviesRepository;
import ru.kinoposisk.service.interfaces.MoviesService;

import java.util.List;
import java.util.Map;

@Service
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;

    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Movies add(Movies movies) {

        if (checkMovieIsExist(movies)) {
            return moviesRepository.save(movies);
        }
        else {
            throw new DuplicateKeyException("Movie with id: " + movies.getKinopoiskId() + " already exist");
        }
    }

    @Override
    public void deleteByID(Long id) {

        moviesRepository.delete(findByID(id));
    }

    @Override
    public Movies findByID(Long id) {

        return moviesRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));
    }

    @Override
    public Movies findByKpID(Long id) {

        return moviesRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));
    }

    @Override
    public List<Movies> findByGenre(String genre) {

        genre = genre.replaceAll("_"," ");
        return moviesRepository.findByGenresContains(genre);

    }

    @Override
    public Movies findByCountry(String country) {
        // TODO findByCountry
        return null;
    }

    @Override
    public Movies findByDurationBetween(String duration) {
        // TODO findByDuration
        return null;
    }

    @Override
    public List<Movies> getAll() {

        return moviesRepository.findAll();
    }

    @Override
    public void updateAvgMovieRating(Movies movies) {

        Movies moviesTemp = findByID(movies.getId());
        moviesTemp.setLocalRating(moviesRepository.getAverageRatingForMovie(moviesTemp.getId()));
        moviesRepository.save(moviesTemp);
    }
    public void movieBuilder(MovieResultResponseDTO movieResultResponseDTO) {
        // TODO movieResponseBuilder
        for (Movie movie : movieResultResponseDTO.getMovies()) {
            if (checkMovieIsExist(movie.getId())) {

            }

        }
    }

    private boolean checkMovieIsExist(Long id) {

        return !moviesRepository.findByKinopoiskId(id).isPresent();
    }

    private boolean checkMovieIsExist(Movies movies) {

        return checkMovieIsExist(movies.getKinopoiskId());
    }
}
