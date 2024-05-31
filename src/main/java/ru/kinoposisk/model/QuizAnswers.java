package ru.kinoposisk.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.utils.CountryEnumConverter;
import ru.kinoposisk.utils.GenreEnumConverter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "quiz_answers", schema = "kinopoisk_dev_service")
public class QuizAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_sequence")
    @SequenceGenerator(name = "quiz_sequence", sequenceName = "quiz_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "quizAnswers", optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SELECT)
    private Users users;

    @Column(name = "genre", nullable = false)
    @Convert(converter = GenreEnumConverter.class)
    private GenreEnums[] genre;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "country", nullable = false)
    @Convert(converter = CountryEnumConverter.class)
    private CountryEnums[] country;

    @Column(name = "date", nullable = false)
    @Builder.Default
    private Date date = new Date();

}