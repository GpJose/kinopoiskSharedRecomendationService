package ru.kinoposisk.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kinoposisk.config.RetryTemplateConfig;
import ru.kinoposisk.dto.request.MovieSearchRequestDTO;
import ru.kinoposisk.dto.response.Country;
import ru.kinoposisk.dto.response.Genre;
import ru.kinoposisk.dto.response.Movie;
import ru.kinoposisk.dto.response.MovieResultResponseDTO;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;
import ru.kinoposisk.service.interfaces.MovieHistoryService;
import ru.kinoposisk.service.interfaces.MoviesService;
import ru.kinoposisk.service.interfaces.UsersService;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class KinopoiskAPIServiceImpl implements KinopoiskAPIService {

    @Value("${ru.kinopoisk.token}")
    private String apiKey;
    @Value("${ru.kinopoisk.url}")
    private String apiUrl;

    private String url = "https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&selectFields=id&selectFields=name&selectFields=enName&selectFields=description&selectFields=year&selectFields=movieLength&selectFields=countries&selectFields=logo&selectFields=genres&selectFields=type&selectFields=rating&selectFields=poster&selectFields=persons&sortField=rating.kp&sortType=-1&type=movie&rating.kp=5-10&movieLength=100-500&genres.name=%2B%D0%B4%D1%80%D0%B0%D0%BC%D0%B0&genres.name=%2B%D0%BA%D0%BE%D0%BC%D0%B5%D0%B4%D0%B8%D1%8F&countries.name=%D0%A1%D0%A8%D0%90";
    private final RetryTemplateConfig templateConfig;

    private final MovieHistoryService movieHistoryService;
    private final UsersService usersService;
    private final MoviesService moviesService;

    @Autowired
    public KinopoiskAPIServiceImpl(RetryTemplateConfig templateConfig, MovieHistoryService movieHistoryService, UsersService usersService, MoviesService moviesService) {
        this.movieHistoryService = movieHistoryService;
        this.usersService = usersService;
        this.templateConfig = templateConfig;
        this.moviesService = moviesService;
    }

    @SneakyThrows
    public void sendRequest(Users user) {

        // TODO Получение историй 10ти фильмов. Если их нет -

        // TODO Movie Repo replace with movie Service and error Throw if user do not have any movies
        List<MovieHistory> userMovieHistory = movieHistoryService.findByUser(user);

        // TODO Refactoring in another method send with history or quizsAnswers

        // TODO Exception с редиректом на прохождение вопросов по фильму
        // TODO CHECK 10 OR MORE MOVIES BEFORE THIS METHOD OR NULL
        // TODO Передавать в метод MovieHistoryList
        if (userMovieHistory.size() < 10) {
            throw new NullPointerException("For recommendation you need to pass 10 or more movies");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", apiKey);
        headers.add("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);


        MovieSearchRequestDTO requestDTO = MovieSearchRequestDTO.builder()
                .genres(Arrays.asList(user.getQuizAnswers().getGenre()))
                .movieLength(user.getQuizAnswers().getDuration())
                .countries(Arrays.asList(user.getQuizAnswers().getCountry()))
                .build();

//        String jsonRequestString = Mapper.objectToJson(userMovieHistory);
//        log.info(jsonRequestString);
//        log.info(entity.toString());

        templateConfig.retryTemplate().execute(context -> {
            ResponseEntity<MovieResultResponseDTO> responseEntity = templateConfig.restTemplate().exchange(URI.create(urlBuilder(requestDTO)), HttpMethod.GET, entity, MovieResultResponseDTO.class);

            MovieResultResponseDTO result = responseEntity.getBody();
            log.info(">>>RESPONSE BODY: " + result.toString());
            List<Movie> movieListResponse =  result.getMovies();

            List<Movies> moviesList = new ArrayList<>();
            for (Movie movie : movieListResponse) {

                Movies movies = Movies.builder()
                        .kinopoiskId(movie.getId())
                        .kpRating(movie.getKpRating())
                        .imdbRating(movie.getImdbRating())
                        .duration(movie.getLength())
                        .type(movie.getType())
                        .name(movie.getName())
                        .description(movie.getDescription())
                        .year(movie.getYear())
                        .url(movie.getPoster().getUrl())
                        .previewURL(movie.getPoster().getPreviewURL())
                        .logoURL(movie.getLogo().getUrl())
                        .genres(movie.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")))
                        .countries(movie.getCountries().stream().map(Country::getName).collect(Collectors.joining(", ")))
                        .build();

                moviesList.add(movies);
            }
            moviesService.saveAllNewMovies(moviesList);

            return ResponseEntity.ok(responseEntity.getBody());
        });
    }

    private String urlBuilder(MovieSearchRequestDTO requestDTO) {
        StringBuilder urlBuilder = new StringBuilder(apiUrl);

        urlBuilder.append("movieLength=").append(URLEncoder.encode(requestDTO.getMovieLength(), StandardCharsets.UTF_8)).append("&");

        List<GenreEnums> genres = requestDTO.getGenres();
        for (GenreEnums genre : genres) {
            urlBuilder.append("genres.name=").append(URLEncoder.encode(genre.toString().replaceAll("_"," "), StandardCharsets.UTF_8)).append("&");
        }

        List<CountryEnums> countries = requestDTO.getCountries();
        for (CountryEnums country : countries) {
            urlBuilder.append("countries.name=").append(URLEncoder.encode(country.toString().replaceAll("_"," "), StandardCharsets.UTF_8)).append("&");
        }

        urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        log.info(">>>>>>>>URL BUILDER: " + urlBuilder);

        return urlBuilder.toString();
    }
}
