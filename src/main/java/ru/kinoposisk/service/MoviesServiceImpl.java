package ru.kinoposisk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.exception.moviesExceptions.MovieNotFoundByIdException;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.repository.MoviesRepository;
import ru.kinoposisk.service.interfaces.MoviesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository  moviesRepository;

    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Movies add(Movies movies) {

        if (!checkMovieIsExist(movies)) {
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
    public Movies getMovieById(Long id) {

        return moviesRepository.findById(id).orElseThrow(() -> new MovieNotFoundByIdException(id));
    }

    @Override
    public List<Long> findAllKpIds() {

        return moviesRepository.findAllKpIds();
    }

    @Override
    public void saveAllNewMovies(List<Movies> movies) {

        List<Long> movieIdsFromDatabase = findAllKpIds();
        List<Movies> filteredMovieList = new ArrayList<>();

        for (Movies movie : movies) {
            if (!movieIdsFromDatabase.contains(movie.getId())) {
                filteredMovieList.add(movie);
            }
        }

        log.info("Move size : {} ", movies.size());
        log.info("Movie values : {}", Arrays.toString(new List[]{movies}));
        moviesRepository.saveAll(filteredMovieList);
        log.info(">>>Added new movies: " + Arrays.toString(filteredMovieList.toArray()));
    }

//    public void movieBuilder(MovieResultResponseDTO movieResultResponseDTO) {
//        // TODO movieResponseBuilder
//        for (Movie movie : movieResultResponseDTO.getMovies()) {
//            if (checkMovieIsExist(movie)) {
//
//            }
//
//        }
//    }

    private boolean checkMovieIsExist(Movies movies) {

        return moviesRepository.findById(movies.getId()).isPresent();
    }
}
