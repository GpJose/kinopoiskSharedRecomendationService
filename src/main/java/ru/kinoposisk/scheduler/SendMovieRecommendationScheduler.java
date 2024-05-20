package ru.kinoposisk.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SendMovieRecommendationScheduler {

    @Scheduled(cron = "${ru.kinopoisk.scheduler.sendMovieRecommendation.cron}")
    private void SendMovieRecommendation() {
        log.info("SendMovieRecommendationScheduler");
        // TODO : Отправка рекомендаций на почту
    }
}
