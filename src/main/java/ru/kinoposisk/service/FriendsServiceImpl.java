package ru.kinoposisk.service;

import ru.kinoposisk.model.Friends;
import ru.kinoposisk.model.Users;
import ru.kinoposisk.model.enums.FriendsRequestStatusEnum;
import ru.kinoposisk.repository.FriendsRepository;
import ru.kinoposisk.service.interfaces.FriendsService;

import java.util.List;

public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendsRepository;

    public FriendsServiceImpl(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    @Override
    public Friends add(Friends friends) {
        return null;
    }

    @Override
    public void deleteByID(Long id) {

    }

    @Override
    public Friends findByID(Long id) {
        return null;
    }
//
//    @Override
//    public Friends findByLogin(String login) {
//        return null;
//    }

    @Override
    public List<Friends> getAll() {
        return null;
    }

    @Override
    public Friends add(Friends friends, Users user) {
        return null;
    }

    @Override
    public List<Friends> findAllFriendsByUsers(Users user) {

        return friendsRepository.findByUserId(user);
    }

    @Override
    public List<Friends> findAllFriendsRequestByUser(Users user) {

        return friendsRepository.findByUserIdAndUserId_Friends_FriendRequestStatusOrderByUserId_Friends_DateDesc(user, FriendsRequestStatusEnum.PENDING);
    }
}
