package ru.kinoposisk.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.kinoposisk.model.enums.RoleEnums;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", schema = "kinopoisk_dev_service")
public class    Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence_GEN")
    @SequenceGenerator(name = "userSequence_GEN"    , sequenceName = "userSequence_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ElementCollection(targetClass = RoleEnums.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<RoleEnums> roles = new ArrayList<>(EnumSet.of(RoleEnums.ROLE_USER));

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<Friends> friends = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("users, date  desc")
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<QuizAnswers> quizAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<MovieHistory> movieHistoriesList = new ArrayList<>();
}

