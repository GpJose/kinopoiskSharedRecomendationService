package ru.kinoposisk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kinoposisk.config.RetryTemplateConfig;
import ru.kinoposisk.dto.response.MovieResultResponseDTO;
import ru.kinoposisk.model.MovieHistory;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.repository.MovieHistoryRepository;
import ru.kinoposisk.repository.UserRepository;
import ru.kinoposisk.service.interfaces.KinopoiskAPIService;

import java.net.URI;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class KinopoiskAPIServiceImpl implements KinopoiskAPIService {

    @Value("${ru.kinopoisk.token}")
    private String apiKey;
    @Value("${ru.kinopoisk.url}")
    private String apiUrl;

    private String url = "https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&selectFields=id&selectFields=name&selectFields=enName&selectFields=description&selectFields=year&selectFields=movieLength&selectFields=countries&selectFields=logo&selectFields=genres&selectFields=type&selectFields=rating&selectFields=poster&selectFields=persons&sortField=rating.kp&sortType=-1&type=movie&rating.kp=5-10&movieLength=100-500&genres.name=%2B%D0%B4%D1%80%D0%B0%D0%BC%D0%B0&genres.name=%2B%D0%BA%D0%BE%D0%BC%D0%B5%D0%B4%D0%B8%D1%8F&countries.name=%D0%A1%D0%A8%D0%90";
    private final MovieHistoryRepository movieHistoryRepository;
    private final UserRepository userRepository;
    private final RetryTemplateConfig templateConfig;

    @Autowired
    public KinopoiskAPIServiceImpl(MovieHistoryRepository movieHistoryRepository, UserRepository userRepository, RetryTemplateConfig templateConfig) {
        this.movieHistoryRepository = movieHistoryRepository;
        this.userRepository = userRepository;
        this.templateConfig = templateConfig;
    }

    @SneakyThrows
    public void sendRequest(Users user) {


        List<MovieHistory> userMovieHistory = movieHistoryRepository.findByUserId(user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", apiKey);
            headers.add("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ObjectMapper mapper = new ObjectMapper();
        String jsonRequestString = mapper.writeValueAsString(userMovieHistory);
        log.info(jsonRequestString);
        log.info(entity.toString());
        templateConfig.retryTemplate().execute(context -> {
            ResponseEntity<MovieResultResponseDTO> responseEntity = templateConfig.restTemplate().exchange(URI.create(url), HttpMethod.GET, entity, MovieResultResponseDTO.class);
            log.info(responseEntity.getBody());
            MovieResultResponseDTO result = responseEntity.getBody();
            return ResponseEntity.ok(responseEntity.getBody());
        });
    }

    public Users findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
