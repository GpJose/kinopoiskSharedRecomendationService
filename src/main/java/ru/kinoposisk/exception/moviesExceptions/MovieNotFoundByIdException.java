package ru.kinoposisk.exception.moviesExceptions;

public class MovieNotFoundByIdException extends NullPointerException {
    public MovieNotFoundByIdException(Long id) {
        super("Movie with id: " + id + " not found");
    }
}
