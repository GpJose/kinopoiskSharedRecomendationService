package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    @Query(value = "select f from Friends f inner join f.userId.friends friends " +
            "where f.userId = ?1 and friends.friendRequestStatus = ?2 " +
            "order by f.userId.friends.date DESC")
    List<Friends> findByUserIdAndUserId_Friends_FriendRequestStatusOrderByUserId_Friends_DateDesc(Users userId, FriendsRequestStatusEnum friendRequestStatus);

    Optional<Friends> findByUserIdAndFriendIdAndFriendRequestStatus(Users userId, Users friendId, FriendsRequestStatusEnum friendRequestStatus);


    Optional<Friends> findByUserIdAndFriendId(Users userId, Users friendId);


}