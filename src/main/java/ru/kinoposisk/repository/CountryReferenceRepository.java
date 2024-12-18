package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kinoposisk.model.CountryReference;

import java.util.Optional;

public interface CountryReferenceRepository extends JpaRepository<CountryReference, Long> {
    @Query(value = "select * from kinopoisk_dev_service.countries sw where sw.country_name = ?1 ", nativeQuery = true)
    Optional<CountryReference> findByCountryName(String countryName);
}