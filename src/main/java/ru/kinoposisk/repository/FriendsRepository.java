package ru.kinoposisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByUserId(Users userId, FriendsRequestStatusEnum friendRequest);

    List<Friends> findByUserIdAndUserId_Friends_FriendRequestStatusOrderByUserId_Friends_DateDesc(Users userId, FriendsRequestStatusEnum friendRequestStatus);



    List<Friends> (Users userId);

}