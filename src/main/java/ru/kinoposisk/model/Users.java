package ru.kinoposisk.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.kinoposisk.model.enums.RoleEnums;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", schema = "kinopoisk_dev_service")
public class Users {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence_SEQ")
    @SequenceGenerator(name = "userSequence_GEN"    , sequenceName = "userSequence_SEQ", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @ToString.Include
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @ToString.Include
    @Column(name = "password", nullable = false)
    private String password;

    @ToString.Include
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Include
    @ElementCollection(targetClass = RoleEnums.class, fetch = FetchType.LAZY)
//    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private List<RoleEnums> roles = new ArrayList<>();

    @ToString.Include
    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

//    @Transient
    @ToString.Include
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<Friends> friends = new ArrayList<>();

    @ToString.Include
    @OneToMany(mappedBy = "friendId", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<Friends> friendsId = new ArrayList<>();

//    @ToString.Include
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "quiz_answers_id")
//    @Fetch(FetchMode.SELECT)
//    private QuizAnswers quizAnswers;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_answers_id",referencedColumnName = "id")
    private QuizAnswers quizAnswers;


    @ToString.Include
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<MovieHistory> movieHistoriesList = new ArrayList<>();

}

