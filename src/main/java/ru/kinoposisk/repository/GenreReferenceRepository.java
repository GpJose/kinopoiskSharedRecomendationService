package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kinoposisk.model.GenreReference;

import java.util.Optional;

public interface GenreReferenceRepository extends JpaRepository<GenreReference, Long> {
    @Query(value = "Select * from kinopoisk_dev_service.genres where genres.genre_name = ?1", nativeQuery = true)
    Optional<GenreReference> searchByGenreName(String genreName);
}