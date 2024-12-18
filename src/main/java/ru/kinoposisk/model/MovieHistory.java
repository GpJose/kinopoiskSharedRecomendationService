package ru.kinoposisk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movie_history", schema = "kinopoisk_dev_service")
public class MovieHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MovieHistory_GEN")
    @SequenceGenerator(name = "MovieHistory_GEN", sequenceName = "MovieHistory_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Users user;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Movies movie;

    @Column(name = "watched_date")
    private Date watchedDate;

    @Column(name = "rating",columnDefinition = "smallint")
    private Short rating;
}
