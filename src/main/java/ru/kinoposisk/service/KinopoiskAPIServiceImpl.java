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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kinoposisk.config.RetryTemplateConfig;
import ru.kinoposisk.dto.request.MovieSearchRequestDTO;
import ru.kinoposisk.dto.response.Country;
import ru.kinoposisk.dto.response.Genre;
import ru.kinoposisk.dto.response.Movie;
import ru.kinoposisk.dto.response.MovieResultResponseDTO;
import ru.kinoposisk.model.Movies;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;
import ru.kinoposisk.service.interfaces.MovieHistoryService;
import ru.kinoposisk.service.interfaces.MoviesService;
import ru.kinoposisk.service.interfaces.UsersService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
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
    public void sendRequest(@Valid Users user) {

        // TODO Получение историй 10ти фильмов. Если их нет -

        // TODO Movie Repo replace with movie Service and error Throw if user do not have any movies
//        List<MovieHistory> userMovieHistory = movieHistoryService.findByUser(user);

        // TODO Refactoring in another method send with history or quizsAnswers

        // TODO Exception с редиректом на прохождение вопросов по фильму
        // TODO CHECK 10 OR MORE MOVIES BEFORE THIS METHOD OR NULL
        // TODO Передавать в метод MovieHistoryList
//        if (userMovieHistory.size() < 10) {
//            throw new NullPointerException("For recommendation you need to pass 10 or more movies");
//        }


        log.info("SENDING REQUEST");
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", apiKey);
        headers.add("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);


        MovieSearchRequestDTO requestDTO = MovieSearchRequestDTO.builder()
                .genres(user.getQuizAnswers().getGenre())
                .movieLength(user.getQuizAnswers().getDuration())
                .countries(user.getQuizAnswers().getCountry())
                .build();

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        params.put("movieLength", Collections.singletonList(requestDTO.getMovieLength().replaceAll("[\\[\\]]","")));
        requestDTO.getGenres().forEach(genre -> {
            params.add("genres.name", genre.getGenreName().replaceAll("_", " ").replaceAll("[\\[\\]]","").toLowerCase());
        });
        requestDTO.getCountries().forEach(country -> {
            params.add("countries.name", country.getCountryName().replaceAll("_", " ").replaceAll("[\\[\\]]",""));
        });

        params.forEach((name, text) -> {
            try {
                log.info("Decoder : {} valuse : {}",name,URLDecoder.decode(String.valueOf(text),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        List<Movies> moviesList = new ArrayList<>();
        templateConfig.retryTemplate().execute(context -> {


            UriBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrl).queryParams(params);
            URI uri = uriBuilder.build();
            log.info(">>>>URI : " + uri);
            ResponseEntity<MovieResultResponseDTO> responseEntity = templateConfig.restTemplate().exchange
                    (uri, HttpMethod.GET, entity, MovieResultResponseDTO.class);
            log.info("GETTING REQUEST : ");

            log.info(">>>RESPONSE : " + responseEntity);
            MovieResultResponseDTO result = responseEntity.getBody();
            log.info(">>>RESPONSE BODY: " + result.toString());
            List<Movie> movieListResponse =  result.getMovies();
            log.info(">>>MovieCount : " + movieListResponse.size());

            for (Movie movie : movieListResponse) {

                try {

                    Movies movies = Movies.builder()
                            .kinopoiskId(movie.getId())
                            .kpRating(movie.getKpRating())
                            .imdbRating(movie.getImdbRating())
                            .duration(movie.getLength())
                            .type(movie.getType())
                            .name(movie.getName())
                            .description(movie.getDescription())
                            .year(movie.getYear())
                            .genres(movie.getGenres()
                                    .stream()
                                    .map(Genre::getName)
                                    .collect(Collectors.joining(", ")))
                            .countries(movie.getCountries()
                                    .stream()
                                    .map(Country::getName)
                                    .collect(Collectors.joining(", ")))
                            .build();

                    if(movie.getPoster() != null) {
                        movies.setUrl(movie.getPoster().getUrl());
                        movies.setPreviewURL(movie.getPoster().getPreviewURL());
                    }
                    if(movie.getLogo() != null) {
                        movies.setLogoURL(movie.getLogo().getUrl());
                    }
                    moviesList.add(movies);
                }

                catch (NullPointerException e) {
                    log.warn("Error parsing movie: " + movie + "\n" + e);
                }
            }
            moviesService.saveAllNewMovies(moviesList);

            return ResponseEntity.ok(responseEntity.getBody());
        });
    }

//    private String urlBuilder(MovieSearchRequestDTO requestDTO) {
//        StringBuilder urlBuilder = new StringBuilder(apiUrl);
//
//        Map<String,String> stringMap = new HashMap<>();
//
//
//        urlBuilder.append("movieLength=").append(URLEncoder.encode(requestDTO.getMovieLength(), StandardCharsets.UTF_8)).append("&");
//
//        List<GenreEnums> genres = requestDTO.getGenres();
//        for (GenreEnums genre : genres) {
//            urlBuilder.append("genres.name=").append(URLEncoder.encode(genre.toString().replaceAll("_"," "), StandardCharsets.UTF_8)).append("&");
//        }
//
//        List<CountryEnums> countries = requestDTO.getCountries();
//        for (CountryEnums country : countries) {
//            urlBuilder.append("countries.name=").append(URLEncoder.encode(country.toString().replaceAll("_"," "), StandardCharsets.UTF_8)).append("&");
//        }
//
//        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
//
//        log.info(">>>>>>>>URL BUILDER: " + urlBuilder);
//
//        return urlBuilder.toString();
//    }

}
