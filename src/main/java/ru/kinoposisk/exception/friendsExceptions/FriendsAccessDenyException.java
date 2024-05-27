package ru.kinoposisk.exception.friendsExceptions;

import ru.kinoposisk.model.Users;

public class FriendsAccessDenyException extends Exception {

    public FriendsAccessDenyException(Users friend) {
        super("You must be friends with " + friend.getLogin() + " to watch his profile");
    }
}
