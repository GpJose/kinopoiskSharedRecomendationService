package ru.kinoposisk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movies", schema = "kinopoisk_dev_service")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_GEN")
    @SequenceGenerator(name = "movies_GEN", sequenceName = "movies_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "kinopoisk_id")
    private Long kinopoiskId;

    @Column(name = "kinopisk_rating")
    private double kpRating;
    @Column(name = "imdb_rating")
    private double imdbRating;

    @Column(name = "duration")
    private Integer duration;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "year")
    private int year;

    @Column(name = "poster_url")
    private String url;
    @Column(name = "preview_poster_url")
    private String previewURL;
    @Column(name = "logo_url")
    private String logoURL;

    @Column(name = "genres")
    private String genres;
    @Column(name = "countries")
    private String countries;


    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<MovieHistory> movieHistory = new java.util.ArrayList<>();
}
