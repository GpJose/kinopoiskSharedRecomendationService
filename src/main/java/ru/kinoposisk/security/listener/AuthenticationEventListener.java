package ru.kinoposisk.security.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {

        if (authenticationEvent instanceof AuthenticationSuccessEvent) {
            handleAuthenticationSuccessEvent((AuthenticationSuccessEvent) authenticationEvent);

        } else if (authenticationEvent instanceof AbstractAuthenticationFailureEvent) {
            handleAuthenticationFailureEvent((AbstractAuthenticationFailureEvent) authenticationEvent);
        }
    }

    private void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent successEvent) {

        Authentication authentication = successEvent.getAuthentication();
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().toString();

        String auditMessage = "Successful login attempt with username: " + username +
                "\tAuthorities: " + authorities;
        log.info(auditMessage);
    }

    private void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent failureEvent) {

        Authentication authentication = failureEvent.getAuthentication();
        String username = authentication != null ? authentication.getName() : "Unknown";
        String errorMessage = failureEvent.getException() != null ? failureEvent.getException().getMessage() : "Unknown Error";

        String auditMessage = "Failed login attempt with username: " + username +
                "\tError Message: " + errorMessage;
        log.warn(auditMessage);
    }
}

