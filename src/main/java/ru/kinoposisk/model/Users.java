package ru.kinoposisk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.kinoposisk.model.enums.RoleEnums;

import javax.persistence.*;
import java.util.*;


@Entity
@Data
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

    @ElementCollection(targetClass = RoleEnums.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<RoleEnums> roles = new ArrayList<>(EnumSet.of(RoleEnums.ROLE_USER));

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
        private List<Friends> friends = new java.util.ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<QuizAnswers> quizAnswers = new java.util.ArrayList<>();
}

