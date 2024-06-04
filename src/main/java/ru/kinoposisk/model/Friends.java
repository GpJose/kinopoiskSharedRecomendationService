package ru.kinoposisk.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "friends", schema = "kinopoisk_dev_service")
public class Friends {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frineds_SEQ")
    @SequenceGenerator(name = "frineds_GEN", sequenceName = "frineds_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private Users userId;

    @ManyToOne
    @ToString.Exclude
    private Users friendId;

    // Todo: FriendLogin
    // TODO User login

    @ToString.Include
    @Column(name = "date")
    @Builder.Default
    private Date date = new Date();

    @ToString.Include
    @Column(name = "friend_request_status")
    @Enumerated(EnumType.STRING)
    private FriendsRequestStatusEnum friendRequestStatus;

}
