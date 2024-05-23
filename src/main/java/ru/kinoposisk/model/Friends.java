package ru.kinoposisk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "friends", schema = "kinopoisk_dev_service")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frineds_GEN")
    @SequenceGenerator(name = "frineds_GEN", sequenceName = "frineds_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Fetch(FetchMode.SELECT)
    private Users userId;

    @Column(name = "friend_id", nullable = false)
    private Long friendId;

    @Column(name = "date")
    @Builder.Default
    private Date date = new Date();

    @Column(name = "friend_request_status")
    @Enumerated(EnumType.STRING)
    private FriendsRequestStatusEnum friendRequestStatus;

}
