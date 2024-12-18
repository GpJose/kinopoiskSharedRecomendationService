package ru.kinoposisk.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Log4j2
@RequiredArgsConstructor
public class SendMovieRecommendationScheduler {


    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String setFrom;
    @Value("${mail.setTo}")
    private String setTo;

    @SneakyThrows
    @Scheduled(cron = "${ru.kinopoisk.scheduler.sendMovieRecommendation.cron}")
    public void SendMovieRecommendation() {
        log.info("SendMovieRecommendationScheduler");
        sendEmail();
        log.info("SendMovieRecommendationScheduler completed");
        // TODO : Отправка рекомендаций на почту
    }

    private void sendEmail() throws MessagingException {
        // TODO : доставать из базы кому / что отправлять
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(emailSender.createMimeMessage());
        mimeMessageHelper.setFrom(setFrom);
        mimeMessageHelper.setTo(setTo);
        mimeMessageHelper.setSubject("test");
        mimeMessageHelper.setText("test text");
//        mimeMessageHelper.addAttachment();
        emailSender.send(mimeMessageHelper.getMimeMessage());
    }
}