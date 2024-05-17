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
@Table(name = "MovieHistory", schema = "kinopoisk_dev_service")
public class MovieHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MovieHistory_GEN")
    @SequenceGenerator(name = "MovieHistory_GEN", sequenceName = "MovieHistory_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id", updatable = false, referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Users user;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "movie_id", updatable = false)
    @Fetch(FetchMode.SELECT)
    private Movies movie;

    @Column(name = "watched_date")
    private Date watchedDate;

    @Column(name = "rating")
    private Integer rating;
}
