package ru.kinoposisk.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genres", schema = "kinopoisk_dev_service")
public class GenreReference {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genreReference_GEN")
    @SequenceGenerator(name = "genreReference_GEN", sequenceName = "genreReference_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "genre_name")
    private String genreName;

    @Column(name = "slug")
    private String slugName;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private QuizAnswers QuizAnswers;

}
