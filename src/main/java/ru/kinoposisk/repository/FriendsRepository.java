package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query(value = "select * from kinopoisk_dev_service.friends f where f.user_id = ?1 and f.friend_request_status = ?2 order by f.date desc",
            nativeQuery = true)
    List<Friends> findByUserIdAndRequestStatusAllFriendList(Long userId, FriendsRequestStatusEnum friendRequestStatus);

    Optional<Friends> findByUserIdAndFriendIdAndFriendRequestStatus(Users userId, Users friendId, FriendsRequestStatusEnum friendRequestStatus);


    Optional<Friends> findByUserIdAndFriendId(Users userId, Users friendId);


}