package ru.kinoposisk.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kinoposisk.exception.friendsExceptions.FriendsAccessDenyException;
import ru.kinoposisk.exception.moviesExceptions.MovieNotFoundByIdException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class HandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({FriendsAccessDenyException.class})
    public ResponseEntity<String> handleValidationExceptions(FriendsAccessDenyException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<String> handleValidationExceptions(UsernameNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ResponseEntity<String> handleValidationExceptions(BindException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<String> handleValidationExceptions(DuplicateKeyException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleValidationExceptions(IOException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MovieNotFoundByIdException.class)
    public ResponseEntity<String> handleValidationExceptions(MovieNotFoundByIdException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleValidationExceptions(Exception ex) {
        ex.printStackTrace();

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @ExceptionHandler({MethodArgumentNotValidException.class, UsernameNotFoundException.class, DuplicateKeyException.class, IOException.class, Exception.class})
//    public ResponseEntity<Object> handleExceptions(Exception ex) {
//
//        HttpStatus status;
//        Map<String, Object> body = new LinkedHashMap<>();
//
//        if (ex instanceof MethodArgumentNotValidException) {
//            status = HttpStatus.BAD_REQUEST;
//            Map<String, String> fieldErrors = new HashMap<>();
//            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
//                String fieldName = ((FieldError) error).getField();
//                String errorMessage = error.getDefaultMessage();
//                fieldErrors.put(fieldName, errorMessage);
//            });
//            body.put("errors", fieldErrors);
//
//        } else if (ex instanceof UsernameNotFoundException || ex instanceof DuplicateKeyException || ex instanceof IOException || ex instanceof MovieNotFoundByIdException) {
//            status = HttpStatus.BAD_REQUEST;
//            body.put("error", ex.getMessage());
//
//        } else {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            body.put("error", ex.getMessage());
//            ex.printStackTrace();
//        }
//        log.info("Body: {}", body);
//        log.info("Status: {}", status);
//
//        return new ResponseEntity<>(body, status);
//    }

}


