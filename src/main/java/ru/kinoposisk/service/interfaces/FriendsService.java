package ru.kinoposisk.service.interfaces;

import ru.kinoposisk.model.Friends;

public interface FriendsService extends BaseService<Friends> {

    Friends add(Friends friends, String login);

}
