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
@Table(name = "countries", schema = "kinopoisk_dev_service")
public class CountryReference {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countryReference_GEN")
    @SequenceGenerator(name = "countryReference_GEN", sequenceName = "countryReference_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "country_name")
    private String countryName;
    @Column(name = "slug")
    private String slugName;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private QuizAnswers quizAnswers;
}
