package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.dto.profile.UsersDTO;
import ru.kinoposisk.exception.friendsExceptions.FriendsAccessDenyException;
import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;

import java.util.List;

public interface FriendsService  {

    void add(Users user, Users friend);

//    List<Friends> getAllFriendsByUsers(Users user);

    void removeFriend(Users user, Users friend);

    List<Friends> getAllFriendsRequestByUser(Users user);

    Friends sendFriendRequest(Users user, Users friend);

    FriendsRequestStatusEnum findFriendRequestStatus(Users user, Users friend);

    UsersDTO getFriendProfile(Users user, Users friend) throws FriendsAccessDenyException;

}
