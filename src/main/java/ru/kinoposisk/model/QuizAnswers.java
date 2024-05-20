package ru.kinoposisk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;

import javax.persistence.*;

/**
 * Класс для хранения вопросов
 * Для представления жанра используется перечисление {@link GenreEnums}.
 * Для представления страны используется перечисление {@link CountryEnums}.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "quiz_answers", schema = "kinopoisk_dev_service")
public class QuizAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_sequence")
    @SequenceGenerator(name = "quiz_sequence", sequenceName = "quiz_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    private Users userId;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "country", nullable = false)
    private String country;
}

