package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;

import java.util.List;

public interface FriendsService  {

    Friends add(Friends friends, String login);

    Friends add(Friends friends, Users user);

    List<Friends> findAllFriendsByUsers(Users user);

    List<Friends> findAllFriendsRequestByUser(Users user);
}
