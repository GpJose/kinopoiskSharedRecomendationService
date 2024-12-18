package ru.kinoposisk.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @OneToOne(mappedBy = "quizAnswers")
//    @JoinColumn(name = "user_login", referencedColumnName = "login")
//    @Fetch(FetchMode.SELECT)
    private Users users;

//    @Column(name = "genre", nullable = false)
    @OneToMany(mappedBy = "genreName", fetch = FetchType.EAGER)
    @CollectionTable(name = "genres")
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "genre_reference", referencedColumnName = "genre_name")
    private List<GenreReference> genre;

    @Column(name = "duration", nullable = false)
    private String duration;

//    @Column(name = "country", nullable = false)
//    @JoinColumn
    @OneToMany(mappedBy = "countryName",fetch = FetchType.LAZY)
    private List<CountryReference> country;

    @Column(name = "date", nullable = false)
    @Builder.Default
    private Date date = new Date();

}